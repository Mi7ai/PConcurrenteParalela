package practica4;

public class MiHebraMultAcumulaciones1 extends Thread{
	int miId, numHebras;
	long numRectangulos;
	Acumula a;
	
	MiHebraMultAcumulaciones1(int miId, int numHebras, long numRectangulos, Acumula a){
		this.miId = miId;
		this.numHebras = numHebras;
		this.numRectangulos = numRectangulos;
		this.a = a;
	}
	
	public void run(){
		double x, baseRectangulo = 1.0 / ((double)numRectangulos);
		for (long i = miId; i<numRectangulos; i+=numHebras){
			x = baseRectangulo * (((double)i)+0.5);
			a.acumulaDato(EjemploNumeroPI1a.f(x));
		}
	}
}