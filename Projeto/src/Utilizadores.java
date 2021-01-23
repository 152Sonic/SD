import jdk.jshell.execution.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Utilizadores {
    private Map<String,Utilizador> users;
    private ReentrantLock lock = new ReentrantLock();
    private ReentrantReadWriteLock l = new ReentrantReadWriteLock();
    private Lock rl = l.readLock();
    private Lock wl = l.writeLock();
    private Condition c = lock.newCondition();

    public Utilizadores (){
        this.l = new ReentrantReadWriteLock();
        this.users = new HashMap<>();

        users.put("MariaBia13", new Utilizador("MariaBia13", "130300", 4, 5, false,false));
        users.put("TekasGG", new Utilizador("TekasGG", "161100", 1, 1, false,false));
        users.put("Xico_Franco", new Utilizador("Xico_Franco", "231299", 4, 5, true,false));
        users.put("MariaQB", new Utilizador("MariaQB", "280900", 5, 7, false,false));

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
        wl.lock();
        try {
            if (users.containsKey(u.getNome())) {
                return false;
            } else {
                users.put(u.getNome(), u);
                return true;
            }
        } finally {
            wl.unlock();
        }
    }

    public String login(DataInputStream in) throws IOException{
        String nome = in.readUTF();
        String pass = in.readUTF();
        rl.lock();
        String p = "";
        try{
            if(users.containsKey(nome)) {
                Utilizador aux = users.get(nome);
                if(!aux.isOnline()) {
                    if (aux.getPass().equals(pass)) {
                        aux.setOnline(true);
                        p = nome;
                    }
                }
            }
            return p;
        }finally{
            rl.unlock();
        }
    }

    public void numeroLocalizacoes (DataOutputStream out, DataInputStream in) throws IOException{
        int x = in.readInt();
        int y = in.readInt();
        int total = 0;
        rl.lock();
        try{
            for(Utilizador u : users.values()){
                if(u.getX() == x && u.getY() == y)
                    total++;
            }
            out.writeInt(total);
            out.flush();
        }finally{
            rl.unlock();
        }
    }

    public int quantosLoc(DataInputStream in) throws IOException{
        int total = 0;
        int x = in.readInt();
        int y = in.readInt();
        rl.lock();
        try {
            for (Utilizador u : users.values()) {
                if (u.getX() == x && u.getY() == y)
                    total++;
            }
            return total;
        }finally{
            rl.unlock();
        }
    }

    public int quantosLoc(int x, int y) throws IOException{
        int total =0;
        rl.lock();
        try {
            for (Utilizador u : users.values()) {
                if (u.getX() == x && u.getY() == y)
                    total++;
            }
            return total;
        }finally{
            rl.unlock();
        }
    }

    public void atualizaLoc(DataInputStream in, String u, Map<Localizacao, List<String>> loca) throws IOException {
        int x = in.readInt();
        int y = in.readInt();
        lock.lock();
        try{
            int xa = users.get(u).getX();
            int ya = users.get(u).getY();
            Localizacao l = new Localizacao(x,y);
            List<String> nomes = loca.get(l);
            if(nomes != null) {
                if(!nomes.contains(u))
                    nomes.add(u);
            }
            else{
                nomes = new ArrayList<>();
                nomes.add(u);
            }
            loca.put(l,nomes);
            users.get(u).setX(x);
            users.get(u).setY(y);
            if(quantosLoc(xa, ya) == 0) {
                c.signalAll();
            }
        }finally{
            lock.unlock();
        }
    }

    public boolean estadoDoente(DataInputStream in) throws IOException{
        String nome = in.readUTF();
        String estado = in.readUTF();
        boolean b = estado.equals("S");
        lock.lock();
        try {
            users.get(nome).setDoente(b);
            return b;
        }finally {
            lock.unlock();
        }
    }

    public Map<Localizacao,Map.Entry<Integer,Integer>> mapa(Map<Localizacao,List<String>> localizacao){
        Map<Localizacao,Map.Entry<Integer,Integer>> map = new HashMap<>();
        int total;
        int doentes = 0;
        lock.lock();
        try {
            for (Map.Entry<Localizacao, List<String>> aux : localizacao.entrySet()) {
                Localizacao l = aux.getKey();
                for (String s : aux.getValue()) {
                    if (this.users.get(s).isDoente())
                        doentes++;
                }
                total = aux.getValue().size();
                map.put(l, new AbstractMap.SimpleEntry<>(total, doentes));
                doentes = 0;
            }
        }finally {
            lock.unlock();
        }
        return map;
    }



    public void quero_ir(DataInputStream in, DataOutputStream out) throws IOException, InterruptedException {
        Thread t = new Thread(new VerifyWorker(this,in,out));
        t.start();
    }

    private static class VerifyWorker implements Runnable{
        private ReentrantLock l = new ReentrantLock();
        private Condition c = l.newCondition();
        private Utilizadores users;
        private DataInputStream in;
        private DataOutputStream out;

        public VerifyWorker(Utilizadores users, DataInputStream in, DataOutputStream out){
            this.users = users;
            this.in = in;
            this.out = out;
        }
        public void run() {
            l.lock();
            try {
                while (users.quantosLoc(in) > 0)
                    c.await();
                out.writeUTF("Localização livre");
                out.flush();
            }catch(IOException | InterruptedException e){
                e.printStackTrace();
            } finally{
                l.unlock();
            }
        }
    }
}


