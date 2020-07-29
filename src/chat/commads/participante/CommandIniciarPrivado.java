package chat.commads.participante;

import chat.commads.Commandos;
import chat.participante.Participante;
import util.constants.Estados;

public class CommandIniciarPrivado implements Commandos {
    protected Participante mParticipante;

    public CommandIniciarPrivado(Participante mParticipante) {
        this.mParticipante = mParticipante;
    }

    @Override
    public void execute(String linea) {
        if (mParticipante.getEstado() > Estados.NO_CONECTADO){
            mParticipante.setEstado(Estados.CONECTADO_PRIVADO);
            mParticipante.enviaMensaje(linea);
        }
    }
}
