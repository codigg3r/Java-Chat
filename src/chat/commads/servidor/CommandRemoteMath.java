package chat.commads.servidor;

import chat.commads.Commandos;
import chat.servidor.ParticipanteProxy;
import util.Utilidades;
import util.constants.Estados;
import util.constants.ResultCodes;

import java.util.ArrayList;

public class CommandRemoteMath implements Commandos {
    protected ParticipanteProxy mParticipanteProxy;

    public CommandRemoteMath(ParticipanteProxy mParticipanteProxy) {
        this.mParticipanteProxy = mParticipanteProxy;
    }

    @Override
    public void execute(String mensaje) {
        if (mParticipanteProxy.getEstado() < Estados.CONECTADO){
            return;
        }

        ArrayList<Float> resArray = new ArrayList<Float>();

        resArray= Utilidades.mathOp(mensaje.substring(mensaje.indexOf(" ")+1));
        float resValue = resArray.get(0);
        float resCode = resArray.get(1);
        if (resCode != ResultCodes.OK){
            if (resCode == ResultCodes.NUMBER_FORMAT_ERROR){
                mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(),"$REMOTE_MATH_OP_RESULT OK" + "Number Format Error");
            }
            if (resCode == ResultCodes.BOUNDRY_ERROR){
                mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(),"$REMOTE_MATH_OP_RESULT " + "Mal format input");
            }
            if (resCode == ResultCodes.ARITHMETIC_ERROR){
                mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(),"$REMOTE_MATH_OP_RESULT "+"Unaccepted value on Operation");
            }
        }else{
            mParticipanteProxy.getGestor().respondame(mParticipanteProxy.getNick(),"$REMOTE_MATH_OP_RESULT OK" + resValue);
        }
    }
}
