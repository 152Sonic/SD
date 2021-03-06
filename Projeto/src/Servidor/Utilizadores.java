package Servidor;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Classe Utilizadores
 */
public class Utilizadores {
    private Map<String, Utilizador> users;
    private ReentrantReadWriteLock l = new ReentrantReadWriteLock();
    private Lock rl = l.readLock();
    private Lock wl = l.writeLock();
    private Condition c = wl.newCondition();

    /**
     * Construtor vazio
     */
    public Utilizadores (){
        this.l = new ReentrantReadWriteLock();
        this.users = new HashMap<>();

        users.put("MariaBia13", new Utilizador("MariaBia13", "130300", 4, 5, false,false,false));
        users.put("TekasGG", new Utilizador("TekasGG", "161100", 1, 1, false,false,false));
        users.put("Xico_Franco", new Utilizador("Xico_Franco", "231299", 4, 5, true,false,false));
        users.put("MariaQB", new Utilizador("MariaQB", "280900", 5, 7, false,false,true));

    }

    /**
     * Funcção que obtém a variavel users
     * @return Retorna users
     */
    public Map<String, Utilizador> getusers(){
        Map<String, Utilizador> aux = new HashMap<>();
        for(Utilizador u : users.values()){
            aux.put(u.getNome(),u);
        }
        return aux;
    }

    /**
     * Função que cria um registo novo
     * @param in DataInputStream onde recebemos o resultado do problema
     * @return Retorna como correu o  novo  registo
     * @throws IOException
     */
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

    /**
     * Função que efetua o login
     * @param in DataInputStream onde recebemos o resultado do problema
     * @return Retorna o utilizador com sessão iniciada
     * @throws IOException
     */
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

    /**
     * Função que calcula o número de utilizadores numa dada localização
     * @param in DataInputStream onde recebemos o resultado do problema
     * @return Retorna o número de utilizadores na localização
     * @throws IOException
     */
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

    /**
     * Funçaõ que obtém o número de utilizadores numa dada localização
     * @param x Coordenada x da localização
     * @param y Coordenada y da localização
     * @return Retorna o  número de utilizadores na localização
     */
    public int quantosLoc(int x, int y) {
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

    /**
     * Função qie atualiza a localização de um dado utilizador
     * @param in DataInputStream onde recebemos o resultado do problema
     * @param u Nome do utilizador
     * @param loca Mapa das localizações em histórico
     * @throws IOException
     */
    public void atualizaLoc(DataInputStream in, String u, Map<Localizacao, List<String>> loca) throws IOException {
        int x = in.readInt();
        int y = in.readInt();
        wl.lock();
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
            wl.unlock();
        }
    }

    /**
     * Função que atualiza estado de doente do utilizador
     * @param in DataInputStream onde recebemos o resultado do problema
     * @return Retorna um inteiro para posterior comparação de dados
     * @throws IOException
     */
    public int estadoDoente(DataInputStream in) throws IOException{
        String nome = in.readUTF();
        String estado = in.readUTF();
        int b = 0;
        if(estado.equals("S"))
            b = 2;
        else if(estado.equals("N"))
            b = 1;
        wl.lock();
        try {
            if(b != 0)
                users.get(nome).setDoente(b == 2);
            return b;
        }finally {
            wl.unlock();
        }
    }

    /**
     * Função que obtém, em todas as localizações, o número total de utilizadores que já lá estiveram e o número de doentes.
     * @param localizacao Mapa de localizações em histórico
     * @return
     */
    public Map<Localizacao,Map.Entry<Integer,Integer>> mapa(Map<Localizacao,List<String>> localizacao){
        Map<Localizacao,Map.Entry<Integer,Integer>> map = new HashMap<>();
        int total;
        int doentes = 0;
        wl.lock();
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
            wl.unlock();
        }
        return map;
    }

    /**
     * Função que obtém a permissão para saber se podemos ou não mudar de localização
     * @param x Coordenada x
     * @param y Coordenada y
     * @param out DataOutputStream onde vamos colocar toda a informação
     * @throws IOException
     * @throws InterruptedException
     */
    public void quero_ir(int x, int y, DataOutputStream out) throws IOException, InterruptedException {
        Thread t = new Thread(new VerifyWorker(x, y, out,this, wl, c));
        t.start();
    }
}


