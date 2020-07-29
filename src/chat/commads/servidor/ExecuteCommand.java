package chat.commads.servidor;

import chat.commads.Commandos;
import chat.servidor.ParticipanteProxy;

public class ExecuteCommand implements Commandos {
    protected ParticipanteProxy mParticipanteProxy;

    public ExecuteCommand(ParticipanteProxy mParticipanteProxy) {
        this.mParticipanteProxy = mParticipanteProxy;
    }

    @Override
    public void execute(String mensaje) {
        Commandos command = null;
        if (mensaje.equals("$SALIR")) {
            command = new CommandSalir(mParticipanteProxy);
        } else if (mensaje.startsWith("$LISTAR_PARTICIPANTES")) {
            command = new CommandListarParticipantes(mParticipanteProxy);
        } else if (mensaje.equals("$LISTAR_PARTICIPANTES_SUBSALA")) {
            command = new CommandListarParticipantesSubsala(mParticipanteProxy);
        } else if (mensaje.startsWith("$INFO_PARTICIPANTE")) {
            command = new CommandInfoParticipante(mParticipanteProxy);
        } else if (mensaje.startsWith("$INICIAR_PRIVADO")) {
            command = new CommandIniciarPrivado(mParticipanteProxy);
        } else if (mensaje.startsWith("$CREAR_SUBSALA")) {
            command = new CommandCrearSubsala(mParticipanteProxy);
        }else if (mensaje.startsWith("$ENTRAR_SUBSALA")) {
            command = new CommandEntrarSubsala(mParticipanteProxy);
        } else if (mensaje.startsWith("$REMOTE_MATH_OP")) {
            command = new CommandRemoteMath(mParticipanteProxy);
        } else if (mensaje.startsWith("$ENVIAR_FICHERO ")) {
            command = new CommandEnviarFichero(mParticipanteProxy);
        }else if (mensaje.startsWith("$ENVIAR_FICHERO_ENCRIPTADO ")) {
            command = new CommandEnviarFicheroEncriptado(mParticipanteProxy);
        }

        if (command != null) {
            command.execute(mensaje);
        }

    }
}
