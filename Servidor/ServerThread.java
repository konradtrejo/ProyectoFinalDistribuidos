package servidor;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.sql.*;

public class ServerThread implements Callable{
    Serpy s;
    
    public ServerThread(Serpy si){
        s = si;
    }
    
    @Override
    public String call() throws IOException,ClassNotFoundException,SQLException {
        return s.initLoop();
    }
}
