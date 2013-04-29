package ServeurTwitt;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import javax.rmi.ssl.SslRMIClientSocketFactory;

public class RMISSLClientSocketFactory implements RMIClientSocketFactory,
		Serializable {

	public Socket createSocket(String host, int port) throws IOException {
		SslRMIClientSocketFactory factory = new SslRMIClientSocketFactory();
		Socket socket = factory.createSocket(host, port);
		return socket;
	}
}