package chat.commads.participante;

import chat.commads.Commandos;
import chat.participante.Participante;
import util.constants.Estados;

public class CommandListarParticipantes implements Commandos {
    protected Participante mParticipante;

    public CommandListarParticipantes(Participante mParticipante) {
        this.mParticipante = mParticipante;
    }

    @Override
    public void execute(String linea) {
        if (mParticipante.getEstado() > Estados.NO_CONECTADO) {
            mParticipante.enviaMensaje(linea);
        }
    }
}
