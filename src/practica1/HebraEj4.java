package practica1;

public class HebraEj4 extends Thread{
	
	long numero;
	 
	public HebraEj4(long n){
		 this.numero = n; 
	}
	
	public void run(){
		
		System.out.println( "Examinando numero: " + numero );
		if(GUIPrimoSencillo1a.esPrimo(numero)) {
            System.out.println( "El numero " + numero + " SI es primo." );
          } else {
            System.out.println( "El numero " + numero + " NO es primo." );
          }
	}

}
