package practica5;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MiHebraCalculadora2 extends Thread{
	
	private ArrayList<Proyectil1a> listaP;
	private LinkedBlockingQueue<NuevoDisparo> lbq;
	private CanvasCampoTiro1a  cnvCampoTiro;
	private JTextField         txfMensajes;
	
	MiHebraCalculadora2(LinkedBlockingQueue<NuevoDisparo> lbq, CanvasCampoTiro1a  cnvCampoTiro, JTextField txfMensajes){
		this.lbq = lbq;
		this.listaP = new ArrayList<>();
		this.cnvCampoTiro = cnvCampoTiro;
		this.txfMensajes = txfMensajes;
	}
	
	public void run(){
		NuevoDisparo nuevoDisparo;
		while(true){
			while(listaP.isEmpty() || !lbq.isEmpty()){
				try{
					nuevoDisparo = lbq.take();
					Proyectil1a nuevoProyectil = new Proyectil1a(nuevoDisparo.getVelocidad(),nuevoDisparo.getAngulo());
					listaP.add(nuevoProyectil);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			int i=0;
			while(i<listaP.size()){
				Proyectil1a p=listaP.get(i);
	            p.muestra();        
	            p.mueveDuranteUnIncremental( cnvCampoTiro.getObjetivoX(),
	                                        cnvCampoTiro.getObjetivoY() ); 
	         // Dibuja el proyectil.
                p.dibujaProyectil( cnvCampoTiro ); 
                if( p.getEstadoProyectil() != 0 ) {
		            try {
		        		SwingUtilities.invokeAndWait(new Runnable() {
		            		public void run() {
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
		            	}); 
		        	}catch(InterruptedException e) {
		        		e.printStackTrace();
		        	}catch(InvocationTargetException e) {
		        		e.printStackTrace();
		        	}
		            listaP.remove(p);
                }else{
                	i++;
                }
			}
		}
	}

}
