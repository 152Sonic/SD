package Cliente;
import Servidor.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class ClienteWrite {
    private String userInput;
    private int x;
    private int y;

    public ClienteWrite() {
        this.userInput = "";
        x = -1;
        y = -1;
    }

    // ------------------------- I N T E R P R E T A D O R   L O G I N -----------------------------------

    // ------------------------------------------- LOGIN -------------------------------------------------
    public String iniciarLogin(DataOutputStream out, BufferedReader systemIn) throws IOException {
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
        return us;
    }

    public void estadoDoente(DataOutputStream out,BufferedReader systemIn,String nome) throws IOException{
        System.out.print("Esta doente? [S/N] ");
        userInput = systemIn.readLine();
        String estado = userInput.toUpperCase();
        out.writeUTF(nome);
        out.writeUTF(estado);
        out.flush();
    }

    public void coordenadasLogin(DataOutputStream out, BufferedReader systemIn) throws IOException{
        System.out.print("Coordenada x: [0-9] ");
        userInput = systemIn.readLine();
        int x = Integer.parseInt(userInput);
        System.out.print("Coordenada y: [0-9] ");
        userInput = systemIn.readLine();
        int y = Integer.parseInt(userInput);
        out.writeInt(x);
        out.writeInt(y);
        out.flush();
    }


    // --------------------------------------------- REGISTO ---------------------------------------------

    public void criarRegisto(DataOutputStream out, BufferedReader systemIn) throws IOException{
        System.out.print("Username: ");
        userInput = systemIn.readLine();
        String usRegisto = userInput;
        System.out.print("Password: ");
        userInput = systemIn.readLine();
        String passRegisto = userInput;
        Utilizador u = new Utilizador(usRegisto,passRegisto,-1,-1,false,false,false);
        u.serialize(out);
    }

    // ------------------------- I N T E R P R E T A D O R   S E R V I D O R ----------------------------

    // ----------------------------------------------- SAIR ---------------------------------------------
    public void sair(DataOutputStream out) throws IOException{
        out.writeInt(0);
        out.flush();
    }

    // ------------------------------------ QUANTOS LOCALIZACAO -----------------------------------------
    public void quantosLocalizacao(DataOutputStream out,BufferedReader systemIn) throws IOException{
        System.out.print("Coordenada x: [0-9] ");
        userInput = systemIn.readLine();
        x = Integer.parseInt(userInput);
        System.out.print("Coordenada y: [0-9] ");
        userInput = systemIn.readLine();
        y = Integer.parseInt(userInput);
        out.writeInt(1);
        out.writeInt(x);
        out.writeInt(y);
        out.flush();
    }


    // ------------------------------------ ATUALIZA LOCALIZACAO --------------------------------------
    public void atualizaLocalizacao(DataOutputStream out, BufferedReader systemIn) throws IOException{
        System.out.print("Coordenada x: [0-9] ");
        userInput = systemIn.readLine();
        x = Integer.parseInt(userInput);
        System.out.print("Coordenada y: [0-9] ");
        userInput = systemIn.readLine();
        y = Integer.parseInt(userInput);
        out.writeInt(2);
        out.writeInt(x);
        out.writeInt(y);
        out.flush();
    }

    // ----------------------------------------- QUERO IR ---------------------------------------------

    public void queroIR(DataOutputStream out, BufferedReader systemIn) throws IOException{
        System.out.print("Coordenada x: [0-9] ");
        userInput = systemIn.readLine();
        x = Integer.parseInt(userInput);
        System.out.print("Coordenada y: [0-9] ");
        userInput = systemIn.readLine();
        y = Integer.parseInt(userInput);
        out.writeInt(3);
        out.writeInt(x);
        out.writeInt(y);
        out.flush();
    }

    // ---------------------------------- MAPA -------------------------------------
    public void mapaLocalizacoes(DataOutputStream out, BufferedReader systemIn) throws IOException{
        out.writeInt(4);
        out.flush();
    }


}
