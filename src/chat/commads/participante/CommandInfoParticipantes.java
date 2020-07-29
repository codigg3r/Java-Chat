package chat.commads.participante;

import chat.commads.Commandos;
import chat.participante.Participante;
import util.constants.Estados;

public class CommandInfoParticipantes implements Commandos {
    protected Participante mParticipante;

    public CommandInfoParticipantes(Participante mParticipante) {
        this.mParticipante = mParticipante;
    }

    @Override
    public void execute(String linea) {
        if (mParticipante.getEstado() > Estados.NO_CONECTADO) {
            mParticipante.enviaMensaje(linea);
        }
    }
}
