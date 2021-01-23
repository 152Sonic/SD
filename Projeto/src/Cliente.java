
import java.net.Socket;
import java.io.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Cliente {

    public static Utilizador parseLine(String userInput) {
        String [] tokens = userInput.split(" ");
        return new Utilizador(
                tokens[0],
                tokens[1],
                Integer.parseInt(tokens[2]),
                Integer.parseInt(tokens[3]),
                false,
                false);
    }

    public static void main(String [] args) throws IOException {
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
                    if(b){
                        System.out.print("Esta doente? [S/N] ");
                        userInput =systemIn.readLine();
                        String estado = userInput.toUpperCase();
                        out.writeUTF(us);
                        out.writeUTF(estado);
                        out.flush();
                        b = in.readBoolean();
                        resposta = in.readUTF();
                        System.out.println(resposta);
                        if(b) {
                            System.out.print("Coordenada x: ");
                            userInput = systemIn.readLine();
                            int x = Integer.parseInt(userInput);
                            System.out.print("Coordenada y: ");
                            userInput = systemIn.readLine();
                            int y = Integer.parseInt(userInput);
                            out.writeInt(x);
                            out.writeInt(y);
                            out.flush();
                            resposta = in.readUTF();
                            System.out.println(resposta);
                            flag = 1;
                        }
                    }
                    break;
                case 2:
                    System.out.print("Username: ");
                    userInput = systemIn.readLine();
                    String usRegisto = userInput;
                    System.out.print("Password: ");
                    userInput = systemIn.readLine();
                    String passRegisto = userInput;
                    Utilizador u = new Utilizador(usRegisto,passRegisto,-1,-1,false,false);
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
        int Cx;
        int Cy;
        int flag = 1;
        String userInput;
        String resposta;
         do{
            m.menuClienteServidor();
            userInput = systemIn.readLine();
            Scanner scanner = new Scanner(userInput);
            int c = scanner.nextInt();
            switch (c) {
                case 0:
                    out.writeInt(0);
                    out.flush();
                    resposta = in.readUTF();
                    System.out.println(resposta);
                    flag = 2;
                    break;
                case 1:
                    System.out.print("Coordenada x: ");
                    userInput = systemIn.readLine();
                    Cx = Integer.parseInt(userInput);
                    System.out.print("Coordenada y: ");
                    userInput = systemIn.readLine();
                    Cy = Integer.parseInt(userInput);
                    out.writeInt(1);
                    out.writeInt(Cx);
                    out.writeInt(Cy);
                    out.flush();
                    int res = in.readInt();
                    System.out.println(res);
                    break;
                case 2:
                    System.out.print("Coordenada x: ");
                    userInput = systemIn.readLine();
                    Cx = Integer.parseInt(userInput);
                    System.out.print("Coordenada y: ");
                    userInput = systemIn.readLine();
                    Cy = Integer.parseInt(userInput);
                    out.writeInt(2);
                    out.writeInt(Cx);
                    out.writeInt(Cy);
                    out.flush();
                    resposta = in.readUTF();
                    System.out.println(resposta);
                    break;
                case 3:
                    System.out.print("Coordenada x: ");
                    userInput = systemIn.readLine();
                    Cx = Integer.parseInt(userInput);
                    System.out.print("Coordenada y: ");
                    userInput = systemIn.readLine();
                    Cy = Integer.parseInt(userInput);
                    out.writeInt(3);
                    out.writeInt(Cx);
                    out.writeInt(Cy);
                    out.flush();
                    resposta = in.readUTF();
                    System.out.println(resposta);
                    break;
                case 4:
                    out.writeInt(4);
                    out.flush();
                    Map<Localizacao, Map.Entry<Integer,Integer>> mapa = new HashMap<>();
                    int tamanho = in.readInt();
                    int i = 0;
                    while(i < tamanho){
                        int x = in.readInt();
                        int y = in.readInt();
                        int total = in.readInt();
                        int doentes = in.readInt();
                        mapa.put(new Localizacao(x,y),new AbstractMap.SimpleEntry<>(total,doentes));
                        i++;
                    }
                    m.mapaDoentes(mapa);
                    break;
                default:
                    System.out.println("Insira opção valida");
                    break;
            }
        }while ((userInput = systemIn.readLine()) != null && flag==1);
        return flag;
    }

}
