/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import util.constants.FileOp;

import java.io.*;

/**
 * @author Jordi
 */
public class IO {

    //.........................................................................
    //.........................................................................
    private static final int LF = 10; // line feed
    private static final int CR = 13; // carriage return
    private static final byte[] SALTO_DE_LINEA = {CR, LF}; // array con 2 casillas: tiene CR y LF

    //.........................................................................
    //.........................................................................
    public static int copia(InputStream entrada, OutputStream salida) throws IOException {
        int unByteLeido;
        int numBytes = 0;
        while ((unByteLeido = entrada.read()) != -1) {
            numBytes++;
            salida.write(unByteLeido);
        } // while
        return numBytes;
    } // ()

    //.........................................................................
    //.........................................................................
    public static String leeLinea(InputStream entrada) throws IOException {


        ByteArrayOutputStream acumulador = new ByteArrayOutputStream();
        int unByte = 0;
        int byteAnterior = 0;



        // leo con read, guardo en unByte y comparo con -1
        while ((unByte = entrada.read()) != -1) {

            if (unByte == CR) {
                // si veo un CR, no lo pongo en el acumulador
                // pero lo guardo
                byteAnterior = CR; // == unByte
            } else if (byteAnterior == CR && unByte == LF) {
                // si había visto un CR y ahora veo un LF
                // => he detectado un salto de lína
                // => termino el bucle
                break;

            } else if (byteAnterior == CR && unByte != LF) {
                // si había visto un CR pero este no es LF
                // no es un salto de línea y
                // como no puse el CR, pongo ahora los dos
                acumulador.write(byteAnterior); // que es CR
                acumulador.write(unByte); // que no es LF
            } else {
                // ninguno de los anteriores casos
                // pongo el byte leído
                acumulador.write(unByte);
                byteAnterior = unByte;
            }
            if (acumulador.toString().startsWith("$ENVIAR_FICHERO ")) {
                leeFichero(entrada, false);
                return "File Recived: " + FileOp.filePath;
            }else if(acumulador.toString().startsWith("$ENVIAR_FICHERO_ENCRIPTADO ")){
                leeFichero(entrada,true);
                return "File Securely Recived: " + FileOp.filePath;
            }

        } // while

        if (unByte == -1) {
            throw new IOException("entrada cerrada");
        }
        // devuelvo los bytes acumulados, pasados a texto
        return acumulador.toString();
    } // ()


    public static void leeFichero(InputStream is, boolean b) {
        String fileName = "";
        String strSize = "";
        int intSize = 0;
        int unByte = 0;
        boolean findSize = false;
        boolean findName = false;
        ByteArrayOutputStream acumulador = new ByteArrayOutputStream();

        // first stage take file name and size of file
        while (!findSize){
            try {
                unByte = is.read();
                acumulador.write(unByte);
                if (acumulador.toString().endsWith(" ")){
                    strSize = acumulador.toString();
                    findSize = true;
                }
            } catch (IOException e) { }
        }
        acumulador.reset();

        while (!findName){
            try {
                unByte = is.read();
                acumulador.write(unByte);
                if (acumulador.toString().endsWith(" ")){
                    fileName = acumulador.toString();
                    FileOp.filePath = fileName;

                    fileName = fileName.substring(0,fileName.length()-1);
                    findName = true;
                }
            } catch (IOException e) { }
        }
        acumulador.reset();
        intSize = Integer.parseInt(strSize.substring(0,strSize.length()-1));


        for (int i = 0; i < intSize ; i++){
            try {
                unByte = is.read();
                acumulador.write(unByte);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] fichero;
        if (b){
            fichero = decryptFihero(acumulador.toByteArray());
        }else{
            fichero = acumulador.toByteArray();
        }

        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(fichero);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //.........................................................................
    //.........................................................................
    public static void escribeLinea(String linea, OutputStream salida) throws IOException {
        salida.write(linea.getBytes());
        salida.write(SALTO_DE_LINEA);
        // creo que no hace falta: salida.flush();
    } // ()

    //.........................................................................
    //.........................................................................
    public static void escribeFichero(String fileName, byte[] input, OutputStream salida, boolean b) throws IOException {
        String size = input.length + " ";
        String comandoDeFichero = "";
        byte[] encrytedInput = input;

        if (b) {
            encrytedInput = encryptFichero(input);
            comandoDeFichero = "$ENVIAR_FICHERO_ENCRIPTADO " + size + fileName + " ";
        }else{
            comandoDeFichero = "$ENVIAR_FICHERO " + size + fileName + " ";
        }

        salida.write(comandoDeFichero.getBytes());
        salida.write(encrytedInput);
        //salida.flush();
    } // ()

    public static byte[] encryptFichero(byte[] input){
        byte[] res = input;
        for(int i = 0; i < input.length ; i ++){
            if (input[i] < 255){
                res[i]  = (byte)(input[i] + 1);
            }else if(input[i] == 255){
                res[i] = (byte) 0;
            }
        }
        return res;
    }
    public static byte[] decryptFihero(byte[] input){

        for(int i = 0; i < input.length ; i ++){
            if (input[i] > 0){
                input[i]  = (byte)(input[i] - 1);
            }else if(input[i] == 0){
                input[i] = (byte) 255;
            }
        }
        return input;
    }

}
