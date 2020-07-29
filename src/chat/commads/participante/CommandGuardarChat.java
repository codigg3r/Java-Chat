package chat.commads.participante;

import chat.commads.Commandos;
import chat.participante.Participante;
import util.Utilidades;
import util.constants.Estados;

public class CommandGuardarChat implements Commandos {

    protected Participante mParticipante;

    public CommandGuardarChat(Participante mParticipante) {
        this.mParticipante = mParticipante;
    }

    @Override
    public void execute(String linea) {
        if (mParticipante.getEstado() > Estados.NO_CONECTADO) {
            Utilidades.guardarChat();
        }else {
            Utilidades.muestraMensajeC("This command not allowed to run in that state!");
        }
    }
}
