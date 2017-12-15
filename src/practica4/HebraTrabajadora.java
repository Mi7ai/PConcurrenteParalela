package practica4;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class HebraTrabajadora extends Thread{
	
	JTextField  txfMensajes;
	ZonaIntercambio zona;
	volatile boolean seguir;
	
	public HebraTrabajadora(JTextField  txfMensajes, ZonaIntercambio zona){
		super();
		this.txfMensajes = txfMensajes;
		this.zona = zona;
		seguir=true;
	}
	
	public void run(){
		long i=1;
		while (seguir){
			if (GUISecuenciaPrimos1a.esPrimo(i)){
				final long numero=i;
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						txfMensajes.setText(String.valueOf(numero));
					}
				});
				try{
					Thread.sleep(zona.getTiempo());
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			i++;
		}
	}
	
	public void para(){
		seguir = false;
	}
	
}
