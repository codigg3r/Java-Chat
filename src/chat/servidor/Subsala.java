package chat.servidor;


import java.util.ArrayList;

public class Subsala {
    protected String mSubsalaName;
    protected ArrayList<ParticipanteProxy> mParticipanteProxies;

    public Subsala(String subsalaName) {
        mParticipanteProxies = new ArrayList<>();
        this.mSubsalaName = subsalaName;
    }

    public String getmSubsalaName() {
        return mSubsalaName;
    }

    public void addUser(ParticipanteProxy p){
        if (!mParticipanteProxies.contains(p)) {
            mParticipanteProxies.add(p);
        }
    }
    public void removeUser(ParticipanteProxy p){
        if (mParticipanteProxies.contains(p)){
            mParticipanteProxies.remove(p);
        }
    }

    public void enviarMensaje(String mensaje) {
        for (ParticipanteProxy p : mParticipanteProxies){
            p.entregaMensaje(p.getNick() +"|" + getmSubsalaName() + " >" + mensaje);
        }
    }
}
