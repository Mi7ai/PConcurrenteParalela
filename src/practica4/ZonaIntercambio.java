package practica4;

public class ZonaIntercambio {
	
	volatile long tiempo;
	
	public ZonaIntercambio(){
		tiempo = 500;
	}
	
	public void setTiempo(long t){
		tiempo=t;
	}
	
	public long getTiempo(){
		return tiempo;
	}
}
