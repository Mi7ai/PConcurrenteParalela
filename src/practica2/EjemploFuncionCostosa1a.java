package practica2;
// ============================================================================
class EjemploFuncionCostosa1a {
// ============================================================================

  // --------------------------------------------------------------------------
  public static void main( String args[] ) {
    int     n, numHebras;
    long    t1, t2, t1Ciclica, t2Ciclica, t1Bloques, t2Bloques;
    double  tt, ttCiclica, ttBloques, sumaX, sumaY;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.err.println( "Uso: java programa <numHebras> <tamanyo>" );
      System.exit( -1 );
    }
    try {
      numHebras = Integer.parseInt( args[ 0 ] );
      n         = Integer.parseInt( args[ 1 ] );
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      n         = -1;
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }

    // Crea los vectores.
    double vectorX[] = new double[ n ];
    double vectorY[] = new double[ n ];

    //
    // Implementacion secuencial.
    //
    inicializaVectorX( vectorX );
    inicializaVectorY( vectorY );
    t1 = System.nanoTime();
    for( int i = 0; i < n; i++ ) {
      vectorY[ i ] = evaluaFuncion( vectorX[ i ] );
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Tiempo secuencial (seg.):                    " + tt );
    ////imprimeResultado( vectorX, vectorY );
    // Comprueba el resultado. 
    sumaX = sumaVector( vectorX );
    sumaY = sumaVector( vectorY );
    System.out.println( "Suma del vector X:          " + sumaX );
    System.out.println( "Suma del vector Y:          " + sumaY );
    
    
    //
    //Implementacion paralela ciclica
    //
    inicializaVectorX( vectorX );
    inicializaVectorY( vectorY );
    t1Ciclica = System.nanoTime();
    
    
    Thread[] threads = new Hebra2Ciclica[numHebras];
    
    
    for (int i=0; i<numHebras; i++) {
    	threads[i] = new Hebra2Ciclica(i, numHebras, n, vectorX, vectorY);
    	threads[i].start();
    }

    for (int i=0; i<numHebras; i++) {
    	try {
    		threads[i].join();
    	}catch(InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    t2Ciclica = System.nanoTime();
    ttCiclica = ( ( double ) ( t2Ciclica - t1Ciclica ) ) / 1.0e9;
    System.out.println( "Tiempo paralelo (seg.):                    " + ttCiclica );
    ////imprimeResultado( vectorX, vectorY );
    // Comprueba el resultado. 
    sumaX = sumaVector( vectorX );
    sumaY = sumaVector( vectorY );
    System.out.println( "Suma del vector X:          " + sumaX );
    System.out.println( "Suma del vector Y:          " + sumaY );
    
    System.out.println( "Incremento ciclica:    " + tt/ttCiclica );
    
    //
    //Implementacion paralela bloques
    //
    inicializaVectorX( vectorX );
    inicializaVectorY( vectorY );
    t1Bloques = System.nanoTime();
    
    
    threads = new Hebra2Bloques[numHebras];
    
    
    for (int i=0; i<numHebras; i++) {
    	threads[i] = new Hebra2Bloques(i, numHebras, n, vectorX, vectorY);
    	threads[i].start();
    }

    for (int i=0; i<numHebras; i++) {
    	try {
    		threads[i].join();
    	}catch(InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    t2Bloques = System.nanoTime();
    ttBloques = ( ( double ) ( t2Bloques - t1Bloques ) ) / 1.0e9;
    System.out.println( "Tiempo paralelo (seg.):                    " + ttBloques );
    ////imprimeResultado( vectorX, vectorY );
    // Comprueba el resultado. 
    sumaX = sumaVector( vectorX );
    sumaY = sumaVector( vectorY );
    System.out.println( "Suma del vector X:          " + sumaX );
    System.out.println( "Suma del vector Y:          " + sumaY );
    
    System.out.println( "Incremento bloques:    " + tt/ttBloques );
    
    System.out.println( "Fin del programa." );
  }

  // --------------------------------------------------------------------------
  static void inicializaVectorX( double vectorX[] ) {
    if( vectorX.length == 1 ) {
      vectorX[ 0 ] = 0.0;
    } else {
      for( int i = 0; i < vectorX.length; i++ ) {
        vectorX[ i ] = 10.0 * ( double ) i / ( ( double ) vectorX.length - 1 );
      }
    }
  }

  // --------------------------------------------------------------------------
  static void inicializaVectorY( double vectorY[] ) {
    for( int i = 0; i < vectorY.length; i++ ) {
      vectorY[ i ] = 0.0;
    }
  }

  // --------------------------------------------------------------------------
  static double sumaVector( double vector[] ) {
    double  suma = 0.0;
    for( int i = 0; i < vector.length; i++ ) {
      suma += vector[ i ];
    }
    return suma;
  }

  // --------------------------------------------------------------------------
  static double evaluaFuncion( double x ) {
    return Math.sin( Math.exp( -x ) + Math.log( 1 + x ) );
  }

  // --------------------------------------------------------------------------
  static void imprimeVector( double vector[] ) {
    for( int i = 0; i < vector.length; i++ ) {
      System.out.println( " vector[ " + i + " ] = " + vector[ i ] );
    }
  }

  // --------------------------------------------------------------------------
  static void imprimeResultado( double vectorX[], double vectorY[] ) {
    for( int i = 0; i < Math.min( vectorX.length, vectorY.length ); i++ ) {
      System.out.println( "  i: " + i + 
                          "  x: " + vectorX[ i ] +
                          "  y: " + vectorY[ i ] );
    }
  }

}

