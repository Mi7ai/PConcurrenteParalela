package practica6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class Ejer_4 {
	public static void main( String args[] ) {
	    long                     t1, t2;
	    double                   tt, ttchm2;
	    int                      numHebras;
	    String                   nombreFichero, palabraActual;
	    Vector<String>           vectorLineas;
	    HashMap<String,Integer> hmCuentaPalabras;
	    ConcurrentHashMap<String,Integer> chm2CuentaPalabras;

	    // Comprobacion y extraccion de los argumentos de entrada.
	    if( args.length != 2 ) {
	      System.err.println( "Uso: java programa <numHebras> <fichero>" );
	      System.exit( -1 );
	    }
	    try {
	      numHebras     = Integer.parseInt( args[ 0 ] );
	      nombreFichero = args[ 1 ];
	    } catch( NumberFormatException ex ) {
	      numHebras = -1;
	      nombreFichero = "";
	      System.out.println( "ERROR: Argumentos numericos incorrectos." );
	      System.exit( -1 );
	    }

	    // Lectura y carga de lineas en "vectorLineas".
	    vectorLineas = readFile( nombreFichero );
	    System.out.println( "Numero de lineas leidas: " + vectorLineas.size() );
	    System.out.println();

	    //
	    // Implementacion secuencial sin temporizar.
	    //
	    hmCuentaPalabras = new HashMap<String,Integer>( 1000, 0.75F );
	    for( int i = 0; i < vectorLineas.size(); i++ ) {
	      // Procesa la linea "i".
	      String[] palabras = vectorLineas.get( i ).split( "\\W+" );
	      for( int j = 0; j < palabras.length; j++ ) {
	        // Procesa cada palabra de la linea "i", si es distinta de blancos.
	        palabraActual = palabras[ j ].trim();
	        if( palabraActual.length() > 0 ) {
	          contabilizaPalabra( hmCuentaPalabras, palabraActual );
	        }
	      }
	    }

	    //
	    // Implementacion secuencial.
	    //
	    t1 = System.nanoTime();
	    hmCuentaPalabras = new HashMap<String,Integer>( 1000, 0.75F );
	    for( int i = 0; i < vectorLineas.size(); i++ ) {
	      // Procesa la linea "i".
	      String[] palabras = vectorLineas.get( i ).split( "\\W+" );
	      for( int j = 0; j < palabras.length; j++ ) {
	        // Procesa cada palabra de la linea "i", si es distinta de blancos.
	        palabraActual = palabras[ j ].trim();
	        if( palabraActual.length() > 0 ) {
	          contabilizaPalabra( hmCuentaPalabras, palabraActual );
	        }
	      }
	    }
	    t2 = System.nanoTime();
	    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
	    System.out.print( "Implemen. secuencial: " );
	    imprimePalabraMasUsadaYVeces( hmCuentaPalabras );
	    System.out.println( " Tiempo(s): " + tt );
	    System.out.println( "Num. elems. tabla hash: " + hmCuentaPalabras.size() );
	    System.out.println();
	    
	    //
	    //Implementacion paralela ConcurrentHashMap reintentos
	    //
	    
	    MiHebra_4[] vT = new MiHebra_4[numHebras];
	    t1 = System.nanoTime();
	    chm2CuentaPalabras = new ConcurrentHashMap<String,Integer>( 1000, 0.75F );
	    for (int i=0; i<numHebras; i++) {
	    	vT[i] = new MiHebra_4(i,numHebras, chm2CuentaPalabras, vectorLineas);
	    	vT[i].start();
	    }
	    
	    for (int i=0; i<numHebras; i++) {
	    	try {
	    		vT[i].join();
	    	}catch(InterruptedException ex) {
	    		ex.printStackTrace();
	    	}
	    }
	    
	    t2 = System.nanoTime();
	    ttchm2 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
	    System.out.print( "Implemen. paralela ConcurrentHashMap reintentos: " );
	    imprimePalabraMasUsadaYVeces( chm2CuentaPalabras );
	    System.out.println( " Tiempo(s): " + ttchm2 );
	    System.out.println( "Num. elems. tabla hash: " + chm2CuentaPalabras.size() );
	    System.out.println();

	    System.out.println( "Fin de programa." );
	  }

	  // -------------------------------------------------------------------------
	  public static Vector<String> readFile( String fileName ) {
	    BufferedReader br; 
	    String         linea;
	    Vector<String> data = new Vector<String>();

	    try {
	      br = new BufferedReader( new FileReader( fileName ) );
	      while( ( linea = br.readLine() ) != null ) {
	        //// System.out.println( "Leida linea: " + linea );
	        data.add( linea );
	      }
	      br.close(); 
	    } catch( FileNotFoundException ex ) {
	      ex.printStackTrace();
	    } catch( IOException ex ) {
	      ex.printStackTrace();
	    }
	    return data;
	  }

	  // -------------------------------------------------------------------------
	  public static void contabilizaPalabra( 
	                         HashMap<String,Integer> cuentaPalabras,
	                         String palabra ) {
	    Integer numVeces = cuentaPalabras.get( palabra );
	    if( numVeces != null ) {
	      cuentaPalabras.put( palabra, numVeces+1 );
	    } else {
	      cuentaPalabras.put( palabra, 1 );
	    }
	  }   

	  // --------------------------------------------------------------------------
	  static void imprimePalabraMasUsadaYVeces(
	                  Map<String,Integer> cuentaPalabras ) {
	    Vector<Map.Entry> lista = 
	        new Vector<Map.Entry>( cuentaPalabras.entrySet() );

	    String palabraMasUsada = "";
	    int    numVecesPalabraMasUsada = 0;
	    // Calcula la palabra mas usada.
	    for( int i = 0; i < lista.size(); i++ ) {
	      String palabra = ( String ) lista.get( i ).getKey();
	      int numVeces = ( Integer ) lista.get( i ).getValue();
	      if( i == 0 ) {
	        palabraMasUsada = palabra;
	        numVecesPalabraMasUsada = numVeces;
	      } else if( numVecesPalabraMasUsada < numVeces ) {
	        palabraMasUsada = palabra;
	        numVecesPalabraMasUsada = numVeces;
	      }
	    }
	    // Imprime resultado.
	    System.out.print( "( Palabra: '" + palabraMasUsada + "' " + 
	                         "veces: " + numVecesPalabraMasUsada + " )" );
	  }

	  // --------------------------------------------------------------------------
	  static void printCuentaPalabrasOrdenadas(
	                  HashMap<String,Integer> cuentaPalabras ) {
	    int             i, numVeces;
	    List<Map.Entry> list = new Vector<Map.Entry>( cuentaPalabras.entrySet() );

	    // Ordena por valor.
	    Collections.sort( 
	        list,
	        new Comparator<Map.Entry>() {
	            public int compare( Map.Entry e1, Map.Entry e2 ) {
	              Integer i1 = ( Integer ) e1.getValue();
	              Integer i2 = ( Integer ) e2.getValue();
	              return i2.compareTo( i1 );
	            }
	        }
	    );
	    // Muestra contenido.
	    i = 1;
	    System.out.println( "Veces Palabra" );
	    System.out.println( "-----------------" );
	    for( Map.Entry e : list ) {
	      numVeces = ( ( Integer ) e.getValue () ).intValue();
	      System.out.println( i + " " + e.getKey() + " " + numVeces );
	      i++;
	    }
	    System.out.println( "-----------------" );
	  }
}
