
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Cliente {

    public static Utilizador parseLine (String userInput) {

        String [] tokens = userInput.split(" ");
        return new Utilizador(
                tokens[0],
                tokens[1],
                Integer.parseInt(tokens[2]),
                Integer.parseInt(tokens[3]),
                false);
    }



    public static void main (String [] args) throws IOException {

        int flag = 0;
        Socket socket = new Socket("localhost", 12345);
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        while(flag != 2) {
            if( flag == 0) {
                flag = interpretadorLogin(systemIn, in, out);
            }
            else if (flag ==1)
                flag = interpretadorServidor(systemIn, in, out);
        }
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }

    public static int interpretadorLogin(BufferedReader systemIn, DataInputStream in, DataOutputStream out) throws IOException {
        System.out.println("Tela de Login");
        boolean b;
        int flag = 0;
        String userInput;
        String resposta;
        while ((userInput = systemIn.readLine()) != null && flag==0) {
            Scanner scanner = new Scanner(userInput);
            int c = scanner.nextInt();
            switch (c) {
                case 2:
                    System.out.println("Insira os dados de Registo");
                    userInput = systemIn.readLine();
                    Utilizador u = parseLine(userInput);
                    System.out.println(u.toString());
                    u.serialize(out);
                    resposta = in.readUTF();
                    System.out.println(resposta);
                    break;
                case 3:
                    out.writeInt(3);
                    out.flush();
                    resposta = String.valueOf(in.readInt());
                    System.out.println(resposta);
                    break;
                case 1:
                    System.out.println("Insira os dados de Login");
                    userInput = systemIn.readLine();
                    String[] token = userInput.split(" ");
                    out.writeInt(1);
                    out.writeUTF(token[0]);
                    out.writeUTF(token[1]);
                    out.flush();
                    b = in.readBoolean();
                    resposta = in.readUTF();
                    System.out.println(resposta);
                    if(b) flag = 1;
                    break;
                default:
                    System.out.println("Insira opção valida");
                    break;
            }
        }
        return flag;
    }

    public static int interpretadorServidor(BufferedReader systemIn, DataInputStream in, DataOutputStream out) throws IOException{
        System.out.println("Bem vindo");
        boolean b;
        int flag = 1;
        String userInput;
        String resposta;
        while ((userInput = systemIn.readLine()) != null && flag==1) {
            Scanner scanner = new Scanner(userInput);
            int c = scanner.nextInt();
            switch (c) {
                case 1:
                    System.out.println("Insira coordenadas");
                    userInput = systemIn.readLine();
                    out.writeInt(4);
                    String[] tokens = userInput.split(" ");
                    out.writeInt(Integer.parseInt(tokens[0]));
                    out.writeInt(Integer.parseInt(tokens[1]));
                    out.flush();
                    int res = in.readInt();
                    System.out.println(res);
                    break;
                default:
                    System.out.println("Insira opção valida");
                    break;
            }
        }
        return flag;
    }

}
