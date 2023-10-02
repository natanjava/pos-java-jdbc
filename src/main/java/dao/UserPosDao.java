package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

/*Classe de persistencia, depois que criar a tabela no BD*/

public class UserPosDao {

	private Connection connection;

	public UserPosDao() {
		connection = SingleConnection.getConnection();

	}

	public void salvar(Userposjava userposjava) throws SQLException {
		try {
			String sql = "insert into public.userposjava (nome, email) values (?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			/*
			 * insert.setLong(1, userposjava.getId()); insert.setString(2,
			 * userposjava.getNome()); insert.setString(3, userposjava.getEmail());
			 */
			insert.setString(1, "breno");
			insert.setString(2, "brenoEmail");
			insert.execute(); // EXECUTE: criar;
			connection.commit(); // salva no banco
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public void salvarTelefone (Telefone telefone) {
		try {
			String sql = "INSERT INTO public.telefoneuser(numero, tipo, usuariopessoa) VALUES (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
			statement.execute(); //EXECUTE: criar;
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback(); 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} 
	}
	

	public List<Userposjava> retornoar() throws Exception {
		List<Userposjava> retorno = new ArrayList<Userposjava>();
		String sql = "Select * from userposjava";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery(); // RECUPERAR MAIS QUE UM DADO : execute Query
		while (resultado.next()) {
			Userposjava userposjava = new Userposjava();
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));
			retorno.add(userposjava);
		}
		return retorno;
	}

	public Userposjava buscar(Long id) throws Exception { 
		Userposjava retorno = new Userposjava();
		String sql = "select * from userposjava where id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery(); // RECUPERAR UM DADO : execute Query

		while (resultado.next()) { // retorna apenas 1 o nenhum registo
			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));
		}
		return retorno;
	}

	public void atualizar(Userposjava userposjava) {
		try {
			String sql = "update userposjava set nome = ? where id = " + userposjava.getId();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userposjava.getNome());
			statement.execute();  // EXECUTE: atualizar
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			e.printStackTrace();
		}

	}
	
	
	public void deletar (Long id) {
		try {			
			String sql = "delete from userposjava where id = " +id;	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute(); // EXECUTE: deletar sem dependencia de tabela filha
			connection.commit(); 			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	
	public List<BeanUserFone> listaUserFone (Long idUser){
		List<BeanUserFone> beanUserFone = new ArrayList<BeanUserFone>();
		String  sql = " select nome, numero, email from telefoneuser as fone ";
				sql += " inner join userposjava as userp ";
				sql += " on fone.usuariopessoa = userp.id ";
				sql += " where userp.id = " +idUser;				
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery(); // EXCUTE QUERY : listar mais que um dado
			while (result.next()) {
				BeanUserFone userFone = new BeanUserFone();
				userFone.setEmail(result.getString("email"));
				userFone.setNome(result.getString("nome"));
				userFone.setNumero(result.getString("Numero"));
				beanUserFone.add(userFone);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}				
		return beanUserFone;
	}
	
	
	public void deleteFonePorUser (Long idUser) { /* primeiro deleta a lista de telefones (tabala-filha) com FK
	 												depois a o usuario na tabela-pai												*/
		try {
			String sqlFone = "delete from telefoneuser where usuariopessoa = "+idUser;
			String sqlUser = "delete from userposjava where id = "+idUser;
			
			PreparedStatement deleteFoneAndUser = connection.prepareStatement(sqlFone);
			deleteFoneAndUser.executeUpdate();
			connection.commit();
			
			deleteFoneAndUser = connection.prepareStatement(sqlUser);
			deleteFoneAndUser.executeUpdate();  // EXECUTE UPDATE: deletar dados que depen
			connection.commit();
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
