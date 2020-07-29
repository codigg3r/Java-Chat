package chat.commads.participante;

import chat.commads.Commandos;
import chat.participante.Participante;
import util.Utilidades;
import util.constants.Estados;

import java.util.HashMap;

public class CommandListar implements Commandos {
    protected Participante mParticipante;

    public CommandListar(Participante participante) {
        this.mParticipante = participante;
    }

    @Override
    public void execute(String linea) {
        HashMap<String, String> listaDeComandos = new HashMap<>();
        listaDeComandos.put("$LISTAR_COMANDOS","Mostrará al usuario la lista de comandos disponibles en el estado NO_CONECTADO");


        switch (mParticipante.getEstado()){
            case Estados.NO_CONECTADO:
            listaDeComandos.put("$ENTRAR_CHAT", "\tLe pedirá al usuario el Nick, el HostName y el Puerto del Servidor\n" +
                    "                \tde Chat.\n" +
                    "                \tA continuación, intentará conectar con el servidor y acceder al\n" +
                    "                \tChat con ese Nick.\n" +
                    "                \tSi el Nick está en uso, o hay algún error con la conexión, volverá al\n" +
                    "                \testado No Conectado.\n" +
                    "                \tSi conecta correctamente, y el servidor acepta el Nick de usuario,\n" +
                    "                \ty el estado del cliente pasará a estado Conectado.");

                break;
            case Estados.CONECTADO:
                listaDeComandos.put("$LISTAR_PARTICIPANTES", "\tEl cliente le enviará al servidor el comando:\n" +
                        "                            \t$LISTAR_PARTICIPANTES\n" +
                        "                            \tEl servidor contestará con el mensaje:\n" +
                        "                            \t$LISTADO_PARTICIPANTES(n) lista_participantes\n" +
                        "                            \tdonde “n” representa el número de usuarios conectados en el\n" +
                        "                            \tchat y “lista_participatnes” la lista de nicks conectados. Esta lista\n" +
                        "                            \tpuede formatearse en una única línea (utilizando un espacio o\n" +
                        "                            \tcoma para separarlos) o en varias líneas (una por nick).\n" +
                        "                            \tEl cliente mostrará la lista al usuario en consola o en la GUI\n" +
                        "                            \t(Interfaz Gráfica)." );
                listaDeComandos.put("$LISTAR_PARTICIPANTES_SUBSALA","TODO");
                break;

        } // switch-case

        Utilidades.muestraMensajeC(listaDeComandos.toString());

    } //execute
}
