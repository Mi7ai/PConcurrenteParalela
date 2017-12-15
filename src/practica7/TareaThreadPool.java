package practica7;

public class TareaThreadPool implements Runnable{

	int codPueblo;
	String fecha;
	PuebloMaximaMinima maxMin;
	
	public TareaThreadPool(int codPueblo, String fecha, PuebloMaximaMinima maxMin) {
		super();
		this.codPueblo = codPueblo;
		this.fecha = fecha;
		this.maxMin = maxMin;
	}
	
	public void run() {
		EjemploTemperaturaProvincia.ProcesaPueblo(fecha, codPueblo, maxMin, false);
	}
	
}
