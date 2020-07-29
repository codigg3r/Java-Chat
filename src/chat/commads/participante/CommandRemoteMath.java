package chat.commads.participante;

import chat.commads.Commandos;
import chat.participante.Participante;

public class CommandRemoteMath implements Commandos {
    protected Participante mParticipante;

    public CommandRemoteMath(Participante mParticipante) {
        this.mParticipante = mParticipante;
    }

    @Override
    public void execute(String linea) {
        mParticipante.enviaMensaje(linea);
    }
}
