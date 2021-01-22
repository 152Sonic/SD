
import java.net.Socket;
import java.io.*;

public class Cliente {

    public static Utilizador parseLine (String[] tokens) {

        return new Utilizador(
                tokens[1],
                tokens[2],
                Integer.parseInt(tokens[3]),
                Integer.parseInt(tokens[4]),
                false);
    }



    public static void main (String [] args) throws IOException {

        Socket socket = new Socket("localhost", 12345);
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        String userInput;
        String resposta;

        while((userInput = systemIn.readLine())!=null){
            String[] token = userInput.split(" ");
            switch(token[0]){
                case("Registar"):
                    Utilizador u = parseLine(token);
                    System.out.println(u.toString());
                    u.serialize(out);
                    resposta = in.readUTF();
                    System.out.println(resposta);
                    break;
                case ("Utilizadores"):
                    out.writeUTF(token[0]);
                    out.flush();
                    resposta = String.valueOf(in.readInt());
                    System.out.println(resposta);
                    break;
                case("Login"):
                    out.writeUTF(token[0]);
                    out.writeUTF(token[1]);
                    out.writeUTF(token[2]);
                    out.flush();
                    resposta = in.readUTF();
                    System.out.println(resposta);
                    break;
                default:
                    System.out.println("Insira opção valida");
                    break;
            }
        }
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }

}
