package Servidor;

import java.io.*;

/**
 * Classe utilizador
 */
public class Utilizador {
    private String nome;
    private String pass;
    private int x;
    private int y;
    private boolean doente;
    private boolean online;
    private boolean especial;

    /**
     * Construtor parametrizado
     * @param nome
     * @param pass
     * @param x
     * @param y
     * @param doente
     * @param online
     * @param especial
     */
    public Utilizador (String nome, String pass, int x, int y, boolean doente,boolean online,boolean especial){
        this.nome = nome;
        this.pass = pass;
        this.x = x;
        this.y = y;
        this.doente = doente;
        this.online = online;
        this.especial = especial;
    }

    /**
     * Funçaõ que converte a informação de binário
     * @param in DataInputStream onde recebemos o resultado do problema
     * @return Retorna o Utilizador
     * @throws IOException
     */
    public static Utilizador deserialize(DataInputStream in) throws IOException {
        String nome = in.readUTF();
        String pass = in.readUTF();
        int x = in.readInt();
        int y = in.readInt();
        boolean doente = in.readBoolean();
        boolean online = in.readBoolean();
        boolean especial = in.readBoolean();

        return new Utilizador(nome,pass,x,y,doente,online,especial);
    }

    /**
     * Funçaõ que converte a informação para binário
     * @param out DataOutputStream onde vamos colocar toda a informação
     * @throws IOException
     */
    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(2);
        out.writeUTF(this.nome);
        out.writeUTF(this.pass);
        out.writeInt(this.x);
        out.writeInt(this.y);
        out.writeBoolean(this.doente);
        out.writeBoolean(this.online);
        out.writeBoolean(this.especial);
        System.out.println();
        out.flush();
    }

    /**
     * Função que obtém a variavel nome
     * @return Retorna Nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Função que obtém a variavel Pass
     * @return Retorna Pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * Função que obtém a variavel X
     * @return Retorna x
     */
    public int getX() {
        return x;
    }

    /**
     * Função que atualiza a variavel X
     * @param x novo valor
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Função que obtém a variavel y
     * @return Retorna y
     */
    public int getY() {
        return y;
    }

    /**
     * Função que atualiza a variavel y
     * @param y novo valor
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Função que sabe se está ou não doente
     * @return Retorna a resposta à questão
     */
    public boolean isDoente() {
        return doente;
    }

    /**
     * Função que atualiza estado de doente
     * @param doente Novo estado
     */
    public void setDoente(boolean doente) {
        this.doente = doente;
    }

    /**
     * Função que sabe se está ou não online
     * @return Retorna a resposta à questão
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * Função que atualiza estado de online
     * @param online Novo estado
     */
    public void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * Função que sabe se é um utilizador especial ou não
     * @return Retorna a resposta à questão
     */
    public boolean isEspecial() {
        return especial;
    }

    /**
     * Função que transforma classe numa string
     * @return Retorna astring obtida
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder("Servidor.Servidor.Utilizador{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", pass='").append(pass).append('\'');
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", doente=").append(doente);
        sb.append(", online=").append(online);
        sb.append(", especial=").append(especial);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Função que cria um clone da classe
     * @return Retorna o clone
     */
    public Utilizador clone(){
        Utilizador u = new Utilizador(this.nome, this.pass, this.x, this.y, this.doente,this.online,this.especial);
        return u;
    }
}
