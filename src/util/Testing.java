package util;

/**
 *
 * @author BERKAY
 */
public class Testing {
    public static void main(String args[]){
        
        Utilidades.muestraMensajeC("Mensaje consola 1 "); //Salida por Consola
        Utilidades.muestraMensajeG("Mensaje GUI 1 ");     //Salida por GUI
        Utilidades.muestraMensajeC("Mensaje consola 2 "); //Salida por Consola,hasta que no pulsa OK el usuario no aparece
        System.out.println("Igual que muestraMensajeC");
        
        //Ahora pruebo a leer datos del usuario
        String lineaConsola = Utilidades.leerTextoC("Dame texto (Consola 1): ");// Leo de la consola
        String lineaGUI     = Utilidades.leerTextoG("Dime algo (GUI): ");       // Leo de un DialogBox
        
        Utilidades.muestraMensajeG("Me has dicho por la GUI : " + lineaGUI); //Muestro el mensaje
        System.out.println("El mensaje por Consola fue: " + lineaConsola);
       
    }
}
