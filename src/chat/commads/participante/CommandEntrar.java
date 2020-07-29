package chat.commads.participante;

import chat.commads.Commandos;
import chat.participante.Participante;
import util.constants.Estados;

import java.io.InputStream;
import java.io.OutputStream;

public class CommandEntrar implements Commandos {
    protected Participante mParticipante;

    public CommandEntrar(Participante participante) {
        this.mParticipante = participante;
    }

    @Override
    public void execute(String linea) {
        String args = "";
        if (linea.split(" ").length > 1) {
            args = linea.split(" ")[1];
        }

        if (args.split("@").length > 1) {
            mParticipante.setNick( args.split("@")[0]);
        }
        if (args.contains(":") && args.contains("@")) {
            mParticipante.setHostServidor(args.substring(args.indexOf("@") + 1, args.indexOf(":")));
            String port = "0";
            if (args.split("@").length > 2) {
                port = args.substring(args.indexOf(":") + 1, args.lastIndexOf("@"));
            } else {
                port = args.substring(args.indexOf(":") + 1);
            }
            mParticipante.setPuertoServidor(Integer.parseInt(port));
            mParticipante.abrirConexion();
            mParticipante.iniciarSesion();
        }
    }
}
