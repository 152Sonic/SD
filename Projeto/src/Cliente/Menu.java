package Cliente;
import Servidor.*;
import java.util.Map;

/**
 * Classe utilizada para imprimir informações
 */
public class Menu {

    /**
     * Função utilizada para imprimir o menu inicial
     */
    public void menuLogin(){
        System.out.println();
        System.out.println("--------------- M E N U ---------------");
        System.out.println("0 - Sair");
        System.out.println("1 - Login");
        System.out.println("2 - Criar registo");
        System.out.println("3 - Numero de utilizadores");
        System.out.println();
    }

    /**
     * Função utilizada para imprimir o menu secundário
     */
    public void menuClienteServidor(){
        System.out.println();
        System.out.println("--------------- C L I E N T E ---------------");
        System.out.println("0 - Sair");
        System.out.println("1 - Numero de pessoas numa dada localizacao");
        System.out.println("2 - Atualizar localização atual");
        System.out.println("3 - Quero Ir");
        System.out.println("4 - Mapa de localizacoes");
    }

    /**
     * Função  utilizada para imprimir o mapa de localizações
     * @param mapa Mapa que obtém a localização e o número de utilizadores que já visitaram e o número de doentes
     */
    public void mapaDoentes(Map<Localizacao, Map.Entry<Integer,Integer>> mapa){
        int cX = 0;
        int cY;
        final int N = 10;
        while(cX <= 10){
            cY = 0;
            while(cY < 10) {
                Localizacao aux = new Localizacao(cX, cY);
                if(mapa.containsKey(aux)) {
                    Map.Entry<Integer, Integer> nova = mapa.get(aux);
                    System.out.print(nova.getKey() + "|" + nova.getValue() + " ");
                }
                else{
                    System.out.print("0|0 ");
                }
                cY++;
            }
            System.out.println();
            cX++;
        }
        System.out.println("**Numero de utilizadores | Numero de doentes");
    }
}
