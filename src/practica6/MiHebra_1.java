package practica6;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MiHebra_1 extends Thread{
	
	private int miId, numHebras;
	private Map<String,Integer> hmCuentaPalabras;
	private Vector<String> vectorLineas;
	
	public MiHebra_1(int miId, int numHebras, Map<String, Integer> hmCuentaPalabras, Vector<String> vectorLineas) {
		super();
		this.miId = miId;
		this.numHebras = numHebras;
		this.hmCuentaPalabras = hmCuentaPalabras;
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
		        	contabilizaPalabra( hmCuentaPalabras, palabraActual );
		        }
		    }
		}
	}
	
	public void contabilizaPalabra( 
            Map<String,Integer> cuentaPalabras,
            String palabra ) {
		synchronized(cuentaPalabras){
			Integer numVeces = cuentaPalabras.get( palabra );
			if( numVeces != null ) {
				cuentaPalabras.put( palabra, numVeces+1 );
			} else {
				cuentaPalabras.put( palabra, 1 );
				
			}	
		}
	} 
}
