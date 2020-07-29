package chat.participante;

import java.io.*;
import util.*;
import util.constants.Estados;

/**
 *Representa un objeto que se pondra a escuchar de manera indefinida en el
 * Buffer de entrada de un Socket.Útil para el cliente de Chat
 * @author BERKAY
 */
public class Escuchador implements Runnable {

    //Atributos
    protected Participante mParticipante;
    protected boolean mSeguir;


    //Constructores
    public Escuchador (Participante p) {
        mParticipante = p ;
        mSeguir          = true ;
        
        //Creo el Thread que se pondrá a la escucha en paralelo
        Thread th = new Thread (this) ;
        th.start() ;
              
    }
    
    //Propiedades (get-set)
    /**
     * @return the mParticipante
     */
    public Participante getParticipante() {
        return mParticipante;
    }

    /**
     * @return the mSeguir
     */
    public boolean Seguir() {
        return mSeguir;
    }
    
    //Metodos Públicos
    public void parar() {
        mSeguir = false ;
        getParticipante().setEstado(Estados.NO_CONECTADO);
    }
    
    @Override
    public void run() {
        while(Seguir()) {     //while(mSeguir) = while(mSeguir == true);
            //Leemos lineas
            recibeMensaje() ;
            
        }
    }
    
    //Metodos Privados
    private void recibeMensaje () {
        
        // 1- Se esperará en el inputStreamm del socket a recibir una linea
        
        // Obtengo el inputStream del Socket del Participante 
        InputStream entrada = getParticipante ().getEntrada() ;
        
        //Espero a leer una linea de él
        try {
               String msgRecibido = IO.leeLinea(entrada) ;
               Utilidades.guardarMensaje(msgRecibido);
            
            // 2- Dependiendo de lo que tenga esa linea, actuará
            // Escribir en consola
            // Notificar que sale

            
            //Escribo el mensaje en consola
            System.out.print(msgRecibido +"\nS-CHAT(" + getParticipante().getNick() + ")#>"); ;
            
            // Podremos hacer mas acceciones con esa linea
            // ...
            if (msgRecibido.startsWith("$") ) {
                
                 // El mensaje recibido es un comando

            }
            
        } catch (IOException ex) {
            // System.out.println (" Error en Escuchador.recibeMensaje : " + ex.getMessage());
            getParticipante().parar() ;
        }
        
    }

    
}
