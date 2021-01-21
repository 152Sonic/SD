
import java.net.Socket;
import java.io.*;

public class Cliente {

    public static void main (String [] args) throws IOException {

        Socket socket = new Socket("localhost", 12345);
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        String userInput;

        while((userInput = systemIn.readLine())!=null){
            

        }
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }

}
