package practica4;

public class MiHebraUnaAcumulacion1 extends Thread{
	int miId, numHebras;
	long numRectangulos;
	Acumula a;
	
	MiHebraUnaAcumulacion1(int miId, int numHebras, long numRectangulos, Acumula a){
		this.miId = miId;
		this.numHebras = numHebras;
		this.numRectangulos = numRectangulos;
		this.a = a;
	}
	
	public void run(){
		double x, suma = 0.0, baseRectangulo = 1.0 / ((double)numRectangulos);
		for (long i = miId; i<numRectangulos; i+=numHebras){
			x = baseRectangulo * (((double)i)+0.5);
			suma += EjemploNumeroPI1a.f(x);
		}
		a.acumulaDato(suma);
	}
}