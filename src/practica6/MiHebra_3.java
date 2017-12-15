package practica6;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class MiHebra_3 extends Thread{
	
	private int miId, numHebras;
	private ConcurrentHashMap<String,Integer> chmCuentaPalabras;
	private Vector<String> vectorLineas;
	
	public MiHebra_3(int miId, int numHebras, ConcurrentHashMap<String, Integer> chmCuentaPalabras, Vector<String> vectorLineas) {
		super();
		this.miId = miId;
		this.numHebras = numHebras;
		this.chmCuentaPalabras = chmCuentaPalabras;
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
		        	contabilizaPalabra( chmCuentaPalabras, palabraActual );
		        }
		    }
		}
	}
	
	public void contabilizaPalabra( 
            ConcurrentHashMap<String,Integer> cuentaPalabras,
            String palabra ) {
		synchronized(cuentaPalabras) {
			Integer numVeces = cuentaPalabras.get( palabra );
			if( numVeces != null ) {
				cuentaPalabras.put( palabra, numVeces+1 );
			} else {
				cuentaPalabras.put( palabra, 1 );
			}
		}
	} 
}
