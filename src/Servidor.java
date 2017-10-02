import java.io.IOException;

import conexao.Conexao;

public class Servidor {

	public static void main(String[] args) {
		try {
			Conexao c = new Conexao();
			System.out.println("Iniciando servidor");
			Thread server = new Thread (c);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
