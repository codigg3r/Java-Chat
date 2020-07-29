package chat.commads.servidor;

import chat.commads.Commandos;
import chat.servidor.ParticipanteProxy;
import util.constants.Estados;

public class CommandEnviarFicheroEncriptado implements Commandos {
    protected ParticipanteProxy mParticipanteProxy;

    public CommandEnviarFicheroEncriptado(ParticipanteProxy mParticipanteProxy) {
        this.mParticipanteProxy = mParticipanteProxy;
    }

    @Override
    public void execute(String linea) {
        switch (mParticipanteProxy.getEstado()) {
            case Estados.CONECTADO:
                mParticipanteProxy.getGestor().difundeFichero(true);
                break;
            case Estados.CONECTADO_PRIVADO:
                mParticipanteProxy.getGestor().enviarFicheroPrivado(mParticipanteProxy.getPrivateUser(), true);
                break;
        }
    }
}
