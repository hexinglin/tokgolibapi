package Net.TCP;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import Net.tool.SocketTool;
import Net.tool.bytetool;

/**
 *
 * @author TOKGO
 */
class MyServerSocket {
    private static final int MAXBUF = SocketTool.MAXBUF;
    private List<RecevceThread> threadlist;
    private ServerSocket serverSocket = null; 
    private ServerInterface ServerObject =null;
    private boolean IsCloseServer = false;
    private boolean IsReceivebyte = true;
    private String HeartString ="\bHB\b\b";
    
    public MyServerSocket(int port ,ServerInterface ServerObject) throws IOException {
        this.ServerObject = ServerObject;
        serverSocket = new ServerSocket(port);
        threadlist = new ArrayList();
        new Thread(new ServerAcceptThread(serverSocket)).start();
    }
    public MyServerSocket(int port, InetAddress address ,ServerInterface Server) throws IOException {
        this.ServerObject = Server;
        serverSocket = new ServerSocket(port,0,address);
        threadlist = new ArrayList();
        new Thread(new ServerAcceptThread(serverSocket)).start();
    }
    
    public void setCloseServer() throws IOException {
        this.IsCloseServer  = true;
        this.serverSocket.close();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    
    public void setHreatbyte(String Hreatbyte) {
        this.HeartString = Hreatbyte;
    }
    
    public void SetUTF8Mode() {
        this.IsReceivebyte = false;
    }

    public boolean isIsReceivebyte() {
        return IsReceivebyte;
    }

    public List<RecevceThread> getThreadlist() {
        return threadlist;
    }
    

/*  
 *处理读操作的线程   
 */  
class ServerAcceptThread implements Runnable{  
    private final ServerSocket server;
    public ServerAcceptThread(ServerSocket server) {  
        this.server = server;  
    }  
    @Override  
    @SuppressWarnings("empty-statement")
    public void run() {  
        while(!IsCloseServer)
        {
            try {
                Socket client = server.accept();
                ServerObject.Accept(client);
                RecevceThread thread =new RecevceThread(client);
                thread.start();
                threadlist.add(thread);
             } catch (IOException ex) {
                  
             }
        }
        if (SocketTool.IsDebug) 
            System.out.println("ServerAcceptThread线程结束 at ServerAcceptThread");
    }  
    }
/*  
 *处理读操作的线程   
 */  
    class RecevceThread extends Thread{  
        private final Socket client;
        private DataInputStream dis = null;
        private String reciverData;
        private final byte[] reciverbyte;
        private final byte[] reciverHreat;
        private int byteLenght;
        private boolean IsExistHeartbeat = false;
        private boolean IsColse = false;
        public RecevceThread(Socket client) {  
            this.client = client;
            reciverbyte = new byte[MAXBUF];
            this.reciverHreat = new byte[5];
        }
        @Override  
        @SuppressWarnings("empty-statement")
        public void run() {
            while(!IsColse)
            {
                try {
                    dis = new DataInputStream(client.getInputStream());
                    if(IsReceivebyte)
                       ReceiveByte();
                    else
                       ReceiveString(); 
                    IsExistHeartbeat = true;
                 }
                catch (SocketException | EOFException ex){
                    ClientDiscon();
                   break;
                }
                catch (IOException ex) {
                     if (SocketTool.IsDebug) 
                            System.out.println("未知异常"+ex.toString()+" at RecevceThread run IOException|EOFException");
                }
            }
            if (SocketTool.IsDebug) 
            System.out.println("RecevceThread线程结束 at RecevceThread");
            threadlist.remove(this);
        }
        private void ClientDiscon() {
            if (!IsColse){
                if (SocketTool.IsDebug) 
                    System.out.println("用户断开引起的接收线程结束 at RecevceThread run SocketException");
                ServerObject.ClientDiscon(client); 
            }
            IsColse = true;
        }
        
        private void ReceiveByte() throws IOException{
            byteLenght = dis.read(reciverbyte);
            if (byteLenght == -1) 
                throw new SocketException("function read return -1");
            System.arraycopy(this.reciverbyte, 0, this.reciverHreat, 0, HeartString.length());
            if(bytetool.Equal(HeartString.getBytes(), reciverHreat, HeartString.length()))
                ServerObject.HeartbeatData(client, reciverbyte, byteLenght);
            else
                ServerObject.Receive(client, reciverbyte, byteLenght);
        }
        private void ReceiveString() throws IOException{
            reciverData = dis.readUTF();
            if(HeartString.equals(reciverData))
                ServerObject.HeartbeatData(client, reciverData);
            else
                ServerObject.Receive(client, reciverData);
        }

    
        public void setIsExistHeartbeat(boolean IsExistHeartbeat) {
            this.IsExistHeartbeat = IsExistHeartbeat;
        }

        public void setIsColse(boolean IsColse) {
            this.IsColse = IsColse;
        }

        public boolean isIsColse() {
            return IsColse;
        }

        public boolean isIsExistHeartbeat() {
            return IsExistHeartbeat;
        }

        public Socket getClient() {
            return client;
        }

        
        
    }   
    
    
}
