package Cliente;
import Servidor.*;
import java.util.Map;

public class Menu {

    public void menuLogin(){
        System.out.println();
        System.out.println("--------------- M E N U ---------------");
        System.out.println("0 - Sair");
        System.out.println("1 - Login");
        System.out.println("2 - Criar registo");
        System.out.println("3 - Numero de utilizadores");
        System.out.println();
    }

    public void menuClienteServidor(){
        System.out.println();
        System.out.println("--------------- C L I E N T E ---------------");
        System.out.println("0 - Sair");
        System.out.println("1 - Numero de pessoas numa dada localizacao");
        System.out.println("2 - Atualizar localização atual");
        System.out.println("3 - Quero Ir");
        System.out.println("4 - Mapa de localizacoes");
    }

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
