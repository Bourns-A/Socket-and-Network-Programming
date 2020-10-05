import java.io.*;
import java.net.*;

/**
*describe: 入口
*
*@author HNH
*/
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.setSoTimeout(3000);
        //连接本地ip地址，端口2000。
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000), 3000);
        System.out.println("Connection Requested");
        System.out.println("Client: "+socket.getLocalAddress() +"p: "+ socket.getLocalPort());
        System.out.println("Server: "+socket.getInetAddress() +"p:" + socket.getPort());

        try {
            send(socket);
        } catch (IOException e) {
            System.out.println("Error");
        }
        socket.close();
        System.out.println("Client out");
    }
    private static void send(Socket client) throws IOException {
        //键盘输入流
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));
        //将socket输出流转换为打印流
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            //键盘读取
            String str = input.readLine();
            //发送到服务器
            socketPrintStream.println(str);

            //服务器读取
            String echo = socketBufferedReader.readLine();
            if ("bye".equalsIgnoreCase(echo)) {
                break;
            } else  {
                System.out.println(echo);
            }
        }
        //资源释放
        socketBufferedReader.close();
        socketPrintStream.close();
    }

}
