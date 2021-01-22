import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Utilizadores {
    private Map<String,Utilizador> users;
    private ReentrantLock l;

    public Utilizadores (){
        this.l = new ReentrantLock();
        this.users = new HashMap<>();

        users.put("MariaBia13", new Utilizador("MariaBia13", "130300", 4, 5, false));
        users.put("TekasGG", new Utilizador("TekasGG", "161100", 1, 1, false));
        users.put("Xico_Franco", new Utilizador("Xico_Franco", "231299", 4, 5, true));
        users.put("MariaQB", new Utilizador("MariaQB", "280900", 5, 7, false));

    }

    public Map<String,Utilizador> getusers(){
        Map<String,Utilizador> aux = new HashMap<>();
        for(Utilizador u : users.values()){
            aux.put(u.getNome(),u);
        }
        return aux;
    }

    public boolean registar(DataInputStream in) throws IOException {
        Utilizador u = Utilizador.deserialize(in);
        l.lock();
        try {
            if (users.containsKey(u.getNome())) {
                return false;
            } else {
                users.put(u.getNome(), u);
                return true;
            }
        } finally {
            l.unlock();
        }
    }

    public boolean login(DataInputStream in) throws IOException{
        String nome = in.readUTF();
        String pass = in.readUTF();
        l.lock();
        try{
            if(users.containsKey(nome)){
                Utilizador u = users.get(nome);
                return u.getPass().equals(pass);
            }
            else return false;
        }finally{
            l.unlock();
        }
    }

    public void numeroLocalizacoes (DataOutputStream out, DataInputStream in) throws IOException{
        int x = in.readInt();
        int y = in.readInt();
        int total = 0;
        l.lock();
        try{
            for(Utilizador u : users.values()){
                if(u.getX() == x && u.getY() == y)
                    total++;
            }
            out.writeInt(total);
            out.flush();
        }finally{
            l.unlock();
        }
    }

    public int quantosLoc(DataInputStream in) throws IOException{
        int total = 0;
        int x = in.readInt();
        int y = in.readInt();
        for(Utilizador u : users.values()){
            if(u.getX() == x  && u.getY() == y)
                total++;
        }
        return total;
    }
}
