package chat.commads.servidor;

import chat.commads.Commandos;
import chat.servidor.ParticipanteProxy;
import util.constants.Estados;

public class CommandEnviarFichero implements Commandos {
    protected ParticipanteProxy mParticipanteProxy;

    public CommandEnviarFichero(ParticipanteProxy mParticipanteProxy) {
        this.mParticipanteProxy = mParticipanteProxy;
    }

    @Override
    public void execute(String mensaje) {
        switch (mParticipanteProxy.getEstado()) {
            case Estados.CONECTADO:
                mParticipanteProxy.getGestor().difundeFichero(false);
                break;
            case Estados.CONECTADO_PRIVADO:
                mParticipanteProxy.getGestor().enviarFicheroPrivado(mParticipanteProxy.getPrivateUser(), false);
                break;
        }

    }
}
