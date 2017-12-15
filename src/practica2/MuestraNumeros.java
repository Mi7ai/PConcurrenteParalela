package practica2;

public class MuestraNumeros {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numHebras, n;
		
		if (args.length != 2) {
			System.err.println("Uso: java programa <numHebras> <n>");
			System.exit(-1);
		}
		
		try {
			numHebras = Integer.parseInt(args[0]);
			n = Integer.parseInt(args[1]);
		}catch(NumberFormatException e) {
			numHebras = n = -1;			
			System.err.println("ERROR: argumentos numericos incorrectos");
			System.exit(-1);
		}
		
		//Reparto ciclico
		//Thread[] threads = new Hebra1Ciclica[numHebras];
		
		//Reparto por bloques
		Thread[] threads = new Hebra1Bloques[numHebras];
		
		for (int i=0; i<numHebras; i++) {
			//Reparto ciclico
			//threads[i] = new Hebra1Ciclica(i, numHebras, n);
			
			//Reparto bloques
			threads[i] = new Hebra1Bloques(i, numHebras, n);
			
			threads[i].start();
		}
	}

}
