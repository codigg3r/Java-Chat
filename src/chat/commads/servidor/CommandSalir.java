package chat.commads.servidor;

import chat.commads.Commandos;
import chat.servidor.ParticipanteProxy;
import util.constants.Estados;

public class CommandSalir implements Commandos {
    protected ParticipanteProxy mParticipanteProxy;

    public CommandSalir(ParticipanteProxy mParticipanteProxy) {
        this.mParticipanteProxy = mParticipanteProxy;
    }

    @Override
    public void execute(String linea) {
        switch (mParticipanteProxy.getEstado() ) {
            case Estados.CONECTADO:
                mParticipanteProxy.cerrar("chat> Se retira : " + mParticipanteProxy.getNick());
            break;
            case Estados.CONECTADO_PRIVADO:
                mParticipanteProxy.setEstado(Estados.CONECTADO);
                mParticipanteProxy.setPrivateUser("");
            case Estados.CONECTADO_SUBSALA:
                mParticipanteProxy.setEstado(Estados.CONECTADO);
                mParticipanteProxy.setMySubsala(mParticipanteProxy.getGestorSubsala().getSubsala("General"));
        }
    }
}
