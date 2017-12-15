package practica6;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MiHebra_6 extends Thread{
	
	private int miId, numHebras;
	private ConcurrentHashMap<String,AtomicInteger> chmCuentaPalabras;
	private Vector<String> vectorLineas;
	
	public MiHebra_6(int miId, int numHebras, ConcurrentHashMap<String, AtomicInteger> chmCuentaPalabras, Vector<String> vectorLineas) {
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
            ConcurrentHashMap<String,AtomicInteger> cuentaPalabras,
            String palabra ) {
		AtomicInteger cuentaActual;
		
		cuentaActual = cuentaPalabras.putIfAbsent(palabra, new AtomicInteger(1));
		if (cuentaActual != null){
				cuentaActual.incrementAndGet();	
		}
	} 
}
