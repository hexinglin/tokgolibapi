package Net.TCP;


import Net.TCP.MyServerSocket.RecevceThread;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import Net.tool.SocketTool;

/**
 *
 * @author TOKGO
 */
public abstract class ServersBaseClass implements ServerInterface{ 
    protected MyServerSocket socket = null;
    private boolean Isopenserver = false;
    private boolean IsOpenHeart = false;
    private int HeartCheckTimeI = 3000;
    // 1服务器只检查心跳包  2 服务器主动发心跳包
    private int HeartModeI = 1;
    private String HeartString ="\bHB\b\b";
    protected abstract void ClientExit(Socket client);
     /**
     *
     * @param port 开启端口号
     * @param Server 数据接收对象
     * @throws IOException
     */
    protected void CreatServer(int port ,ServersBaseClass Server) throws IOException{
        if(Isopenserver) 
            throw new IOException("Exist open");
        socket = new MyServerSocket(port,Server);
        Isopenserver = true;
        if(this.IsOpenHeart)
            new Thread(new HeartbeatThread()).start();
    }
    
    /**
     *
     * @param port 开启端口号
     * @param address 地址
     * @param Server 数据接收对象
     * @throws IOException
     */
    protected void CreatServer(int port,String address ,ServersBaseClass Server) throws IOException{
        if(Isopenserver) 
            throw new IOException("Exist open");
        socket = new MyServerSocket(port,InetAddress.getByName(address),Server);
        if(this.IsOpenHeart)
            new Thread(new HeartbeatThread()).start();
        Isopenserver = true;
    }
     /**
     *
     * @param HeartModeI 1 -服务器只检查心跳包  2- 服务器主动发心跳包
     */
    public void setHeartModeI(int HeartModeI) {
        if (HeartModeI == 1) {
            this.HeartModeI = 1;
        }
        else
            this.HeartModeI = 2;
    }
     
    public void CloseServers(){  
        try {
            this.Isopenserver = false;
            CloseAccpetThread();
            CloseAllReceiveThread();
        } catch (IOException ex) {
            if (SocketTool.IsDebug) 
                System.out.println("servers colse error at ServersBaseClass CloseServers");
        }
        
    }
     protected ServerSocket getServerSocket() {
        return socket.getServerSocket();
    }
    private void CloseAccpetThread() throws IOException{
        this.socket.setCloseServer();
    }
    
    @Override
    public void ClientDiscon(Socket client){
         ClientExit(client);
         if (IsOpenHeart) {
            for(RecevceThread clientThread :socket.getThreadlist())
                if (clientThread.getClient() == client) {
                try {
                    CloseTread(clientThread);
                } catch (IOException ex) {
                      return;}
                   
                }
            
         }
    }
    
    private void CloseAllReceiveThread() throws IOException{
       for(RecevceThread clientThread :socket.getThreadlist()){
           if (!clientThread.isIsColse()) 
               ClientExit(clientThread.getClient());
            CloseTread(clientThread);
        }
    }
    
    /**
     *
     * @param client
     * @param Data
     * @return 
     */
    @SuppressWarnings("empty-statement")
    @Override
    public boolean Send(Socket client,String Data){
        DataOutputStream dos = null;  
        try {
            dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF(Data);
            return true;
        } catch (IOException ex) {}
        return false;
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    public boolean Send(Socket client,byte[] Data,int off ,int legth){
        DataOutputStream dos = null;  
        try {
            dos = new DataOutputStream(client.getOutputStream());
            dos.write(Data, off, legth);
             return true;
        } catch (IOException ex) {}
         return false;
    }
    
    public boolean Send(Socket client,byte[] Data ,int legth){
        DataOutputStream dos = null;  
        try {
            dos = new DataOutputStream(client.getOutputStream());
            dos.write(Data, 0, legth);
            return true;
        } catch (IOException ex) {}
         return false;
    }
    
    
    @SuppressWarnings("empty-statement")
    public boolean Send(Socket client,byte Data){
        DataOutputStream dos = null;  
        try {
            dos = new DataOutputStream(client.getOutputStream());
            dos.write(Data);
             return true;
        } catch (IOException ex) {}
         return false;
    }
    
    @Override
    public void Receive(Socket client,String Data){}
    @Override
    public void Accept(Socket client){}
    
    protected void SetUTF8Mode() throws IOException{
         if(!Isopenserver)
            throw new IOException("Server don't open");
        socket.SetUTF8Mode();
    }
   
   private void CloseTread(RecevceThread clientThread) throws IOException
   {
        clientThread.setIsColse(true);
        clientThread.getClient().close();
   }
   
    @Override
   public void HeartbeatData(Socket client,byte[] data,int legth)
   {
        if (SocketTool.IsDebug) 
            System.out.println("心跳数据byte at ServersBaseClass HeartbeatData");
        if (HeartModeI != 2)
                Send(client, data,0,legth);
   }
   
    @Override
    public void HeartbeatData(Socket client,String data)
    {
        if (SocketTool.IsDebug) 
            System.out.println("心跳数据string at ServersBaseClass HeartbeatData");
        if (HeartModeI != 2)
            Send(client, data);
    }
   
   private void CheckHeartbeat() throws IOException{
       for(RecevceThread clientThread :socket.getThreadlist()){
           if(!clientThread.isIsExistHeartbeat()){
                 if (SocketTool.IsDebug) 
                       System.out.println("心跳引起的引起的接收线程结束 at ServersBaseClass CheckHeartbeat");
                ClientExit(clientThread.getClient());
                CloseTread(clientThread);
                return ;
            }
            else
                clientThread.setIsExistHeartbeat(false);
       }
   }

    public void setHeartBeatMode() throws IOException {
        if(Isopenserver)
            throw new IOException("Set hreat must before open server");
        this.IsOpenHeart = true;
    }

    public int getHeartCheckTimeI() {
        return HeartCheckTimeI;
    }

    public void setHeartCheckTimeI(int ms) throws IOException {
        if(HeartCheckTimeI<1000)
             throw new IOException("HeartCheckTimeI must more than 1000 at "+this.getClass().toString());
        this.HeartCheckTimeI = ms;
    }
   
    public void setHeartString(String HeartString) throws IOException {
        if (HeartString.isEmpty()) 
            throw new IOException("NULLString at "+this.getClass().toString());
        if (HeartString.length()>5) 
            throw new IOException("HeartString to long at "+this.getClass().toString());
        this.HeartString = HeartString;
        socket.setHreatbyte(HeartString);
    }
    
    private void ProduceHeart(){
       for(RecevceThread clientThread :socket.getThreadlist()){
           if(socket.isIsReceivebyte())
               Send(clientThread.getClient(),HeartString.getBytes(), 0, HeartString.length());
           else
               Send(clientThread.getClient(),HeartString);
           }
    }
   
   class HeartbeatThread implements Runnable{
     
    @Override  
    @SuppressWarnings("empty-statement")
    public void run() {
        int coutI = 0;
        while(Isopenserver)
        {
            try {
                Thread.sleep(HeartCheckTimeI);
                if (HeartModeI == 2)
                    ProduceHeart();
                coutI ++;
                if (coutI%3==0) {
                     CheckHeartbeat();
                     coutI = 0;
                }
               
            } catch (Exception ex) {} 
        }
    }  
    }
}
