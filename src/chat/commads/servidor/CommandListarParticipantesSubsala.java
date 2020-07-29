package chat.commads.servidor;

import chat.commads.Commandos;
import chat.servidor.ParticipanteProxy;

public class CommandListarParticipantesSubsala implements Commandos {
    protected ParticipanteProxy mParticipanteProxy;

    public CommandListarParticipantesSubsala(ParticipanteProxy mParticipanteProxy) {
        this.mParticipanteProxy = mParticipanteProxy;
    }

    @Override
    public void execute(String linea) {
        //TODO:Command of Listar Participantes Subsala..
    }
}
