package Servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Class Runnable para resolver um dos problemas básicos
 */
public class VerifyWorker implements Runnable{
    private DataOutputStream out;
    private int x;
    private int y;
    private Lock wl;
    private Condition c;
    private Utilizadores users;

    /**
     * Construtor parametrizado
     * @param x
     * @param y
     * @param out
     * @param users
     * @param wl
     * @param c
     * @throws IOException
     */
    public VerifyWorker(int x, int y,DataOutputStream out, Utilizadores users,Lock wl, Condition c) throws IOException {
        this.users = users;
        this.x =x;
        this.y = y;
        this.out = out;
        this.wl = wl;
        this.c = c;

    }

    /**
     * Função run
     */
    public void run() {
        wl.lock();
        try {
            while (users.quantosLoc(x,y) > 0) {
                c.await();
            }
            out.writeUTF("Localização livre");
            out.flush();
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        } finally{
            wl.unlock();
        }
    }
}
