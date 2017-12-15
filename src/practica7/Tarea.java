package practica7;

public class Tarea {
	
	private boolean esVeneno;
	private int codPueblo;
	
	public Tarea(boolean esVeneno, int codPueblo) {
		super();
		this.esVeneno = esVeneno;
		this.codPueblo = codPueblo;
	}

	public boolean isEsVeneno() {
		return esVeneno;
	}


	public int getCodPueblo() {
		return codPueblo;
	}

}
