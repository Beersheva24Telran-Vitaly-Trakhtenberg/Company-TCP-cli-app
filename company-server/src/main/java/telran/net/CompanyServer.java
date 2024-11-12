package telran.net;

import netscape.javascript.JSObject;

import java.net.*;
import java.io.*;

public class CompanyServer
{
    private Socket socket = null;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void seanceStarts(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void seanceStops() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

/*
    public void companyServer() throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while(true) {
            this.socket = serverSocket.accept();
            this.runSession();
        }
    }
*/

    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    public void sendMessage(String message) {
        out.println(message);
    }

/*
    private void runSession() throws IOException
    {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintStream out = new PrintStream(this.socket.getOutputStream())) {
            String line = "";
            while((line = in.readLine()) != null) {
                out.printf("Echo Server on %s, port %d sends back %s\n", socket.getLocalAddress().getHostAddress(),
                        socket.getLocalPort(), line);
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Client closed connection abnormally");
        }
    }

    public void sendJSONToClient(JSObject json_object) throws IOException
    {
        try {
            PrintStream out = new PrintStream(this.socket.getOutputStream());
            out.println(json_object);
        } catch (IOException e) {
            System.out.printf("Anything wrong: %s", e.getMessage());
        }
    }

    public void getJSONFromClient()
    {

    }
*/
}
