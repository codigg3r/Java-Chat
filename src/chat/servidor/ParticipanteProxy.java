package chat.servidor;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import chat.commads.servidor.ExecuteCommand;
import util.*;
import util.constants.Estados;
import util.constants.FileOp;

/**
 * Respresenta la conexion con un único Cliente de Chat, desde el lado del servidor.
 * Contendrá el Socket al Cliente y todo lo necesario para hablar con él
 * Correrá en hilo de paralelo ---> implements Runnable
 *
 * @author BERKAY
 */
public class ParticipanteProxy implements Runnable {

    //Atributos
    protected String mNick;
    protected Socket mSocket; // socket donde estamos conectados
    protected InputStream mEntrada; // stream para leer (recibir)
    protected OutputStream mSalida; // stream para escribir (enviar)
    protected boolean mSeguir;
    protected GestorParticipantes mGestor;
    protected GestorSubsala mGestorSubsala;
    protected int mEstado;
    protected String mPrivateUser;
    protected String mHoraDeConexion;
    protected Subsala mySubsala;


    //Constructores
    public ParticipanteProxy(Socket s, GestorParticipantes g, GestorSubsala subsala) {

        mGestor = g;
        mSocket = s;
        mGestorSubsala = subsala;
        mSeguir = true; // Asuimimos inicialmente que todo ha ido bien y que hemos de seguir

        //Obtener los input(recepción), output (envío) y Nick
        try {
            mEntrada = s.getInputStream();
            mSalida = s.getOutputStream();


            // Por protocolo el cliente envia primero su nick
            mNick = recibeMensaje();
        } catch (IOException ex) {
            mSeguir = false;
            System.out.println("Error en Constructor de ParticipanteProxy : " + ex.getMessage());
            return;
        }

        //Ya tenemos  el Nick de Participante   
        try {
            mGestor.anyadaParticipante(this);
            mGestorSubsala.getSubsala("General").addUser(this);
            setMySubsala(mGestorSubsala.getSubsala("General"));

            entregaMensaje("$OK");
            //Lanzo el nuevo thread
            Thread th = new Thread(this);
            th.start();
        } catch (NickEnUsoException ex) {
            entregaMensaje("$ERR_NICK_EN_USO Nick ya Utilizado: " + getNick());
            cerrar("");
        }

    } // (ParticipanteProxy) --> constructor

    //Propiedades
    // getter setter
    public String getPrivateUser() {
        return mPrivateUser;
    }

    public void setPrivateUser(String privateUser) {
        this.mPrivateUser = privateUser;
    }

    public String getHoraDeConexion() {
        return mHoraDeConexion;
    }

    public GestorSubsala getGestorSubsala() {
        return mGestorSubsala;
    }

    public Subsala getMySubsala() {
        return mySubsala;
    }

    public void setMySubsala(Subsala mySubsala) {
        this.mySubsala = mySubsala;
    }

    /**
     * @return the mNick
     */
    public String getNick() {
        return mNick;
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
     * @return El Gestor que contiene este ParticipanteProxy
     */
    public GestorParticipantes getGestor() {
        return mGestor;
    }

    /**
     * @return the mSeguir
     */
    public boolean Seguir() {
        return mSeguir;
    }

    public int getEstado() {
        return mEstado;
    }

    public void setEstado(int mEstado) {
        this.mEstado = mEstado;
    }

    //Metodos publicos

    @Override
    public void run() {

        mGestor.difundeMensaje(" chat> Nuevo Usuario Conectado : " + getNick());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date dt = new Date();

        mHoraDeConexion = sdf.format(dt);

        setEstado(Estados.CONECTADO);
        // 1 - Difundimos a todos los conectados quien se ha conectado ahora
        // 2 - Solo al nuevo que se conecta le mandamos los que están conectados
        entregaMensaje("chat : conectados> " + mGestor.getListaParticipantes(-1));

        //Buckle principal de escuha al participante
        while (Seguir()) {

            String mensaje = recibeMensaje();

            if (mensaje.startsWith("$")) {
                ExecuteCommand command = new ExecuteCommand(this);
                command.execute(mensaje);
            } else {
                if (getEstado() == Estados.CONECTADO) {
                    mGestor.difundeMensaje(getNick() + " > " + mensaje);
                }else if (getEstado() == Estados.CONECTADO_PRIVADO) {
                    mGestor.enviarMensajePrivado(getNick(), getNick() +"|" + getPrivateUser() + " >" + mensaje);
                }else if(getEstado() == Estados.CONECTADO_SUBSALA){
                    getMySubsala().enviarMensaje(mensaje);
                }

            }
        }
        setEstado(Estados.NO_CONECTADO);

    } // (run)

    public void entregaMensaje(String mensaje) {

        // 1 - Precondicion
        if (mensaje == null || mensaje.length() == 0) {
            //No enviamos mensaje vacios
            return;
        }
        try {
            // Enviamos el mensaje sobre la salida del Socket
            IO.escribeLinea(mensaje, getSalida());
        } catch (IOException ex) {
            cerrar(" chat> se retira por fallos en la conexion (entregaMensaje ()  ) : " + getNick());
        }

    } // (entregaMensaje)

    public void enviarFichero(boolean b) {
        File nFile = new File(FileOp.filePath);

        try {
            InputStream in = new FileInputStream(nFile);
            IO.escribeFichero(FileOp.filePath, in.readAllBytes(), getSalida(), b);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    // Metedos privados

    private String recibeMensaje() {

        String msg = "";

        try {
            msg = IO.leeLinea(getEntrada());
            if (msg.equals("file recieved")){

            }
        } catch (IOException ex) {
            cerrar(" chat> se retira por fallos en la conexion (recibeMensaje() ) : " + getNick());
        }
        return msg;

    } // (recibeMensaje)

    public void cerrar(String mensaje) {
        mSeguir = false;
        setEstado(Estados.NO_CONECTADO);

        try {
            mGestor.eliminaParticipante(this);

            // Cierro los Streams de I/O y el Socket
            if (mEntrada != null) {
                mEntrada.close();
                mEntrada = null;
            }

            if (mSalida != null) {
                mSalida.close();
                mSalida = null;
            }

            if (mSocket != null) {
                mSocket.close();
                mSocket = null;
            }
        } catch (IOException ex) {
            System.out.println(" Error en cerrar ( " + getNick() + " )  :  " + ex.getMessage());
        }

        //Ultima parte de cerrar
        if (mensaje != null && mensaje.length() > 0) {

            mGestor.difundeMensaje(mensaje);
            mGestor.difundeMensaje(" chat : Participantes> " + mGestor.getListaParticipantes(-1));
        }

    } // (cerrar)

} // class
