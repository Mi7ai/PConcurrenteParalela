#include <stdio.h>
#include <stdlib.h>
#include "mpi.h"

int main(int argc, char * argv[]) {

  int miId, numProcs, n;
  MPI_Status s;

  MPI_Init(&argc, &argv);

  MPI_Comm_size(MPI_COMM_WORLD, &numProcs);

  MPI_Comm_rank(MPI_COMM_WORLD, &miId);
  
  if(miId == 0) {
    printf("Escribe un número: ");
    scanf("%d\n", &n);
    for (int i=1; i<numProcs; i++){
      MPI_Send(&n, 1, MPI_INT, i, 86, MPI_COMM_WORLD);
    }
  } else {
    MPI_Recv(&n, 1, MPI_INT, 0, 86, MPI_COMM_WORLD, &s);
  }
    
  printf("Soy el proceso %d y el valor de n es %d\n", miId, n);

  MPI_Finalize();

  return 0;

}
