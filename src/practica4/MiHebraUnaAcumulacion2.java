package practica4;

import java.util.concurrent.atomic.DoubleAdder;

public class MiHebraUnaAcumulacion2 extends Thread{
	int miId, numHebras;
	long numRectangulos;
	DoubleAdder da;
	
	MiHebraUnaAcumulacion2(int miId, int numHebras, long numRectangulos, DoubleAdder da){
		this.miId = miId;
		this.numHebras = numHebras;
		this.numRectangulos = numRectangulos;
		this.da = da;
	}
	
	public void run(){
		double x, suma = 0.0, baseRectangulo = 1.0 / ((double)numRectangulos);
		for (long i = miId; i<numRectangulos; i+=numHebras){
			x = baseRectangulo * (((double)i)+0.5);
			suma += EjemploNumeroPI1a.f(x);
		}
		da.add(suma);
	}
}