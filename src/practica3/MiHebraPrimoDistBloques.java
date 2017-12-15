package practica3;

public class MiHebraPrimoDistBloques extends Thread{
	int miId, numHebras;
	long[] vector;
	
	public MiHebraPrimoDistBloques(int miId, int numHebras, long[] vector) {
		super();
		this.miId = miId;
		this.numHebras = numHebras;
		this.vector = vector;
	}
	
	public void run() {
		int tam = (vector.length + numHebras - 1)/numHebras;
		int ini = miId * tam;
		int fin = Math.min(vector.length, ini+tam);
		for(int i=ini; i<fin; i++) {
			if (EjemploMuestraPrimosEnVector2a.esPrimo(vector[i])) {
				System.out.println("Encontrado primo: "+vector[i]);
			}
		}
	}
}
