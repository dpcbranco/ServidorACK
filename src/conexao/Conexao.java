package conexao;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Conexao implements Runnable {

	ServerSocket servidor;
	Socket cliente;

	public Conexao() throws UnknownHostException, IOException {
		servidor = new ServerSocket(1234);
		cliente = servidor.accept();
		System.out.println("Cliente " + cliente.getInetAddress() + " conectado");
	}

	@Override
	public void run() {

		try {
			InputStream entrada_arq = cliente.getInputStream();
			DataInputStream entrada_info = new DataInputStream(cliente.getInputStream());
			DataOutputStream resposta = new DataOutputStream(cliente.getOutputStream());
			FileOutputStream f = new FileOutputStream ("C:\\Users\\Nerds\\Desktop\\Paelias Liadon - Background.docx");
			BufferedOutputStream escrita_arq = new BufferedOutputStream(f);

			int tam = entrada_info.readInt();
			
			while (!cliente.isClosed() && cliente.isConnected()) {
				
				byte [] arq = new byte[tam];

				for (int i = 0; i < tam; i++) {
					byte [] aux = new byte[1];
					int eof = entrada_arq.read(aux);
					if (eof == 0) {
						resposta.writeUTF("NAK");
						i--;
					}

					else if (eof == -1) {
						break;
					}

					else {
						resposta.writeUTF("ACK");
						arq[i] = aux[0];
						System.out.println(aux[0]);
					}

				}

				cliente.close();

				escrita_arq.write(arq, 0, tam);
				escrita_arq.flush();

				escrita_arq.close();
			}
			
			System.out.println("Arquivo recebido");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
