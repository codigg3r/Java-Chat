package chat.commads.servidor;

import chat.commads.Commandos;
import chat.servidor.ParticipanteProxy;
import chat.servidor.Subsala;

public class CommandCrearSubsala implements Commandos {
    protected ParticipanteProxy mParticipanteProxy;

    public CommandCrearSubsala(ParticipanteProxy mParticipanteProxy) {
        this.mParticipanteProxy = mParticipanteProxy;
    }

    @Override
    public void execute(String mensaje) {
        if (mensaje.split(" ").length > 1) {
            Subsala subsala = new Subsala(mensaje.split(" ")[1]);
            mParticipanteProxy.getGestorSubsala().addSubsala(subsala);
            mParticipanteProxy.getGestor().difundeMensaje("Subsala "+mensaje.split(" ")[1]+ " has created");
        }
    }
}
