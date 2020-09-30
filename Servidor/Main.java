package servidor;
import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException{
        int port = 2018;
        int clientes_max = 20;
        String word = "cliente";
        ServerSocket serversocket = new ServerSocket(port);
        ServerThread[] st = new ServerThread[clientes_max];
        Serpy[] sp = new Serpy[clientes_max];
        
        int i = 0;
        
        while(i<clientes_max){
            sp[i] = new Serpy(serversocket);
            sp[i].initialize(i, clientes_max);
            st[i] = new ServerThread(sp[i]);
			i++;
        }
        
    }
}