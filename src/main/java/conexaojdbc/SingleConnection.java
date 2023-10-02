package conexaojdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
	
	private static String url = "jdbc:postgresql://localhost:5433/posjava";
	//private static String url = "jdbc:postgresql://localhost:5433/postgres?currentSchema=SCHEMA";
	private static String password = "admin";
	private static String user = "postgres";
	private static Connection connection = null;
	
	
	static {
		conectar();
	}
	
	public SingleConnection () {
		conectar();
	}
	
	private static void conectar() {
		try {
			if (connection == null) {
				Class.forName("org.postgresql.Driver");  // carrega o driver
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false); /* false: não fazer operação automaticamente,
				 									o proprio desenvolverdor decide depois da aplicacao	*/
				System.out.println("Conectou com sucesso.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
		
}


