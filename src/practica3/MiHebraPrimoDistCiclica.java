package practica3;

public class MiHebraPrimoDistCiclica extends Thread{
	int miId, numHebras;
	long[] vector;
	
	public MiHebraPrimoDistCiclica(int miId, int numHebras, long[] vector) {
		super();
		this.miId = miId;
		this.numHebras = numHebras;
		this.vector = vector;
	}
	
	public void run() {
		for(int i=miId; i<vector.length; i+=numHebras) {
			if (EjemploMuestraPrimosEnVector2a.esPrimo(vector[i])) {
				System.out.println("Encontrado primo: "+vector[i]);
			}
		}
	}
}
