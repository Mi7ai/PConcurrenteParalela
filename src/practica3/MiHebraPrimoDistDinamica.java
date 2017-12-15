package practica3;

import java.util.concurrent.atomic.AtomicInteger;

public class MiHebraPrimoDistDinamica extends Thread{
	
	private AtomicInteger ai;
	long[] vector;
	
	public MiHebraPrimoDistDinamica(AtomicInteger ai, long[] vector) {
		super();
		this.ai = ai;
		this.vector = vector;
	}
	
	public void run(){
		int i = ai.getAndIncrement();
		while (i<vector.length){
			if (EjemploMuestraPrimosEnVector2a.esPrimo(vector[i])) {
				System.out.println("Encontrado primo: "+vector[i]);
			}
			i = ai.getAndIncrement();
		}
	}

}
