package practica1;

// ============================================================================
class CuentaIncrementos1a {
// ============================================================================
  long contador = 0;

  // --------------------------------------------------------------------------
  void incrementaContador() {
    contador++;
  }

  // --------------------------------------------------------------------------
  long dameContador() {
    return( contador );
  }
}


// ============================================================================
class EjemploIncrementos1a {
// ============================================================================

  // --------------------------------------------------------------------------
  public static void main( String args[] ) {
    int  numHebras;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 1 ) {
      System.err.println( "Uso: java programa <numHebras>" );
      System.exit( -1 );
    }
    try {
      numHebras = Integer.parseInt( args[ 0 ] );
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }

    System.out.println( "numHebras: " + numHebras );
    
    CuentaIncrementos1a incrementos = new CuentaIncrementos1a();
    Thread[] threads = new HebraEj3[numHebras];
    
    System.out.println("Contador: "+incrementos.dameContador());
    
    for (int i=0; i<numHebras; i++){
    	threads[i] = new HebraEj3(i,incrementos);
    	threads[i].start();
    }
    
    for (int i=0; i<numHebras; i++){
    	try{
    		threads[i].join();
    	}catch(InterruptedException e){
    		e.printStackTrace();
    	}
    }
    
    System.out.println("Contador: "+incrementos.dameContador());
  }
}

