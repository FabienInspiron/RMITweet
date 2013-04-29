package ServeurTwitt;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

import javax.rmi.ssl.SslRMIServerSocketFactory;

public class RMISSLServerSocketFactory implements RMIServerSocketFactory,
		Serializable {
	public ServerSocket createServerSocket(int port) throws IOException {
		SslRMIServerSocketFactory factory = new SslRMIServerSocketFactory();
		ServerSocket socket = factory.createServerSocket(port);
		return socket;
	}
}