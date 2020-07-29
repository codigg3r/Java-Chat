package chat.commads.servidor;

import chat.commads.Commandos;
import chat.servidor.ParticipanteProxy;
import util.constants.Estados;

public class CommandListarParticipantes implements Commandos {
    protected ParticipanteProxy mParticipanteProxy;

    public CommandListarParticipantes(ParticipanteProxy mParticipanteProxy) {
        this.mParticipanteProxy = mParticipanteProxy;
    }

    @Override
    public void execute(String mensaje) {

        if (mParticipanteProxy.getEstado() > Estados.NO_CONECTADO) {
            if (mensaje.split(" ").length > 1) {
                try {
                    int n = Integer.parseInt(mensaje.split(" ")[1]);
                    mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(),
                    mParticipanteProxy.getGestor().getListaParticipantes(n));
                } catch (NumberFormatException ex) {
                    mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(), "$LISTAR_PARTICIPANTES_RESULT Mal format input");
                    mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(), "Usage; $LISTAR_PARTICIPANTES (n); where n is number of user want to print.");
                }
            }
        }
    }
}
