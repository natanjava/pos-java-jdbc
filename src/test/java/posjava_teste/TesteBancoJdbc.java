package posjava_teste;

import org.junit.Test;

import conexaojdbc.SingleConnection;

public class TesteBancoJdbc {
	
	@Test
	public void initBanco () {
		SingleConnection.getConnection();
	 	
	}

}
