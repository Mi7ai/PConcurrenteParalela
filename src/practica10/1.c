#include <stdio.h>
#include <stdlib.h>
#include "mpi.h"

int main (int argc, char * argv[]){
  int miId, numProcs;
  
  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &miId);
  MPI_Comm_size(MPI_COMM_WORLD, &numProcs);
  
  int dato = numProcs - miId + 1, suma = 0;
  MPI_Status s;
  int ant, sig;
  
  ant = (miId == 0)?MPI_PROC_NULL:miId-1;
  sig = (miId == numProcs - 1)?MPI_PROC_NULL:miId+1;
  
  
  if (miId==0){
    printf("OP Punto a punto.\n");
  }
  
  MPI_Recv(&suma, 1, MPI_INT, sig, 88, MPI_COMM_WORLD, &s);
  suma += dato;
  MPI_Send(&suma, 1, MPI_INT, ant, 88, MPI_COMM_WORLD);
  
  MPI_Barrier(MPI_COMM_WORLD);
  
  if (miId == 0){
    printf("Proc %d: dato=%d, suma=%d\n", miId, dato, suma);
  }else{
    printf("Proc %d: dato=%d\n", miId, dato);
  }
  
  MPI_Barrier(MPI_COMM_WORLD);
  
  if (miId==0){
    printf("\nOP Colectivas.\n");
  }
  
  MPI_Reduce(&dato, &suma, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);
  
  if (miId == 0){
    printf("Proc %d: dato=%d, suma=%d\n", miId, dato, suma);
  }else{
    printf("Proc %d: dato=%d\n", miId, dato);
  }
  
  MPI_Finalize();
  
  if (miId == 0){
    printf("Fin de programa.\n");
  }
  
  return 0;
}