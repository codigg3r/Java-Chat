package chat.commads.participante;

import chat.commads.Commandos;
import chat.participante.Participante;

public class ExecuteCommand implements Commandos {
    protected Participante mParticipante;

    public ExecuteCommand(Participante mParticipante) {
        this.mParticipante = mParticipante;
    }

    @Override
    public void execute(String linea) {
        Commandos command = null;
        if (linea.equals("$LISTAR_COMANDOS")) {
            command = new CommandListar(mParticipante);
        } else if (linea.startsWith("$ENTRAR_CHAT")) {
            command = new CommandEntrar(mParticipante);
        } else if (linea.startsWith("$CREAR_SUBSALA")) {
            command = new CommandCrearSubsala(mParticipante);
        }else if (linea.startsWith("$ENTRAR_SUBSALA")) {
            command = new CommandEntrarSubsala(mParticipante);
        } else if (linea.equals("$SALIR")) {
            command = new CommandSalir(mParticipante);
        } else if (linea.startsWith("$LISTAR_PARTICIPANTES")) {
            command = new CommandListarParticipantes(mParticipante);
        } else if (linea.startsWith("$ENVIAR_FICHERO ")) {
            command = new CommandEnviarFichero(mParticipante);
        }  else if (linea.startsWith("$ENVIAR_FICHERO_ENCRIPTADO ")) {
            command = new CommandEnviarFicheroEncriptado(mParticipante);
        } else if (linea.equals("$GUARDAR_CHAT")) {
            command = new CommandGuardarChat(mParticipante);
        } else if (linea.startsWith("$REMOTE_MATH_OP")) {
            command = new CommandRemoteMath(mParticipante);
        } else if (linea.equals("$LISTAR_PARTICIPANTES_SUBSALA")) {
            command = new CommandListarParticipantesSubsala(mParticipante);
        } else if (linea.startsWith("$INFO_PARTICIPANTE")) {
            command = new CommandInfoParticipantes(mParticipante);
        } else if (linea.startsWith("$INICIAR_PRIVADO")) {
            command = new CommandIniciarPrivado(mParticipante);
        }

        if (command != null) {
            command.execute(linea);
        }


    }
    
}
