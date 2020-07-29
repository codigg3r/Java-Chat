package chat.participante;

import java.io.*;
import java.net.*;


import chat.commads.participante.ExecuteCommand;
import util.*;
import util.constants.Estados;

/**
 * Respresenta un Cliente de Chat,conectado a un servidor
 *
 * @author BERKAY
 */
public class Participante {
    //Atributos
    protected String mNick;              //Nick o identificador del Usurario en el Chat
    protected String mPrivateNick;
    protected String mhostServidor;      //IP o nombre del hostServidor de chat al que nos queremos conectar
    protected int mPuertoServidor;    //Puerto del Servidor al que nos queremos conectar
    protected Socket mSocket;       //El Socket con la conexion al servidor
    protected InputStream mEntrada;      //Stream/Buffer de entrada del Socket con el servidor
    protected OutputStream mSalida;       //Stream/Buffer de salida del Socket con el servidor
    protected boolean mSeguir;       //Indica si ha de seguir activo el cliente
    protected Escuchador mEscuchador;   //Objeto encargado de escuchar los mensajes
    protected int mEstado;

    /**
     * 0  -> NO CONECTADO
     * 1  -> CONECTADO
     * 11 -> CONECTADO PRIVADO
     * 12 -> CONECTADO  SUBSALA
     */

    // que va enviará el servidor EN UN HILO DE EJECUCION PARALELO

    //Constructores
    public Participante() {
        //Cuando creo un participante, no se pone 
        // inmediamente a la escucha
        mEscuchador = null;
    }

    //Proepiedades

    public static void main(String[] args) {

        Participante p = new Participante();
        p.LanzarCliente();
    }

    //Metodos Publicos


    public void setPuertoServidor(int mPuertoServidor) {
        this.mPuertoServidor = mPuertoServidor;
    }

    public void setHostServidor(String mhostServidor) {
        this.mhostServidor = mhostServidor;
    }

    public void setPrivateNick(String mPrivateNick) {
        this.mPrivateNick = mPrivateNick;
    }

    /**
     * @return the mNick
     */
    public String getNick() {
        return mNick;
    }

    public void setNick(String mNick) {
        this.mNick = mNick;
    }

    /**
     * @return the mhostServidor
     */
    public String getHostServidor() {
        return mhostServidor;
    }

    /**
     * @return the mPuertoServidor
     */
    public int getPuertoServidor() {
        return mPuertoServidor;
    }

    /**
     * @return the mSocket
     */
    public Socket getSocket() {
        return mSocket;
    }

    /**
     * @return the mEntrada
     */
    public InputStream getEntrada() {
        return mEntrada;
    }

    /**
     * @return the mSalida
     */
    public OutputStream getSalida() {
        return mSalida;
    }

    /**
     * @return the mSeguir
     */
    public boolean Seguir() {
        return mSeguir;
    }

    /**
     * @return the mEscuchador
     */
    public Escuchador getEscuchador() {
        return mEscuchador;
    }

    /**
     * @return the mEstado
     */
    public int getEstado() {
        return mEstado;
    }

    /**
     * puedes usar clase de Estados
     *
     * @param mEstado
     */
    public void setEstado(int mEstado) {
        this.mEstado = mEstado;
    }

    //Metodos Privados

    public void LanzarCliente() {
        leerYEnviar();     // Paso 4 - Nos ponemos a enviar mensaje
    }

    /**
     * Pedirá al usuario la IP y el Puerto del Servidor de Chat al que se ha de conectar
     */
    private void obtenerIpPuerto() {

        String linea = Utilidades.leerTextoC("Dime localizador del servidor (ip:puerto) : ");

        //saca el hostServidor y Puerto de la linea leida
        String[] args = linea.split(":");

        //Comprabamos que la lienea tenga dos trozos (palabras)
        if (args.length != 2) {
            Utilidades.muestraMensajeG(" Formato incorre ip:puerto  = " + linea);
            parar(); //termino el programa
        } else {
            // Obtenemos la ip/hostServidor de Chat
            mhostServidor = args[0];

            try {
                //Obtenemos el numero de puerto al que conectar

                mPuertoServidor = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                Utilidades.muestraMensajeG(" No he entendido numero de puerto =  " + args[1]);
                parar();
            } // catch
        }

    }

    /**
     * Intentará conectar, via Socket, con el Servidor de Chat
     */
    public void abrirConexion() {

        // Se asume que hostname y puerto ya están inicalizados
        try {
            mSocket = new Socket(getHostServidor(), getPuertoServidor());
            mEntrada = getSocket().getInputStream();
            mSalida = getSocket().getOutputStream();

            System.out.println("Hemos conectado! ");
        } catch (IOException ex) {

            System.out.println("Error en abrirConexion: " + ex.getMessage());

            // Aborta todo el programa
            parar();
        }

    }

    /**
     * Enviar al Servidor nuestro Nick (previamente preguntado al usuario),
     * e intentará iniciar sesion.
     * Posibilidades:
     * 1 - OK - Sesión iniciada
     * 2 - Error - Nick En Uso
     */
    public void iniciarSesion() {

        // mNick = Utilidades.leerTextoC(" Introduce Nick a utilizar : ") ;
        // mNick = Utilidades.leerTextoG(" Introduce Nick a utilizar  : ") ;

        // El protocolo con el Seervidor pide que el cliebnte envie primer el nick
        enviaMensaje(getNick());

        //Esperamos la respuesta del Servidor

        try {
            String respuesta = IO.leeLinea(getEntrada());
            if (respuesta.equals("$OK")) {
                // Significa que estamos dentro del servidor de chat ( usuario valido)

                //El escuchador se ha de poner  automaticamnete y EN PARALELO a escuchar
                mEscuchador = new Escuchador(this);
                setEstado(Estados.CONECTADO);
                //Informo al usuario que todo ido bien
                System.out.println(" Ya nos hemos iniciado sesion! ");
            } else {
                //Nos ha enviado un error ( No podemos entrar)
                System.out.println(" El Servidor no nos deja entrar :  " + respuesta);
                parar();
            }
        } catch (IOException ex) {
            System.out.println(" Error en iniciarSesion : " + ex.getMessage());
            parar();
        }


    }

    /**
     * Esperará a que el usuario escriba una linea, y lo enviará al Servidor de Chat
     */
    private void leerYEnviar() {

        String linea;
        setEstado(Estados.NO_CONECTADO);

        Utilidades.muestraMensajeC("Welcome to S-CHAT, for commands $LISTAR_COMANDOS");


        while (true) {
            // Preguntamos al usuario, mediante GUI el mensaje a enviar

            if (getEstado() == Estados.NO_CONECTADO) {
                linea = Utilidades.leerTextoC("S-CHAT#>");
            } else {
                linea = Utilidades.leerTextoC("S-CHAT(" + getNick() + ")#>");
            }
            // just save incoming messages. It is already inculuded out message.
            //Utilidades.guardarMensaje(linea);

            if (linea.startsWith("$")) {
                ExecuteCommand command = new ExecuteCommand(this);
                command.execute(linea);
            } else {
                if (getEstado() > Estados.NO_CONECTADO) {
                    enviaMensaje(linea);
                }else{
                    Utilidades.muestraMensajeC("Error: NO CONECTADO!");
                }
            }
        }
    }

    /**
     * Se encarga de enviar el mensaje al servidor de chat
     *
     * @param mensaje El mensaje a enviar
     */
    public void enviaMensaje(String mensaje) {

        //Precondicion
        if (mensaje == null || mensaje.length() == 0) {
            return;
        }

        try {
            IO.escribeLinea(mensaje, getSalida());
        } catch (IOException ex) {

            System.out.println("Error enviando mensaje: " + ex.getMessage());
            parar(); //Termino la ejecucion del cliente

        }

    }

    public void enviarFichero(String path, boolean b) {

        String fileName = "";
        File nFile = new File(path);
        if (path.contains("\\")) {
            fileName = path.substring(path.lastIndexOf("\\") + 1);
        } else if (path.contains("/")) {
            fileName = path.substring(path.lastIndexOf("/") + 1);
        } else {
            if (path.contains(":")) {
                fileName = path.substring(path.indexOf(":") + 1);
            }
        }


        try {
            InputStream in = new FileInputStream(nFile);
            IO.escribeFichero(fileName, in.readAllBytes(), getSalida(),b);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    // Main - Punto inicial de entreda del cliente de Chat

    /**
     * Detiene la ejecucion del cliente
     */
    public void parar() {

        //Detenemos la ejecucion
        mSeguir = false;
        if (getEscuchador() != null)
            getEscuchador().parar();

        //Cerramos todos los Streams

        try {
            if (getEntrada() != null)
                getEntrada().close();

            if (getSalida() != null)
                getSalida().close();

            if (getSocket() != null)
                getSocket().close();

        } catch (IOException ex) {
            System.out.println("Error al parar : " + ex.getMessage());
        }

        //Salimos definitivamente del programa
        //System.exit(-1);
    }


}
