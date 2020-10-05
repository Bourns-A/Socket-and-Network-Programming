import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
*describe: 监听
*
*@author HNH
*/

public class Service {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2000);
        System.out.println("Server Ready");
        System.out.println("Client: "+server.getInetAddress() +"p: "+ server.getLocalPort());

        while (true) {
            //客户端的连接,得到客户端
            Socket client = server.accept();
            ClientHandler clientHandler = new ClientHandler(client);
            clientHandler.start();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            super.run();
            System.out.println("New Client" + socket.getInetAddress()+"p: "+socket.getPort());
            try {
                //打印流用于数据输出，服务器回送数据
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    //从客户端拿到数据
                    String str = socketInput.readLine();
                    if ("bye".equalsIgnoreCase(str)) {
                        socketOutput.println("bye");
                        break;
                    } else {
                        System.out.println(str);
                    }
                }
                socketInput.close();
                socketOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
