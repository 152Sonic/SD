package Cliente;
import Servidor.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilizada para receber toda a informação do servidor
 */
public class ClienteRead {
    private boolean b;
    private String resposta;

    /**
     * Construtor vazio
     */
    public ClienteRead() {
        this.b = false;
        this.resposta = "";
    }


    // ------------- I N T E R P R E T A D O R   L O G I N ------------------------

    // ---------------------------------- LOGIN -----------------------------------

    /**
     * Função que recebe dados para login
     * @param in DataInputStream onde recebemos o resultado do problema
     * @return Retorna um boolean para saber se podemos continuar posteriormente com o login
     * @throws IOException
     */
    public boolean iniciarLogin(DataInputStream in) throws IOException {
        b = in.readBoolean();
        resposta = in.readUTF();
        System.out.println(resposta);
        return b;
    }

    /**
     * Função que recebe informação se está doente ou não
     * @param in DataInputStream onde recebemos o resultado do problema
     * @return Retorna um boolean para saber se podemos continuar posteriormente com o login
     * @throws IOException
     */
    public boolean estadoDoente(DataInputStream in) throws IOException{
        b = in.readBoolean();
        resposta = in.readUTF();
        System.out.println(resposta);
        return b;
    }

    /**
     * Função que recebe informação daa conclusão do login
     * @param in DataInputStream onde recebemos o resultado do problema
     * @throws IOException
     */
    public void coordenadasLogin(DataInputStream in) throws IOException{
        resposta = in.readUTF();
        System.out.println(resposta);
    }

    // ----------------------------------- REGISTO --------------------------------

    /**
     * Função que informa se registo foi concluido com sucesso ou não
     * @param in DataInputStream onde recebemos o resultado do problema
     * @throws IOException
     */
    public void criarRegisto(DataInputStream in) throws IOException{
        resposta = in.readUTF();
        System.out.println(resposta);
    }


    // ------------- I N T E R P R E T A D O R   S E R V I D O R ------------------


    // ----------------------------------- SAIR -----------------------------------

    /**
     * Função que informa se termino da aplicação foi com sucesso ou não
     * @param in DataInputStream onde recebemos o resultado do problema
     * @throws IOException
     */
    public void sair(DataInputStream in) throws IOException {
        resposta = in.readUTF();
        System.out.println(resposta);
    }


    // ------------------- QUANTOS LOCALIZACAO ------------------------------------

    /**
     * Função que responde ao problema em questão
     * @param in DataInputStream onde recebemos o resultado do problema
     * @throws IOException
     */
    public void quantosLocalizacao(DataInputStream in) throws IOException{
        int res = in.readInt();
        System.out.println(res);
    }

    // ------------------- ATUALIZA LOCALIZACAO ------------------------------------

    /**
     * Função que informa se atualização foi bem sucedida ou não
     * @param in DataInputStream onde recebemos o resultado do problema
     * @throws IOException
     */
    public void atualizaLocalizacao(DataInputStream in) throws IOException{
        resposta = in.readUTF();
        System.out.println(resposta);
    }

    // ------------------------------- QUERO IR ------------------------------------

    /**
     * Função que informa se mudança de localização é possivel ou não
     * @param in DataInputStream onde recebemos o resultado do problema
     * @throws IOException
     */
    public void queroIR(DataInputStream in) throws IOException{
        resposta = in.readUTF();
        System.out.println(resposta);
    }

    // ---------------------------------- MAPA -------------------------------------

    /**
     * Função que recebe o mapa de localizações pedido no problema em questão
     * @param in DataInputStream onde recebemos o resultado do problema
     * @return Retorna o Mapa em questão
     * @throws IOException
     */
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

    /**
     * Função utilizada para o caso de não ter permissão para ver o mapa
     * @param in DataInputStream onde recebemos o resultado do problema
     * @throws IOException
     */
    public void mapaLocalizacoesElse(DataInputStream in) throws IOException{
        resposta = in.readUTF();
        System.out.println(resposta);
    }



}
