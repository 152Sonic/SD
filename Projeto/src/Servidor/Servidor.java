package Servidor;

import java.net.ServerSocket;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe principal do Servidor
 */
public class Servidor {

    /**
     * Metodo main do Servidor
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Utilizadores users = new Utilizadores();
        ServerSocket ss = new ServerSocket(12345);
        Map<Localizacao,List<String>> local = new HashMap<>();
        Localizacao l1 = new Localizacao(4,5);
        Localizacao l2 = new Localizacao(1,1);
        Localizacao l3 = new Localizacao(5,7);
        List<String> nomes1 = new ArrayList<>();
        nomes1.add("MariaBia13");
        nomes1.add("Xico_Franco");
        local.put(l1,nomes1);
        List<String> nomes2 = new ArrayList<>();
        nomes2.add("TekasGG");
        local.put(l2,nomes2);
        List<String> nomes3 = new ArrayList<>();
        nomes3.add("MariaQB");
        local.put(l3,nomes3);
        try {
            while (true) {
                Socket socket = ss.accept();
                Thread worker = new Thread(new ServerWorker(socket, users,local));
                worker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}