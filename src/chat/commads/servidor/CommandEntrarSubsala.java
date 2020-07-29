package chat.commads.servidor;

import chat.commads.Commandos;
import chat.servidor.ParticipanteProxy;
import chat.servidor.Subsala;
import util.constants.Estados;

public class CommandEntrarSubsala implements Commandos {
    protected ParticipanteProxy mParticipanteProxy;

    public CommandEntrarSubsala(ParticipanteProxy mParticipanteProxy) {
        this.mParticipanteProxy = mParticipanteProxy;
    }

    @Override
    public void execute(String mensaje) {
        if (mensaje.split(" ").length > 1) {
            String subsalaName = mensaje.split(" ")[1];
            Subsala subsala = mParticipanteProxy.getGestorSubsala().getSubsala(subsalaName);
            if (subsala != null){
                subsala.addUser(mParticipanteProxy);
                mParticipanteProxy.setEstado(Estados.CONECTADO_SUBSALA);
                mParticipanteProxy.setMySubsala(subsala);
                mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(),"$ENTRAR_SUBSALA "+subsalaName+" OK");
            }else{
                mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(), "$ENTRAR_SUBSALA " + subsalaName + " FAILED!");
            }
        } else {
            mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(), "$ENTRAR_SUBSALA "+ " FAILED!");
        }
    }
}
