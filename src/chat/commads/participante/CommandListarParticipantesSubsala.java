package chat.commads.participante;

import chat.commads.Commandos;
import chat.participante.Participante;

public class CommandListarParticipantesSubsala implements Commandos {
    protected Participante mParticipante;

    public CommandListarParticipantesSubsala(Participante mParticipante) {
        this.mParticipante = mParticipante;
    }

    @Override
    public void execute(String linea) {
        mParticipante.enviaMensaje("$LISTAR_PARTICIPANTES_SUBSALA");
    }
}
