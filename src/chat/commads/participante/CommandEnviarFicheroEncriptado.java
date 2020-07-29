package chat.commads.participante;

import chat.commads.Commandos;
import chat.participante.Participante;
import util.constants.Estados;

public class CommandEnviarFicheroEncriptado implements Commandos {
    protected Participante mParticipante;

    public CommandEnviarFicheroEncriptado(Participante mParticipante) {
        this.mParticipante = mParticipante;
    }

    @Override
    public void execute(String linea) {
        if (mParticipante.getEstado() > Estados.NO_CONECTADO) {
            String path = "";
            if (linea.split(" ").length > 1) {
                path = linea.split(" ")[1];
            }
            if (path != null) {
                mParticipante.enviarFichero(path, true);
            }
        }
    }
}
