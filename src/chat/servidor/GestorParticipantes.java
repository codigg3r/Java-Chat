package chat.servidor;

import java.net.* ;
import java.io.* ;
import java.util.* ;

import chat.participante.Participante;
import util.* ;

/**
 * 
 * Represente un conjunto de Participante conectados al Chat
 * Gestiona el envio de mensajes a todos, y el add/romeve de Participantes
 * 
 * @author BERKAY
 */
public class GestorParticipantes {
    
     //Atributos
    private     ArrayList<ParticipanteProxy> mParticipantes ; //losParticipantes

    
    
    //Constructores
    public GestorParticipantes () {
        
        //Inicio la lista. Inicialmente no contiene ningún participante
        mParticipantes = new ArrayList<ParticipanteProxy> () ;
        
    } // (GestorParticipantes) --> constructor
    
    
    // Propiedades - Indexers


    public synchronized ParticipanteProxy getParticipante (String nick) {  // buscaParticipante(String nick)
        
        //Recorro la lista buscando un participante con el nick indicando
        for (ParticipanteProxy p : mParticipantes) {
            if (p.getNick().equals(nick)) {
                 return p ;
            } // if
        }//for
        
        // No he encontrado ningun participante en ese Nick
        return null ;
        
    } // (getParticipante)
    
    
    //Metodos publicos
    
    public synchronized boolean estaConectado(String nick) {
        
         return getParticipante(nick) != null ;
      
        /*2.yol
          if ( getParticipante(nick) != null) {
            return true ;
        } // if
        return false;
        */
        
        /*3.yol
        for ( ParticipanteProxy p : mParticipantes) {
            if (p.getNick().equals(nick)) {
                return true ;
            } // if
        } // for
        return false ; 
        */
    }
    
    public synchronized void anyadaParticipante (ParticipanteProxy p) throws NickEnUsoException {
        
        //Precondicones
        if (estaConectado( p.getNick() ))
            throw new NickEnUsoException (" Ya existe usurario con nick " + p.getNick()) ;

        // En este punto sabemos que no tenemos un participante con ese nick
        mParticipantes.add (p) ;
        
    }
    
    public synchronized void eliminaParticipante (ParticipanteProxy p) {
    
         if (mParticipantes.remove(p)) {
                System.out.println (" Participante elimindado del gestor : " + p.getNick()) ;
         }
         else {
                 System.out.println (" Error : No se puede borrar el participante porque estaba en la lista : " + p.getNick() ) ;
         }
         
    } // (eliminaParticipante)


    public synchronized void difundeMensaje (String msg) {

        //recorrer la lista de participantes y enviar el mensaje
        //Tambien conocido como Broadcast
        for (ParticipanteProxy p : mParticipantes) {
            p.entregaMensaje(msg);
        } // for
    } // (difundeMensaje)

    public synchronized void difundeFichero (boolean b) {

        //recorrer la lista de participantes y enviar el mensaje
        //Tambien conocido como Broadcast
        for (ParticipanteProxy p : mParticipantes) {
            p.enviarFichero(b);
        } // for
    } // (difundeMensaje)

    public synchronized void enviarFicheroPrivado(String nick,boolean b) {
        ParticipanteProxy currentUser = null;

        for (ParticipanteProxy p: mParticipantes){
            if (p.getNick().equals(nick)){
                currentUser = p;
            }
        }

        for (ParticipanteProxy p: mParticipantes){
            if (p.getNick().equals(currentUser.getPrivateUser())){
                p.enviarFichero(b);
            }
        }

    } // (enviarFicheroPrivado)

    public synchronized  void respondame(String nick , String msg){
        for (ParticipanteProxy p : mParticipantes){
            if (p.getNick().equals(nick)){
                p.entregaMensaje(msg);
            }
        }
    }


    public synchronized void enviarMensajePrivado(String nick, String msg) {
        ParticipanteProxy currentUser = null;


        for (ParticipanteProxy p: mParticipantes){
            if (p.getNick().equals(nick)){
                currentUser = p;
            }
        }
        currentUser.entregaMensaje(msg);

        for (ParticipanteProxy p: mParticipantes){
            if (p.getNick().equals(currentUser.getPrivateUser())){
                p.entregaMensaje(msg);
            }
        }

    } // (enviarMensajePrivado)

    
    public synchronized String getListaParticipantes (int n) {
        
        StringBuilder sb = new StringBuilder() ;
        if (n>=0 && n < mParticipantes.size()) {
            for (int i = 0; i < n; i++) {
                sb.append(mParticipantes.get(i).getNick() + " ");
            }
        }else {
            // Recorrer la lista y anyadir los nick en el StringBuilder
            for (ParticipanteProxy p : mParticipantes) {
                sb.append((mParticipantes.indexOf(p) + 1) + ".");
                sb.append(p.getNick());
                sb.append(", ");
            } //for
        }
        return sb.toString () ;
        
    } // (getListaParticipantes)

    public String getInfo(String nick) {
        String res = "";
        for(ParticipanteProxy p : mParticipantes){
            if (p.getNick().equals(nick)){
                res ="Ip ->" + p.getSocket().getLocalAddress().toString().substring(1);
                res += "\t||\tHora de Connexion ->" +p.getHoraDeConexion();
            }
        }
        if (res.equals("")){
            res = "User not Found..";
        }

        return res;
    }
    public boolean isUserExist(String nick){
        for (ParticipanteProxy p :mParticipantes) {
            if(p.getNick().equals(nick)){
                return true;
            }
        }
        return false;
    }
// hello world

    //Metedos privados
}
