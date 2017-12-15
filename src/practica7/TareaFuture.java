package practica7;

import java.util.concurrent.Callable;

public class TareaFuture implements Callable<PuebloMaximaMinima>{
	
	int codPueblo;
	String fecha;
	PuebloMaximaMinima maxMin;
	
	public TareaFuture(int codPueblo, String fecha) {
		super();
		this.codPueblo = codPueblo;
		this.fecha = fecha;
		this.maxMin = new PuebloMaximaMinima();
	}

	public PuebloMaximaMinima call(){
		EjemploTemperaturaProvincia.ProcesaPueblo(fecha, codPueblo, maxMin, false);
		return maxMin;
	}

}
