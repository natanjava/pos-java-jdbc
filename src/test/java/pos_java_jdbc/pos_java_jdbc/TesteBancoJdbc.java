package pos_java_jdbc.pos_java_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.Test;

import conexaojdbc.SingleConnection;
import dao.UserPosDao;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class TesteBancoJdbc {
	

	@Test
	public void initBanco () throws SQLException {
		
		try {
			UserPosDao userPosDao = new UserPosDao(); // objeto DAO ja inicia a configuração com BD
			Userposjava userposjava = new Userposjava();
			/*userposjava.setNome("Pedro");
			userposjava.setEmail("email");*/
			userPosDao.salvar(userposjava);
		} catch (Exception e) {
			
			e.printStackTrace();			
		}
	}
	
	@Test
	public void initBuscar () {
		UserPosDao dao = new UserPosDao();
		try {
			Userposjava userposjava = dao.buscar(1L);
			System.out.println(userposjava);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Test
	public void initAtualizar() {
		
		try {
			UserPosDao dao = new UserPosDao();
			Userposjava objetoBanco = dao.buscar(3L);
			objetoBanco.setNome("Novo Nome");
			dao.atualizar(objetoBanco);
			System.out.println("Atualizado com sucesso.");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void initDeletar() {
		try {
			UserPosDao dao = new UserPosDao();
			dao.deletar(3L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeInsertTelefone() {
		 Telefone telefone = new Telefone();
		 telefone.setNumero("3434 9089");
		 telefone.setTipo("fixo");
		 telefone.setUsuario(9L);
		 
		 UserPosDao dao = new  UserPosDao();
		 dao.salvarTelefone(telefone);
		 System.out.println("Telefone cadastrado.");		 
	}
	
	@Test
	public void testCarregaFoneUser() {
		UserPosDao dao = new UserPosDao();
		List <BeanUserFone> beanUserFones = dao.listaUserFone(8L);
 		for (BeanUserFone beanUserFone : beanUserFones) {
			System.out.println(beanUserFone.toString());
			System.out.println("--------------------------");
		}
	}
	
	@Test
	public void testeDeleteUserFone() {
		UserPosDao dao = new UserPosDao();
		dao.deleteFonePorUser(8L);
		System.out.println("Deletou com sucesso.");
		
	}
	
	
	
	
	

}
