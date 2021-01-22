import java.io.*;
import java.net.Socket;

class ServerWorker implements Runnable {
    private Socket socket;
    private Utilizadores users;

    public ServerWorker(Socket socket, Utilizadores users){
        this.socket = socket;
        this.users = users;
    }

    public void run (){
        try{
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            int c;
            boolean b;
            do {
                c = in.readInt();
                switch (c) {
                    case 1:
                        b = users.login(in);
                        out.writeBoolean(b);
                        if(b){
                            out.writeUTF("Autenticado com sucesso");
                            out.flush();
                        } else {
                            out.writeUTF("Dados de login errados");
                            out.flush();
                        }
                        break;
                    case 2:
                        b = users.registar(in);
                        if (b) {
                            out.writeUTF("Registado com sucesso");
                            out.flush();
                        } else {
                            out.writeUTF("Nome de utilizador impossivel");
                            out.flush();
                        }
                        break;
                    case 3:
                        int total = users.getusers().size();
                        out.writeInt(total);
                        out.flush();
                        break;
                    case 4:
                        System.out.println("Entrei");
                        int res = users.quantosLoc(in);
                        out.writeInt(res);
                        out.flush();
                        break;
                    default:
                        break;
                }
            }while(c!=0);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static int interpretadorLogin(BufferedReader systemIn, DataInputStream in,DataOutputStream out) throws IOException{
        return 0;
    }

    public static int interpretadorServidor(BufferedReader systemIn, DataInputStream in,DataOutputStream out) throws IOException{
        return 0;
    }
}
