package chat.servidor;

/**
 * 
 * Excepcion que indica un error cuando un Nick ya está  en el Gestor de Participantes
 * 
 * @author BERKAY
 */
public class NickEnUsoException extends Exception {
    
    //Constructor
    public NickEnUsoException(String mensaje) {
        super(mensaje) ;
    } // ()
    
} // class
