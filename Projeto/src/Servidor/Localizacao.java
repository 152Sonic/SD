package Servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Classe Localização
 */
public class Localizacao {
    private int x;
    private int y;

    /**
     * Construtor parametrizado
     * @param x
     * @param y
     */
    public Localizacao(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Metodo que obtém a variavel x
     * @return retorna x
     */
    public int getX() {
        return x;
    }

    /**
     * Metodo que obtém a variavel y
     * @return retorna y
     */
    public int getY() {
        return y;
    }

    /**
     * Função que transforma converte informação
     * @param out DataOutputStream onde vamos colocar toda a informação
     * @throws IOException
     */
    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(x);
        out.writeInt(y);
        out.flush();
    }

    /**
     * Função que compara dois objetos da mesma classe
     * @param o Objeto a comparar
     * @return Retorna o resultado da comparação
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Localizacao)) return false;
        Localizacao that = (Localizacao) o;
        return getX() == that.getX() &&
                getY() == that.getY();
    }

    /**
     * Função que transforma a classe numa String
     * @return Retorna a string obtida
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder("Servidor.Servidor.Localizacao{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }
}
