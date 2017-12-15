#include <stdio.h>
#include <stdlib.h>
#include "mpi.h"

#define IMPRIME 1

// ============================================================================
int main( int argc, char *argv[] ) {
  int     dimVectorInicial, dimVectorLocal, i;
  int     miId, numProcs;
  double  *vectorInicial, *vectorLocal;
  double  sumaLocal, sumaFinal;

  // Inicializa MPI.
  MPI_Init( & argc, & argv );
  MPI_Comm_size( MPI_COMM_WORLD, & numProcs );
  MPI_Comm_rank( MPI_COMM_WORLD, & miId );

  // En primer lugar se comprueba si el numero de parametros es valido
  if( argc != 2 ) {
    if ( miId == 0 ) {
      fprintf( stderr, "\n" );
      fprintf( stderr, "Uso: a.out dimension\n");
      fprintf( stderr, "\n" );
    }
    MPI_Finalize();
    return( -1 );
  }
  
  // Todos los procesos deben comprobar que la dimension de vectorInicial "n"
  // es multiplo del numero de procesos.
  dimVectorInicial = atoi(argv[1]);
  if( ( dimVectorInicial % numProcs ) != 0 ) {
    if( miId == 0 ) {
      fprintf( stderr, "\n" );
      fprintf( stderr, 
          "ERROR: La dimension %d no es multiplo del numero de procesos: %d\n",
          dimVectorInicial, numProcs );
      fprintf( stderr, "\n" );
    }
    MPI_Finalize();
    exit( -1 );
  }

  // El proceso 0 crea e inicializa "vectorInicial".
  if( miId == 0 ) {
    vectorInicial = ( double * ) malloc( dimVectorInicial * sizeof( double ) );
    for( i = 0; i < dimVectorInicial; i++ ) {
      vectorInicial[ i ] = ( double ) i+1;
    }
  }

#ifdef IMPRIME
  // El proceso 0 imprime el contenido de "vectorInicial".
  if( miId == 0 ) {
    for( i = 0; i < dimVectorInicial; i++ ) {
      printf( "Proc: %d.  vectorInicial[ %3d ] = %lf\n", 
              miId, i, vectorInicial[ i ] );
    }
  }
#endif
 
  // El proceso 0 suma todos los elementos de vectorInicial
  if( miId == 0 ) {
    sumaFinal = 0;
    for( i = 0; i < dimVectorInicial; i++ ) {
      sumaFinal += vectorInicial[ i ];
    }
    printf( "sumaInicial = %lf\n", sumaFinal );
  }

  // Todos los procesos crean e inicializan "vectorLocal".
  // La siguiente linea no es correcta. Debes arreglarla.
  dimVectorLocal = dimVectorInicial/numProcs ; // ... (A)
  vectorLocal = ( double * ) malloc( dimVectorLocal * sizeof( double ) );
  for( i = 0; i < dimVectorLocal; i++ ) {
    vectorLocal[ i ] = -1.0;
  }

  // Distribucion por bloques de "vectorInicial".
  // Al final de esta fase, cada proceso debe tener sus correspondientes datos 
  // propios en su "vectorLocal".
  // ... (B)
  MPI_Status s;
  if(miId == 0){
    for ( i=0; i<dimVectorLocal; i++){
      vectorLocal[i] = vectorInicial[i];
    }
    for( i = dimVectorLocal; i<dimVectorInicial; i += dimVectorLocal ){
      MPI_Send(&vectorInicial[i], dimVectorLocal, MPI_DOUBLE, i/dimVectorLocal, 88, MPI_COMM_WORLD);
    }
  }else{
    MPI_Recv(vectorLocal, dimVectorLocal, MPI_DOUBLE, 0, 88, MPI_COMM_WORLD, &s);  
  }
  
 

#ifdef IMPRIME
  // Todos los procesos imprimen su vector local.
  for( i = 0; i < dimVectorLocal; i++ ) {
    printf( "Proc: %d.  vectorLocal[ %3d ] = %lf\n", 
            miId, i, vectorLocal[ i ] );
  }
  
   MPI_Barrier(MPI_COMM_WORLD);
#endif
 
  // Cada proceso suma todos los elementos de vectorLocal
  // ... (C)

  // Se acumulan las sumas locales de cada procesador en sumaFinal sobre el proceso 0
  // ... (D)

  // El proceso 0 imprime la suma final
  if ( miId == 0) {
    printf( "Proc: %d.  sumaFinal = %lf\n", miId, sumaFinal );
  }

  // El proceso 0 borra el vector inicial.
  if( miId == 0 ) {
    free( vectorInicial );
  }

  // Todos los procesos borran su vector local.
  free( vectorLocal );

  // Finalizacion de MPI.
  MPI_Finalize();
  
  // Fin de programa.
  printf( "Fin de programa (%d) \n", miId );
  return 0;
}

