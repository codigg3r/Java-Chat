package chat.commads.servidor;

import chat.commads.Commandos;
import chat.servidor.ParticipanteProxy;
import util.constants.Estados;

public class CommandIniciarPrivado implements Commandos {
    protected ParticipanteProxy mParticipanteProxy;

    public CommandIniciarPrivado(ParticipanteProxy mParticipanteProxy) {
        this.mParticipanteProxy = mParticipanteProxy;
    }

    @Override
    public void execute(String mensaje) {
        if (mensaje.split(" ").length > 1) {
            if (mParticipanteProxy.getGestor().isUserExist(mensaje.split(" ")[1])){
                mParticipanteProxy.setPrivateUser(mensaje.split(" ")[1]);
                mParticipanteProxy.setEstado(Estados.CONECTADO_PRIVADO);
                mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(),"$INICIAR_PRIVADO "+mParticipanteProxy.getPrivateUser()+" OK");
            }else{
                mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(), "$INICIAR_PRIVADO " + mensaje.split(" ")[1] + " FAILED!");
            }
        } else {
            mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(), "$INICIAR_PRIVADO "+ " FAILED!");
        }
    }
}
