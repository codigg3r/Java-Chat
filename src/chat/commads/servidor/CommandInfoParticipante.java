package chat.commads.servidor;

import chat.commads.Commandos;
import chat.servidor.ParticipanteProxy;
import util.constants.Estados;

public class CommandInfoParticipante implements Commandos {
    protected ParticipanteProxy mParticipanteProxy;

    public CommandInfoParticipante(ParticipanteProxy mParticipanteProxy) {
        this.mParticipanteProxy = mParticipanteProxy;
    }

    @Override
    public void execute(String mensaje) {
        if(mParticipanteProxy.getEstado() == Estados.CONECTADO){
            String nick = "";
            if (mensaje.split(" ").length > 1){
                nick = mensaje.split(" ")[1];
            }
            mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(), mParticipanteProxy.getGestor().getInfo(nick));
        }
    }
}
