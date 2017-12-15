#include <stdio.h>
#include <stdlib.h>
#include "mpi.h"

int main(int argc, char * argv[]) {

  int miId, numProcs, n, testigo;
  MPI_Status s;

  MPI_Init(&argc, &argv);

  MPI_Comm_size(MPI_COMM_WORLD, &numProcs);

  MPI_Comm_rank(MPI_COMM_WORLD, &miId);
  
  if(miId == 0) {
      printf("Escribe un número: ");
      scanf("%d", &n);
      for(int i=1; i<numProcs; i++){
	MPI_Send(&testigo, 1, MPI_INT, i, 88, MPI_COMM_WORLD);
      }

  } else {
    MPI_Recv(&testigo, 1, MPI_INT, 0, 88, MPI_COMM_WORLD, &s);
  }
    
  printf("Soy el proceso %d y el valor de n es %d\n", miId, n);

  MPI_Finalize();

  return 0;

}
