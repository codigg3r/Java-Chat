package chat.servidor;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * 
 * Representa el Servidor de Chat que esperará nuevas conexiones y las añadirá
 * al Gestor de Clientes/Participantes
 * 
 * @author BERKAY
 */
public class ServidorMiniChat {
    //Constantes
     public static final int PUERTO_DEFECTO = 1234;
    
    //Atributos
    protected GestorParticipantes   mGestor;        // elGestor
    protected GestorSubsala         mSubsala;        // elSubsala
    protected ServerSocket          mServerSocket;  // ss
    protected int                   mPuerto;        // Represente el puerto en el que el servidor se pone a la escucha
    
    //Contructores
    public ServidorMiniChat () {
         mPuerto = PUERTO_DEFECTO;
         mGestor =  new GestorParticipantes () ;
         mSubsala = new GestorSubsala();
    }
    
     //Propiedades
    /**
     * @return the mGestor
     */
    public GestorParticipantes getGestor() {
        return mGestor;
    }

    /**
     * @return the mServerSocket
     */
    public ServerSocket getServerSocket() {
        return mServerSocket;
    }

    /**
     * @return the mPuerto
     */
    public int getPuerto() {
        return mPuerto;
    }
    
    
    //Metodos publicos
    
    /**
     * 
     * Inicializará el Servidor y se pondrá a la escucha
     * @throws java.lang.Exception
     */
    public void RunServidorChat() throws Exception  {
        //Inicializa el ServidorSocket en elpuerto indicado
         try {
                mServerSocket = new ServerSocket(getPuerto()) ;
                
                // Imprimimos un mensaje al usuario de nuestra ip y localHost y puerto al que se pueden conectar
                System.out.println("*****************************************************");
                System.out.println("Servidor MiniChat   ");
                System.out.println("*****************************************************");
                System.out.println ("           " + InetAddress.getLocalHost().getHostAddress()   + ":" + getPuerto()) ;
                System.out.println ("           " + InetAddress.getLocalHost().getHostName()       + ":" + getPuerto()) ; 
                System.out.println("*****************************************************");
                
         } catch (IOException ex) {
             System.out.println("Error en RunServidorChat. ServerSocket: " + ex.getMessage());
             return ;
         }
         
         
         //Buckle infinito que acepta Clientes
         while (true) { // repetir por siempre
             Socket s = null ;
             
             //Esperar a que se conecte otro cliente al servidor y
              //Devuelve el socket de esa conexion
             try {
                 
                  s = mServerSocket.accept() ;   
                  
                  System.out.println (" Nuevo cliente de chat conectado! ") ;
             } 
             catch (IOException ex) {
                       System.out.println("Error en RunServidorChat. LOOP: " + ex.getMessage());
             }
            if ( s != null) {
                            new ParticipanteProxy (s, mGestor, mSubsala) ;
             }
         }
         
    } //Metedos privados

    
    
    // Main
    public static void main (String [] args) throws Exception {
        // Crearé una nueva instancia del Servidor de Chat
        ServidorMiniChat servidor = new ServidorMiniChat() ;
        
        // Y la inicializaré
        servidor.RunServidorChat() ;
        
     }    
}
