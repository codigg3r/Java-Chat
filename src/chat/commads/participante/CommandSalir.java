package chat.commads.participante;

import chat.commads.Commandos;
import chat.participante.Participante;
import util.constants.Estados;

public class CommandSalir implements Commandos {
    protected Participante mParticipante;

    public CommandSalir(Participante participante) {
        this.mParticipante = participante;
    }

    @Override
    public void execute(String linea) {
        switch (mParticipante.getEstado()) {
            case Estados.CONECTADO:
                mParticipante.setEstado(Estados.NO_CONECTADO);
                mParticipante.parar();
            break;
            case Estados.CONECTADO_PRIVADO:
                mParticipante.setEstado(Estados.CONECTADO);
                mParticipante.enviaMensaje("$SALIR");
            break;
            case Estados.CONECTADO_SUBSALA:
                mParticipante.setEstado(Estados.CONECTADO);
                mParticipante.enviaMensaje("$SALIR");
            break;
        }
    }
}
