package practica3;

import java.util.concurrent.atomic.AtomicInteger;

// ===========================================================================
public class EjemploMuestraPrimosEnVector2a {
// ===========================================================================

  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    int     numHebras;
    long    t1, t2, t1C, t2C, t1B, t2B, t1D, t2D;
    double  tt, ttC, ttB, ttD;
    /*long    vectorNumeros[] = {
                200000033L, 200000039L, 200000051L, 200000069L, 
                200000081L, 200000083L, 200000089L, 200000093L, 
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L
            };*/
     long    vectorNumeros[] = {
                200000033L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000039L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000051L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000069L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000081L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000083L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000089L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000093L, 4L, 4L, 4L, 4L, 4L, 4L, 4L
            };

    
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
    

    //
    // Implementacion secuencial.
    //
    System.out.println( "" );
    System.out.println( "Implementacion secuencial." );
    t1 = System.nanoTime();
    for( int i = 0; i < vectorNumeros.length; i++ ) {
      if( esPrimo( vectorNumeros[ i ] ) ) {
        System.out.println( "  Encontrado primo: " + vectorNumeros[ i ] );
      }
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Tiempo secuencial (seg.):                    " + tt );
    
    //
    // Implementacion paralela ciclica
    //
    
    System.out.println( "" );
    System.out.println( "Implementacion paralela ciclica." );
    t1C = System.nanoTime();
    MiHebraPrimoDistCiclica[] vH = new MiHebraPrimoDistCiclica[numHebras];
    
    for (int i=0; i<numHebras; i++) {
    	vH[i] = new MiHebraPrimoDistCiclica(i,numHebras,vectorNumeros);
    	vH[i].start();
    }
    
    for (int i=0; i<numHebras; i++) {
    	try {
    		vH[i].join();
    	}catch(InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    t2C = System.nanoTime();
    ttC = ( ( double ) ( t2C - t1C ) ) / 1.0e9;
    System.out.println( "Tiempo paralelo ciclico (seg.):                    " + ttC );
    System.out.println( "Incremento ciclica:    " + tt/ttC );
    
    //
    // Implementacion paralela bloques
    //
    
    System.out.println( "" );
    System.out.println( "Implementacion paralela bloques." );
    t1B = System.nanoTime();
    MiHebraPrimoDistBloques[] vB = new MiHebraPrimoDistBloques[numHebras];
    
    for (int i=0; i<numHebras; i++) {
    	vB[i] = new MiHebraPrimoDistBloques(i,numHebras,vectorNumeros);
    	vB[i].start();
    }
    
    for (int i=0; i<numHebras; i++) {
    	try {
    		vB[i].join();
    	}catch(InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    t2B = System.nanoTime();
    ttB = ( ( double ) ( t2B - t1B ) ) / 1.0e9;
    System.out.println( "Tiempo paralelo bloques (seg.):                    " + ttB );
    System.out.println( "Incremento bloques:    " + tt/ttB );
    
    //
    // Implementacion paralela dinamica
    //
    
    System.out.println( "" );
    System.out.println( "Implementacion paralela dinamica." );
    t1D = System.nanoTime();
    MiHebraPrimoDistDinamica[] vD = new MiHebraPrimoDistDinamica[numHebras];
    AtomicInteger ai = new AtomicInteger(0);
    
    for (int i=0; i<numHebras; i++) {
    	vD[i] = new MiHebraPrimoDistDinamica(ai,vectorNumeros);
    	vD[i].start();
    }
    
    for (int i=0; i<numHebras; i++) {
    	try {
    		vD[i].join();
    	}catch(InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    t2D = System.nanoTime();
    ttD = ( ( double ) ( t2D - t1D ) ) / 1.0e9;
    System.out.println( "Tiempo paralelo dinamica (seg.):                    " + ttD );
    System.out.println( "Incremento dinamica:    " + tt/ttD );
  }

  // -------------------------------------------------------------------------
  static boolean esPrimo( long num ) {
    boolean primo;
    if( num < 2 ) {
      primo = false;
    } else {
      primo = true;
      long i = 2;
      while( ( i < num )&&( primo ) ) { 
        primo = ( num % i != 0 );
        i++;
      }
    }
    return( primo );
  }
}

