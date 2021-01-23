import java.net.ServerSocket;
import java.io.*;
import java.net.Socket;


public class Servidor {

    public static void main(String[] args) throws IOException {
        Utilizadores users = new Utilizadores();
        ServerSocket ss = new ServerSocket(12345);

        try {
            while (true) {
                Socket socket = ss.accept();
                Thread worker = new Thread(new ServerWorker(socket, users));
                worker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}