package servidor;
import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

public class Serpy {

    ServerSocket server;
    Socket cli;
    String word;			//id en el servidor
	String nombre_usuario_db;	//nombre de usuario base de datos
	String id_usuario_db;		//id base de datos

    public Serpy(ServerSocket s) throws IOException {
        server = s;
    }
    
    public void initialize(int id_i, int miners_i) throws IOException{
        word = id_i + " " + miners_i;
        getClient(id_i);
    }
    
    public void getClient(int i) throws IOException{
        System.out.println("Esperando cliente (cantidad actual: "+(i)+" clientes)");
        cli = server.accept();
		System.out.println("Conectado...");
    }
    
    public String initLoop() throws IOException,ClassNotFoundException,SQLException {
        //String recibido = "", enviado = "";
        OutputStreamWriter outw = new OutputStreamWriter(cli.getOutputStream(), "UTF8");
        InputStreamReader inw = new InputStreamReader(cli.getInputStream(), "UTF8");
        System.out.println("Cliente conseguido!");
		
		//// conexion mysql
		Connection conexion = null;
        try{
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/servidor", "servidor1" , "1234");
            System.out.println("Conexión a la base de datos satisfactoria! "+word);
        }catch (Exception exc) {
            exc.printStackTrace();
        }finally {
            if (conexion != null) {
                conexion.close();
            }
        }
		
		////  usuario:constrasena
        String recibido = "";
        char[] cbuf = new char[512];
        while(recibido.equals("")){
            try {
                inw.read(cbuf);
                for (char c : cbuf) {
                    recibido += c;
                    if (c == 00) {
						String[] partes = recibido.split(":");
						String user = partes[0];
						String password = partes[1];
						if(!iniciar_sesion(conexion,user,password)){
							recibido = "";
						}
						break;
                    }
                }
            }catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
		
		String lista = "";
		
		try (PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM archivos WHERE id_user='"+id_usuario_db+"'")) {
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
				lista += rs.getString("nombre_file")+",";
		} catch (SQLException sqle) { 
			System.out.println("Error en la ejecución:" + sqle.getErrorCode() + " " + sqle.getMessage());    
		}
		
		outw.write(word.toCharArray());
        //outw.flush();
		
        return lista;
    }
	
	public boolean iniciar_sesion(Connection conexion, String user, String pass) throws IOException,ClassNotFoundException,SQLException {
		
		try (PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM usuarios WHERE nombre_user='"+user+"' AND pass_user='"+pass+"' LIMIT 1")) {
			ResultSet rs = stmt.executeQuery();
			rs.next();
			id_usuario_db = rs.getString("id_user");
			nombre_usuario_db = rs.getString("nombre_user");
			return true;
		} catch (SQLException sqle) {
			System.out.println("Error en la ejecución:"+ sqle.getErrorCode() + " " + sqle.getMessage());
			return false;
		}
	}
}