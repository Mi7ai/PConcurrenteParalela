package practica4;

import java.util.concurrent.atomic.DoubleAdder;

public class MiHebraMultAcumulaciones2 extends Thread{
	int miId, numHebras;
	long numRectangulos;
	DoubleAdder da;
	
	MiHebraMultAcumulaciones2(int miId, int numHebras, long numRectangulos, DoubleAdder da){
		this.miId = miId;
		this.numHebras = numHebras;
		this.numRectangulos = numRectangulos;
		this.da = da;
	}
	
	public void run(){
		double x, baseRectangulo = 1.0 / ((double)numRectangulos);
		for (long i = miId; i<numRectangulos; i+=numHebras){
			x = baseRectangulo * (((double)i)+0.5);
			da.add(EjemploNumeroPI1a.f(x));
		}
	}
}