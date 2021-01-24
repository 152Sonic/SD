package Servidor;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ServerWorker implements Runnable {
    private Socket socket;
    private Utilizadores users;
    private Map<Localizacao,List<String>> localizacoes;
    private String u;
    private ReentrantLock l;
    private Condition c;

    public ServerWorker(Socket socket, Utilizadores users, Map<Localizacao,List<String>> loc){
        this.socket = socket;
        this.users = users;
        this.u = new String();
        this.localizacoes = loc;
        this.l = new ReentrantLock();
        this.c = l.newCondition();
    }

    public void run (){
        try{
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            int flag = 0;
            while(flag != 2) {
                if( flag == 0) {
                    flag = interpretadorLogin( in, out);
                }
                else if (flag == 1)
                    flag = interpretadorServidor( in, out);
            }
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public  int interpretadorLogin(DataInputStream in,DataOutputStream out) throws IOException{
        int flag =0;
        int c;
        String r;
        boolean b;
        do {
            c = in.readInt();
            switch (c) {
                case 0:
                    out.writeUTF("Até à próxima!");
                    out.flush();
                    flag = 2;
                    break;
                case 1:
                    r = users.login(in);
                    if(!r.equals("")){
                        out.writeBoolean(true);
                        this.u = r;
                        out.writeUTF("Autenticado com sucesso");
                        out.flush();
                        int d = users.estadoDoente(in);
                        if(d == 1) {
                            out.writeBoolean(true);
                            flag = 1;
                            out.writeUTF("Pode continuar");
                            out.flush();
                            users.atualizaLoc(in, u,localizacoes);
                            out.writeUTF("Localização atualizada!");
                            out.flush();
                        }
                        else if(d == 2){
                            out.writeBoolean(false);
                            out.writeUTF("Esta doente. Aplicacao  indisponivel.");
                            out.flush();
                        }
                        else{
                            out.writeBoolean(false);
                            out.writeUTF("Opcao Incorreta");
                            out.flush();
                        }
                    } else {
                        out.writeBoolean(false);
                        out.writeUTF("Dados de login errados");
                        out.flush();
                    }
                    users.getusers().get(r).setOnline(false);
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
                default:
                    break;
            }
        }while(c!=0 && flag==0);

        return flag;
    }

    public  int interpretadorServidor(DataInputStream in,DataOutputStream out) throws IOException, InterruptedException {
        int flag = 1;
        int c;
        boolean b;
        do{
            c = in.readInt();
            switch(c){
                case 0:
                    users.getusers().get(u).setOnline(false);
                    out.writeUTF("Até à próxima!");
                    out.flush();
                    flag = 2;
                    break;
                case 1:
                    System.out.println("Entrei");
                    int res = users.quantosLoc(in);
                    out.writeInt(res);
                    out.flush();
                    break;
                case 2:
                    users.atualizaLoc(in,u,localizacoes);
                    out.writeUTF("Localização atualizada!");
                    out.flush();
                    break;
                case 3:
                    int x = in.readInt();
                    int y = in.readInt();
                    users.quero_ir(x,y, out);
                    break;
                case 4:
                    Utilizador uti = this.users.getusers().get(u);
                    if(uti.isEspecial()) {
                        out.writeInt(1);
                        Map<Localizacao, Map.Entry<Integer, Integer>> m = users.mapa(this.localizacoes);
                        out.writeInt(m.size());
                        for (Map.Entry<Localizacao, Map.Entry<Integer, Integer>> aux : m.entrySet()) {
                            aux.getKey().serialize(out);
                            out.writeInt(aux.getValue().getKey());
                            out.writeInt(aux.getValue().getValue());
                            out.flush();
                        }
                    }
                    else {
                        out.writeInt(0);
                        out.writeUTF("Nao tem permissoes!");
                        out.flush();
                    }
                    break;
                default:
                    break;
            }
        }while(c!=-1 && flag == 1);
        return flag;
    }
}
