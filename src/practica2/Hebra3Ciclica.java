package practica2;

public class Hebra3Ciclica extends Thread{
	private int id, numHebras, n;
	private double vectorX[], vectorY[];

	public Hebra3Ciclica(int id, int numHebras, int n, double vectorX[], double vectorY[]) {
		super();
		this.id = id;
		this.numHebras = numHebras;
		this.n = n;
		this.vectorX = vectorX;
		this.vectorY = vectorY;
	}
	
	@Override
	public void run() {
		for (int i=id; i<n; i+=numHebras) {
			 vectorY[ i ] = EjemploFuncionSencilla1a.evaluaFuncion( vectorX[ i ] );

		}
	}
}
