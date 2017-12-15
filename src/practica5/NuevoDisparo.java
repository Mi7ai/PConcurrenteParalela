package practica5;

public class NuevoDisparo {

	private volatile double velocidad;
	private volatile double angulo;
	
	NuevoDisparo(double velocidad, double angulo){
		this.velocidad = velocidad;
		this.angulo = angulo;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public double getAngulo() {
		return angulo;
	}

	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}
	
	
}
