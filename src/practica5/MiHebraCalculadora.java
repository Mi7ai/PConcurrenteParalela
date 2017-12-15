package practica5;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MiHebraCalculadora extends Thread{

	CanvasCampoTiro1a  cnvCampoTiro;
	JTextField         txfMensajes;
	Proyectil1a p;
	
	public MiHebraCalculadora(CanvasCampoTiro1a cnvCampoTiro, JTextField txfMensajes, Proyectil1a p) {
		super();
		this.cnvCampoTiro = cnvCampoTiro;
		this.txfMensajes = txfMensajes;
		this.p = p;
	}
	
	public void run() {
		while( p.getEstadoProyectil() == 0 )  {
            // Muestra en pantalla los datos del proyectil p.
            p.muestra();        
            // Mueve el proyectil durante un incremental de tiempo.
            p.mueveDuranteUnIncremental( cnvCampoTiro.getObjetivoX(),
                                        cnvCampoTiro.getObjetivoY() );            
               
            // Comprueba si el proyectil ha impactado contra el suelo o no.
            
              // El proyectil ha impactado contra el suelo.
              // Construye y muestra mensaje adecuado.
        	try {
        		SwingUtilities.invokeAndWait(new Runnable() {
            		public void run() {
            			// Dibuja el proyectil.
                        p.dibujaProyectil( cnvCampoTiro );   
                        if( p.getEstadoProyectil() != 0 ) {
                			String mensaje;
                			if( p.getEstadoProyectil() == 2 ) {
                                mensaje = "Destruido!";
                            } else {
                                mensaje = "Fallado. El objetivo esta en: " +
                                cnvCampoTiro.getObjetivoX() +
                                "  Has disparado a: " + p.getIntPosX();
                            }
                            txfMensajes.setText( mensaje );
                        }
            		}          		
            	}); 
        	}catch(InterruptedException e) {
        		e.printStackTrace();
        	}catch(InvocationTargetException e) {
        		e.printStackTrace();
        	}
            	
            
          }
	}
	
	
}
