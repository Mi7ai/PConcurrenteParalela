package practica7;

import java.util.concurrent.LinkedBlockingQueue;

public class Consumidora extends Thread{
	
	LinkedBlockingQueue<Tarea> lbq;
	String fecha;
	PuebloMaximaMinima maxMin;
	
	public Consumidora(LinkedBlockingQueue<Tarea> lbq, String fecha, PuebloMaximaMinima maxMin) {
		super();
		this.lbq = lbq;
		this.fecha = fecha;
		this.maxMin = maxMin;
	}
	
	public void run() {
		Tarea tarea;
		try {
			while(!(tarea = lbq.take()).isEsVeneno()) {
				EjemploTemperaturaProvincia.ProcesaPueblo(fecha, tarea.getCodPueblo(), maxMin, false);
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}	
}
