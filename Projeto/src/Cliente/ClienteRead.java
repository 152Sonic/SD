package Cliente;
import Servidor.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class ClienteRead {
    private boolean b;
    private String resposta;

    public ClienteRead() {
        this.b = false;
        this.resposta = "";
    }


    // ------------- I N T E R P R E T A D O R   L O G I N ------------------------

    // ---------------------------------- LOGIN -----------------------------------
    public boolean iniciarLogin(DataInputStream in) throws IOException {
        b = in.readBoolean();
        resposta = in.readUTF();
        System.out.println(resposta);
        return b;
    }

    public boolean estadoDoente(DataInputStream in) throws IOException{
        b = in.readBoolean();
        resposta = in.readUTF();
        System.out.println(resposta);
        return b;
    }

    public void coordenadasLogin(DataInputStream in) throws IOException{
        resposta = in.readUTF();
        System.out.println(resposta);
    }

    // ----------------------------------- REGISTO --------------------------------
    public void criarRegisto(DataInputStream in) throws IOException{
        resposta = in.readUTF();
        System.out.println(resposta);
    }


    // ------------- I N T E R P R E T A D O R   S E R V I D O R ------------------


    // ----------------------------------- SAIR -----------------------------------
    public void sair(DataInputStream in) throws IOException {
        resposta = in.readUTF();
        System.out.println(resposta);
    }


    // ------------------- QUANTOS LOCALIZACAO ------------------------------------
    public void quantosLocalizacao(DataInputStream in) throws IOException{
        int res = in.readInt();
        System.out.println(res);
    }

    // ------------------- ATUALIZA LOCALIZACAO ------------------------------------
    public void atualizaLocalizacao(DataInputStream in) throws IOException{
        resposta = in.readUTF();
        System.out.println(resposta);
    }

    // ------------------------------- QUERO IR ------------------------------------
    public void queroIR(DataInputStream in) throws IOException{
        resposta = in.readUTF();
        System.out.println(resposta);
    }

    // ---------------------------------- MAPA -------------------------------------
    public Map<Localizacao, Map.Entry<Integer,Integer>> mapaLocalizacoes(DataInputStream in) throws IOException{
        Map<Localizacao, Map.Entry<Integer, Integer>> mapa = new HashMap<>();
        int tamanho = in.readInt();
        int i = 0;
        while (i < tamanho) {
            int x = in.readInt();
            int y = in.readInt();
            int total = in.readInt();
            int doentes = in.readInt();
            mapa.put(new Localizacao(x, y), new AbstractMap.SimpleEntry<>(total, doentes));
            i++;
        }
        return mapa;
    }

    public void mapaLocalizacoesElse(DataInputStream in) throws IOException{
        resposta = in.readUTF();
        System.out.println(resposta);
    }



}
