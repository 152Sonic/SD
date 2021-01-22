import java.io.*;

public class Utilizador {
    private String nome;
    private String pass;
    private int x;
    private int y;
    private boolean doente;


    public Utilizador (String nome, String pass, int x, int y){
        this.nome = nome;
        this.pass = pass;
        this.x = x;
        this.y = y;
        this.doente = false;
    }

    public Utilizador (String nome, String pass, int x, int y, boolean doente){
        this.nome = nome;
        this.pass = pass;
        this.x = x;
        this.y = y;
        this.doente = doente;
    }

    public static Utilizador deserialize(DataInputStream in) throws IOException {
        String nome = in.readUTF();
        String pass = in.readUTF();
        int x = in.readInt();
        int y = in.readInt();
        boolean doente = in.readBoolean();

        return new Utilizador(nome,pass,x,y,doente);
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeUTF("Registar");
        out.writeUTF(this.nome);
        out.writeUTF(this.pass);
        out.writeInt(this.x);
        out.writeInt(this.y);
        out.writeBoolean(this.doente);
        System.out.println();
        out.flush();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isDoente() {
        return doente;
    }

    public void setDoente(boolean doente) {
        this.doente = doente;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Utilizador{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", pass='").append(pass).append('\'');
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", doente=").append(doente);
        sb.append('}');
        return sb.toString();
    }
}
