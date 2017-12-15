package practica2;

public class Hebra1Ciclica extends Thread{
	private int id, numHebras, n;

	public Hebra1Ciclica(int id, int numHebras, int n) {
		super();
		this.id = id;
		this.numHebras = numHebras;
		this.n = n;
	}
	
	@Override
	public void run() {
		for (int i=id; i<n; i+=numHebras) {
			System.out.println("Hebra "+id+": "+i);
		}
	}
}
