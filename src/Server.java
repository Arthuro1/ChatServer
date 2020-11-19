import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    public static void main(String[] argv) throws IOException {
        Socket clientSocket = null;
         try {
             ServerSocket serverSocket = new ServerSocket(3000);

             while(true){
                 clientSocket = serverSocket.accept();
                 BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( clientSocket.getOutputStream() ) );
                 BufferedReader br = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );

                 Thread t = new Thread(new Service(clientSocket, bw, br));
                 t.start();
             }

         }catch (Exception e){
             clientSocket.close();
             System.out.println(e);
         }

  } // ende: main
}

class Service implements Runnable {

    Socket clientSocket ;
    BufferedWriter bw;
    BufferedReader br;

    public Service(Socket clientSocket, BufferedWriter bw, BufferedReader br) {
        this.clientSocket = clientSocket;
        this.br = br;
        this.bw = bw;
    }

    public void run() {
      try {

      String servermessage;
      String clientMessage;
      bw.write("Hi, here is your Server! What is your name?");
      bw.newLine();
      bw.flush();

      clientMessage = br.readLine();
      servermessage = "Hi "+clientMessage+", You are now connected ";
      bw.write(servermessage);
      bw.newLine();
      bw.flush();

      bw.close();
      br.close();
      clientSocket.close();
    }
    catch (UnknownHostException uhe) {
      System.out.println(uhe);
    }
    catch (IOException ioe) {
      System.out.println(ioe);
    }
   }

}