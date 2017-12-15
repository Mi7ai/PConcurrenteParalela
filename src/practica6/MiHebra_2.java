package practica6;


import java.util.Hashtable;
import java.util.Vector;

public class MiHebra_2 extends Thread{

	private int miId, numHebras;
	private Hashtable<String,Integer> htCuentaPalabras;
	private Vector<String> vectorLineas;
	
	public MiHebra_2(int miId, int numHebras, Hashtable<String, Integer> htCuentaPalabras, Vector<String> vectorLineas) {
		super();
		this.miId = miId;
		this.numHebras = numHebras;
		this.htCuentaPalabras = htCuentaPalabras;
		this.vectorLineas = vectorLineas;
	}
	
	public void run() {
		String palabraActual;
		for(int i=miId; i<vectorLineas.size(); i+=numHebras) {
			String[] palabras = vectorLineas.get( i ).split( "\\W+" );
		    for( int j = 0; j < palabras.length; j++ ) {
		        // Procesa cada palabra de la linea "i", si es distinta de blancos.
		        palabraActual = palabras[ j ].trim();
		        if( palabraActual.length() > 0 ) {
		        	contabilizaPalabra( htCuentaPalabras, palabraActual );
		        }
		    }
		}
	}
	
	public void contabilizaPalabra( 
            Hashtable<String,Integer> cuentaPalabras,
            String palabra ) {
		synchronized(cuentaPalabras) {
			Integer numVeces = cuentaPalabras.get( palabra );
			if( numVeces != null ) {
				cuentaPalabras.put( palabra, numVeces+1 );
			} else {
				cuentaPalabras.put( palabra, 1 );
			}
		}
   //cuentaPalabras.merge(palabra, 1, (oldVal, newVal) -> oldVal+newVal);
	} 
}
