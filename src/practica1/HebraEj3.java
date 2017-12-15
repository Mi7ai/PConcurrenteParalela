package practica1;

public class HebraEj3 extends Thread{
	
	int miId;
	CuentaIncrementos1a incremento;
	
	public HebraEj3(int id, CuentaIncrementos1a incremento){
		this.miId = id;
		this.incremento = incremento;
	}
	
	@Override
	public void run(){
		System.out.println("Hebra "+miId+" empieza ha incrementar.");
		for (int i = 0; i<1000000; i++){
			incremento.incrementaContador();
		}
		System.out.println("Hebra "+miId+" ha acabado.");
	}

}
