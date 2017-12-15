package practica2;

public class Hebra2Bloques extends Thread{
	private int id, numHebras, n;
	private double vectorX[], vectorY[];
	
	public Hebra2Bloques(int id, int numHebras, int n, double[] vectorX, double[] vectorY) {
		super();
		this.id = id;
		this.numHebras = numHebras;
		this.n = n;
		this.vectorX = vectorX;
		this.vectorY = vectorY;
	}
	
	@Override
	public void run(){
		int tam = (n+numHebras-1)/numHebras;
		int ini = id*tam;
		int fin = Math.min(n, ini+tam);
		
		for(int i=ini; i<fin; i++){
			vectorY[ i ] = EjemploFuncionCostosa1a.evaluaFuncion( vectorX[ i ] );
			vectorY[ i ] = EjemploFuncionSencilla1a.evaluaFuncion( vectorX[ i ] );

		}
		
	}
	
}
