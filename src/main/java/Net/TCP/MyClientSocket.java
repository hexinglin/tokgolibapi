
package Net.TCP;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import Net.tool.SocketTool;
import Net.tool.bytetool;

/**
 *
 * @author TOKGO
 */
 class MyClientSocket {
    private static final int MAXBUF = SocketTool.MAXBUF;
    private String HeartString ="\bHB\b\b";
    private Socket clientsockt =null;
    private ClientInterface clientObject = null;
    private boolean IsExistHeartbeat = false;
    private boolean IsCloseCleint = false;
     private boolean IsReceivebyte = true;
    public MyClientSocket(String IP,int port,ClientInterface clientObject) throws IOException {
        this.clientObject = clientObject;
        clientsockt = new Socket(IP, port);
        new Thread(new RecevceThread(clientsockt)).start();
    }

    public Socket getClientsockt() {
        return clientsockt;
    }
   
    public void setCloseCleint() throws IOException {
        this.IsCloseCleint = true;
        this.clientsockt.close();
    }

     public boolean isIsExistHeartbeat() {
        return IsExistHeartbeat;
    }

    public void setIsExistHeartbeat(boolean IsExistHeartbeat) {
        this.IsExistHeartbeat = IsExistHeartbeat;
    }
    
    public void setUTF8Mode() {
        this.IsReceivebyte = false;
    }

    public boolean isReceivebyte() {
        return IsReceivebyte;
    }
    public void setHreatbyte(String Hreatbyte) {
        this.HeartString = Hreatbyte;
   }

    public boolean isIsCloseCleint() {
        return IsCloseCleint;
    }
    
    
/*  
 *处理读操作的线程   
 */  
class RecevceThread implements Runnable{  
    private final Socket server;
    private DataInputStream dis = null;
    private String reciverData;
    private final byte[] reciverbyte;
    private final byte[] reciveHreat;
    private int byteLenght;
    public RecevceThread(Socket server) {  
        this.server = server;
        reciverbyte = new byte[MAXBUF];
        this.reciveHreat = new byte[5];
    } 
    
    @Override  
    @SuppressWarnings("empty-statement")
    public void run() {
        while(!IsCloseCleint)
        {
            try {
                dis = new DataInputStream(server.getInputStream());  
                 if(IsReceivebyte)
                   ReceiveByte();
                else
                   ReceiveString(); 
                IsExistHeartbeat = true;
             } catch (SocketException | EOFException ex){
                 LinkBreak();
                 break;
            }catch (IOException ex) {
                if (SocketTool.IsDebug) 
                    System.out.println("未知异常"+ex.toString()+" at RecevceThread run IOException");
             }
        }
    }
    
     private void LinkBreak() {
       if (!IsCloseCleint){
            if (SocketTool.IsDebug) 
                System.out.println("用户断开引起的接收线程结束 at RecevceThread run SocketException|EOFException");
            clientObject.LinkBreak();
       }
        IsCloseCleint = true;     
    }
    
    private void ReceiveByte() throws IOException{
        byteLenght = dis.read(reciverbyte);
        if (byteLenght == -1) 
            throw new SocketException("function read return -1");
        System.arraycopy(this.reciverbyte, 0, this.reciveHreat, 0, HeartString.length());
        if(!bytetool.Equal(HeartString.getBytes(), reciveHreat, HeartString.length()))
            clientObject.Receive(server, reciverbyte, byteLenght);
    }
    private void ReceiveString() throws IOException{
        reciverData = dis.readUTF();
        if(!HeartString.endsWith(reciverData)) 
            clientObject.Receive(server, reciverData);
    }

       
    
    } 
}
