package chat.servidor;

import java.util.ArrayList;

public class GestorSubsala {
    protected ArrayList<Subsala> subsalas;
    protected Subsala generalChat;

    public GestorSubsala() {
        subsalas = new ArrayList<>();
        generalChat = new Subsala("General");
        subsalas.add(generalChat);
    }

    public String getListOfSubsala(){
        return subsalas.toString();
    }

    public void addSubsala(Subsala subsala){
        if (!subsalas.contains(subsala)){
            subsalas.add(subsala);
        }
    }
    public void removeSubsala(Subsala subsala){
        if (subsalas.contains(subsala)){
            subsalas.remove(subsala);
        }
    }
    public Subsala getSubsala(String subsalaName){
        for (Subsala s: subsalas){
            if (s.mSubsalaName.equals(subsalaName)){
                return s;
            }
        }
        return null;
    }


}
