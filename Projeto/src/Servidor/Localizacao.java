package Servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Localizacao {
    private int x;
    private int y;

    public Localizacao(int x, int y){
        this.x = x;
        this.y = y;
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

    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(x);
        out.writeInt(y);
        out.flush();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Localizacao)) return false;
        Localizacao that = (Localizacao) o;
        return getX() == that.getX() &&
                getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Servidor.Servidor.Localizacao{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }
}
