package Cliente;
import Servidor.*;
import java.net.Socket;
import java.io.*;
import java.util.*;

public class Cliente {

    public static Utilizador parseLine(String userInput) {
        String [] tokens = userInput.split(" ");
        return new Utilizador(
                tokens[0],
                tokens[1],
                Integer.parseInt(tokens[2]),
                Integer.parseInt(tokens[3]),
                false,
                false,
                false);
    }

    public static void main(String [] args) throws IOException {
        int flag = 0;
        Socket socket = new Socket("localhost", 12345);
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        ClienteRead readC = new ClienteRead();
        ClienteWrite writeC = new ClienteWrite();
        while(flag != 2) {
            if( flag == 0) {
                flag = interpretadorLogin(systemIn, in, out,readC,writeC);
            }
            else if (flag == 1)
                flag = interpretadorServidor(systemIn, in, out,writeC,readC);
        }
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }

    public static int interpretadorLogin(BufferedReader systemIn, DataInputStream in, DataOutputStream out, ClienteRead readC, ClienteWrite writeC) throws IOException {
        Menu m = new Menu();
        boolean b;
        int flag = 0;
        String userInput;
        try {
            do {
                m.menuLogin();
                userInput = systemIn.readLine();
                Scanner scanner = new Scanner(userInput);
                int c = scanner.nextInt();
                switch (c) {
                    case 0:
                        writeC.sair(out);
                        readC.sair(in);
                        flag = 2;
                        break;
                    case 1:
                        String us = writeC.iniciarLogin(out, systemIn);
                        b = readC.iniciarLogin(in);
                        if (b) {
                            writeC.estadoDoente(out, systemIn, us);
                            b = readC.estadoDoente(in);
                            if (b) {
                                try {
                                    writeC.coordenadasLogin(out, systemIn);
                                    readC.coordenadasLogin(in);
                                    flag = 1;
                                }catch (NumberFormatException e){
                                    System.out.println("Dados invalidos");
                                }
                            }
                        }
                        break;
                    case 2:
                        writeC.criarRegisto(out, systemIn);
                        readC.criarRegisto(in);
                        break;
                    default:
                        System.out.println("Insira opção valida");
                        break;
                }
            } while (flag == 0 && (userInput = systemIn.readLine()) != null);
        }catch (InputMismatchException e){
            System.out.println("Opcao incorreta.");
        }
        return flag;

    }

    public static int interpretadorServidor(BufferedReader systemIn, DataInputStream in, DataOutputStream out, ClienteWrite writeC, ClienteRead readC) throws IOException{
        Menu m = new Menu();
        int flag = 1;
        String userInput;
        try {
            do {
                m.menuClienteServidor();
                userInput = systemIn.readLine();
                Scanner scanner = new Scanner(userInput);
                int c = scanner.nextInt();
                switch (c) {
                    case 0:
                        writeC.sair(out);
                        readC.sair(in);
                        flag = 2;
                        break;
                    case 1:
                        try {
                            writeC.quantosLocalizacao(out, systemIn);
                            readC.quantosLocalizacao(in);
                        }catch (NumberFormatException e){
                            System.out.println("Dados invalidos");
                        }
                        break;
                    case 2:
                        try {
                            writeC.atualizaLocalizacao(out, systemIn);
                            readC.atualizaLocalizacao(in);
                        }catch (NumberFormatException e){
                            System.out.println("Dados invalidos");
                        }
                        break;
                    case 3:
                        try {
                            writeC.queroIR(out, systemIn);
                            readC.queroIR(in);
                        }catch (NumberFormatException e){
                            System.out.println("Dados invalidos");
                        }
                        break;
                    case 4:
                        writeC.mapaLocalizacoes(out, systemIn);
                        int r = in.readInt();
                        if (r == 1) {
                            Map<Localizacao, Map.Entry<Integer, Integer>> mapa = readC.mapaLocalizacoes(in);
                            m.mapaDoentes(mapa);
                        } else {
                            readC.mapaLocalizacoesElse(in);
                        }
                        break;
                    default:
                        System.out.println("Insira opção valida");
                        break;
                }
            } while ((userInput = systemIn.readLine()) != null && flag == 1);
        }catch (InputMismatchException e){
            System.out.println("Opcao incorreta.");
        }
        return flag;
    }

}
