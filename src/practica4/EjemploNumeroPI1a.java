package practica4;

import java.util.concurrent.atomic.DoubleAdder;

// ===========================================================================
class Acumula {
// ===========================================================================
  double  suma;

  // -------------------------------------------------------------------------
  Acumula() {
    // ...
	  suma=0;
  }

  // -------------------------------------------------------------------------
  synchronized void acumulaDato( double dato ) {
    // ...
	  suma+=dato;
  }

  // -------------------------------------------------------------------------
  synchronized double dameDato() {
    // ...
	  return suma;
  }
}
/*
// ===========================================================================
class MiHebraMultAcumulaciones1b extends Thread {
// ===========================================================================
  int      miId, numHebras;
  long     numRectangulos;
  Acumula  a;

  // -------------------------------------------------------------------------
  MiHebraMultAcumulaciones1b( int miId, int numHebras, long numRectangulos, 
                              Acumula a ) {
    // ...
  }

  // -------------------------------------------------------------------------
  public void run() {
    // ...
  }
}
*/

// ===========================================================================
class EjemploNumeroPI1a {
// ===========================================================================

  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    long                        numRectangulos;
    double                      baseRectangulo, x, suma, pi;
    int                         numHebras;
    Acumula                     a;
    long                        t1, t2, t1M1, t2M1;
    double                      tSec, tPar;

    // Comprobacion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.out.println( "ERROR: numero de argumentos incorrecto.");
      System.out.println( "Uso: java programa <numHebras> <numRectangulos>" );
      System.exit( -1 );
    }
    try {
      numHebras      = Integer.parseInt( args[ 0 ] );
      numRectangulos = Long.parseLong( args[ 1 ] );
    } catch( NumberFormatException ex ) {
      numHebras      = -1;
      numRectangulos = -1;
      System.out.println( "ERROR: Numeros de entrada incorrectos." );
      System.exit( -1 );
    }

    System.out.println();
    System.out.println( "Calculo del numero PI mediante integracion." );

    //
    // Calculo del numero PI de forma secuencial.
    //
    System.out.println();
    System.out.println( "Comienzo del calculo secuencial." );
    t1 = System.nanoTime();
    baseRectangulo = 1.0 / ( ( double ) numRectangulos );
    suma           = 0.0;
    for( long i = 0; i < numRectangulos; i++ ) {
      x = baseRectangulo * ( ( ( double ) i ) + 0.5 );
      suma += f( x );
    }
    pi = baseRectangulo * suma;
    t2 = System.nanoTime();
    tSec = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Version Secuencial. Numero PI: " + pi );
    System.out.println( "Tiempo transcurrido (s.):      " + tSec );

    //
    // Calculo del numero PI de forma paralela: 
    // Multiples acumulaciones por hebra.
    //
    
    System.out.println();
    System.out.println( "Comienzo del calculo paralelo multi." );
    t1M1 = System.nanoTime();
    a = new Acumula();
    
    MiHebraMultAcumulaciones1[] vH = new MiHebraMultAcumulaciones1[numHebras];
    for (int i=0; i<numHebras; i++) {
    	vH[i] = new MiHebraMultAcumulaciones1(i, numHebras, numRectangulos, a);
    	vH[i].start();
    }
    
    for (int i=0; i<numHebras; i++) {
    	try {
    		vH[i].join();
    	}catch(InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    pi = a.dameDato();
    t2M1 = System.nanoTime();
    tPar = ( ( double ) ( t2M1 - t1M1 ) ) / 1.0e9;
    System.out.println( "Version paralela multi. Numero PI: " + pi );
    System.out.println( "Tiempo transcurrido (s.):      " + tPar );
    System.out.println( "Incremento:    "+tSec/tPar);
    
    //
    // Calculo del numero PI de forma paralela: 
    // Una acumulacion por hebra.
    //
    
    System.out.println();
    System.out.println( "Comienzo del calculo paralelo uni." );
    t1M1 = System.nanoTime();
    a = new Acumula();
    
    MiHebraUnaAcumulacion1[] vH2 = new MiHebraUnaAcumulacion1[numHebras];
    for (int i=0; i<numHebras; i++) {
    	vH2[i] = new MiHebraUnaAcumulacion1(i, numHebras, numRectangulos, a);
    	vH2[i].start();
    }
    
    for (int i=0; i<numHebras; i++) {
    	try {
    		vH2[i].join();
    	}catch(InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    pi = a.dameDato();
    t2M1 = System.nanoTime();
    tPar = ( ( double ) ( t2M1 - t1M1 ) ) / 1.0e9;
    System.out.println( "Version paralela uni. Numero PI: " + pi );
    System.out.println( "Tiempo transcurrido (s.):      " + tPar );
    System.out.println( "Incremento:    "+tSec/tPar);
    
    //
    // Calculo del numero PI de forma paralela: 
    // Multiples acumulaciones por hebra, clase atomica.
    //
    
    System.out.println();
    System.out.println( "Comienzo del calculo paralelo multi con clase atomica." );
    t1M1 = System.nanoTime();
    DoubleAdder da = new DoubleAdder();
    
    MiHebraMultAcumulaciones2[] vH3 = new MiHebraMultAcumulaciones2[numHebras];
    for (int i=0; i<numHebras; i++) {
    	vH3[i] = new MiHebraMultAcumulaciones2(i, numHebras, numRectangulos, da);
    	vH3[i].start();
    }
    
    for (int i=0; i<numHebras; i++) {
    	try {
    		vH3[i].join();
    	}catch(InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    pi = da.doubleValue();
    t2M1 = System.nanoTime();
    tPar = ( ( double ) ( t2M1 - t1M1 ) ) / 1.0e9;
    System.out.println( "Version paralela multi clase atomica. Numero PI: " + pi );
    System.out.println( "Tiempo transcurrido (s.):      " + tPar );
    System.out.println( "Incremento:    "+tSec/tPar);
    
    
    //
    // Calculo del numero PI de forma paralela: 
    // Una acumulacion por hebra, clase atomica.
    //
    
    System.out.println();
    System.out.println( "Comienzo del calculo paralelo uni con clase atomica." );
    t1M1 = System.nanoTime();
    da = new DoubleAdder();
    
    MiHebraUnaAcumulacion2[] vH4 = new MiHebraUnaAcumulacion2[numHebras];
    for (int i=0; i<numHebras; i++) {
    	vH4[i] = new MiHebraUnaAcumulacion2(i, numHebras, numRectangulos, da);
    	vH4[i].start();
    }
    
    for (int i=0; i<numHebras; i++) {
    	try {
    		vH4[i].join();
    	}catch(InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    pi = da.doubleValue();
    t2M1 = System.nanoTime();
    tPar = ( ( double ) ( t2M1 - t1M1 ) ) / 1.0e9;
    System.out.println( "Version paralela uni clase atomica. Numero PI: " + pi );
    System.out.println( "Tiempo transcurrido (s.):      " + tPar );
    System.out.println( "Incremento:    "+tSec/tPar);

    System.out.println();
    System.out.println( "Fin de programa." );
  }

  // -------------------------------------------------------------------------
  static double f( double x ) {
    return ( 4.0/( 1.0 + x*x ) );
  }
}

