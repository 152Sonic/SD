
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
            else if (flag == 1)
                flag = interpretadorServidor(systemIn, in, out);
        }
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }

    public static int interpretadorLogin(BufferedReader systemIn, DataInputStream in, DataOutputStream out) throws IOException {
        Menu m = new Menu();
        boolean b;
        int flag = 0;
        String userInput;
        String resposta;
        do{
            m.menuLogin();
            userInput = systemIn.readLine();
            Scanner scanner = new Scanner(userInput);
            int c = scanner.nextInt();
            switch (c) {
                case 1:
                    System.out.print("Username: ");
                    userInput = systemIn.readLine();
                    String us = userInput;
                    System.out.print("Password: ");
                    userInput = systemIn.readLine();
                    String p = userInput;
                    out.writeInt(1);
                    out.writeUTF(us);
                    out.writeUTF(p);
                    out.flush();
                    b = in.readBoolean();
                    resposta = in.readUTF();
                    System.out.println(resposta);
                    if(b) flag = 1;
                    break;
                case 2:
                    System.out.print("Username: ");
                    userInput = systemIn.readLine();
                    String usRegisto = userInput;
                    System.out.print("Password: ");
                    userInput = systemIn.readLine();
                    String passRegisto = userInput;
                    System.out.print("Coordenada X: ");
                    userInput = systemIn.readLine();
                    int xRegisto = Integer.parseInt(userInput);
                    System.out.print("Coordenada Y: ");
                    userInput = systemIn.readLine();
                    int yRegisto = Integer.parseInt(userInput);
                    Utilizador u = new Utilizador(usRegisto,passRegisto,xRegisto,yRegisto,false);
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
                default:
                    System.out.println("Insira opção valida");
                    break;
            }
        }while (flag == 0 && (userInput = systemIn.readLine()) != null);
        return flag;
    }

    public static int interpretadorServidor(BufferedReader systemIn, DataInputStream in, DataOutputStream out) throws IOException{
        Menu m = new Menu();
        boolean b;
        int flag = 1;
        String userInput;
        String resposta;
         do{
            m.menuClienteServidor();
            userInput = systemIn.readLine();
            Scanner scanner = new Scanner(userInput);
            int c = scanner.nextInt();
            switch (c) {
                case 1:
                    System.out.print("Coordenada x: ");
                    userInput = systemIn.readLine();
                    int xC = Integer.parseInt(userInput);
                    System.out.print("Coordenada y: ");
                    userInput = systemIn.readLine();
                    int yC = Integer.parseInt(userInput);
                    out.writeInt(4);
                    out.writeInt(xC);
                    out.writeInt(yC);
                    out.flush();
                    int res = in.readInt();
                    System.out.println(res);
                    break;
                default:
                    System.out.println("Insira opção valida");
                    break;
            }
        }while ((userInput = systemIn.readLine()) != null && flag==1);
        return flag;
    }

}
