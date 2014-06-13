package com.http;

import java.security.*;
import java.io.IOException;
import java.net.*;
import java.net.UnknownHostException;
import javax.net.*;
import javax.net.ssl.*;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

public class MySecureProtocolSocketFactory implements
		ProtocolSocketFactory {
	
	private SSLContext sslcontext = null;

	public MySecureProtocolSocketFactory() {
		// TODO Auto-generated constructor stub
	}

	private SSLContext createSSLContext() {  
        SSLContext sslcontext = null;  
        try {  
            sslcontext = SSLContext.getInstance("SSL");  
            sslcontext.init(null, new TrustManager[]{new TrustAllManager()}, new java.security.SecureRandom());  
              
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (KeyManagementException e) {  
            e.printStackTrace();  
        }  
        return sslcontext;  
    }  
	
	private SSLContext getSSLContext() {  
        if (this.sslcontext == null) {  
            this.sslcontext = createSSLContext();  
              
        }  
        return this.sslcontext;  
    } 
	
	public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {  
        return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);  
    }  
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {  
        return getSSLContext().getSocketFactory().createSocket(host, port);  
    }  
     
     
    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException, UnknownHostException {  
        return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);  
    }  
    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {  
        if (params == null) {  
            throw new IllegalArgumentException("Parameters may not be null");  
        }  
        int timeout = params.getConnectionTimeout();  
        SocketFactory socketfactory = getSSLContext().getSocketFactory();  
        if (timeout == 0) {  
            return socketfactory.createSocket(host, port, localAddress, localPort);  
        } else {  
            Socket socket = socketfactory.createSocket();  
            SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);  
            SocketAddress remoteaddr = new InetSocketAddress(host, port);  
            socket.bind(localaddr);  
            socket.connect(remoteaddr, timeout);  
            return socket;  
        }  
    }  

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
