

package Net.TCP;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author TOKGO
 */
public abstract class ClientBaseClass implements ClientInterface{
   protected  MyClientSocket ClientSocket;
   private String HeartString ="\bHB\b\b";
   private boolean IsConnect = false;
   private boolean IsOpenHeart = false;
   private int HeartCheckTimeI = 1000;
   
   protected abstract void ServersBreakoff();
   
   protected void Connect(String IP,int port,ClientBaseClass clientObject) throws IOException{
       if(IsConnect) 
            throw new IOException("Exist open");
       ClientSocket = new MyClientSocket(IP,port,clientObject);
       IsConnect = true;
       if(this.IsOpenHeart)
            new Thread(new HeartbeatThread()).start();
   }
    /**
     *
     * @param Data
     */
   @Override
   public boolean Send(String Data){
        DataOutputStream dos = null;  
        try {
            dos = new DataOutputStream(ClientSocket.getClientsockt().getOutputStream());
            dos.writeUTF(Data);
            return true;
        } catch (Exception ex) {}
        return false;
    }
   
   @Override
    public boolean Send(byte[] Data,int off ,int legth){
        DataOutputStream dos = null;  
        try {
            dos = new DataOutputStream(ClientSocket.getClientsockt().getOutputStream());
            dos.write(Data, off, legth);
             return true;
        } catch (IOException ex) {}
        return false;
    }
    
    public boolean Send(byte[] Data ,int legth){
        DataOutputStream dos = null;  
        try {
            dos = new DataOutputStream(ClientSocket.getClientsockt().getOutputStream());
            dos.write(Data, 0, legth);
            return true;
        } catch (IOException ex) {}
        return false;
    }
   
    @SuppressWarnings("empty-statement")
    public boolean Send(byte Data){
        DataOutputStream dos = null;  
        try {
            dos = new DataOutputStream(ClientSocket.getClientsockt().getOutputStream());
            dos.write(Data);
            return true;
        } catch (IOException ex) {}
        return false;
    }
    
     @Override
    public void Receive(Socket client,String Data){}
    
    protected void SetUTF8Mode() throws IOException{
         if(!IsConnect)
            throw new IOException("Server don't open");
        ClientSocket.setUTF8Mode();
    }
    
   @Override
    public void LinkBreak(){
       try {
           if (IsConnect) {
               ServersBreakoff();
               ClientSocket.setCloseCleint();
           }
           IsConnect = false;
           System.gc();
       } catch (IOException ex) {}
       
    
    }
    
    public void Close(){
       try {
           ClientSocket.setCloseCleint();
       } catch (IOException ex) {     }
        IsConnect = false;
           System.gc();
    }
    
    public void setHeartString(String HeartString) throws IOException {
        if (HeartString.isEmpty()) 
            throw new IOException("NULLString at "+this.getClass().toString());
        if (HeartString.length()>5) 
            throw new IOException("HeartString to long at "+this.getClass().toString());
        this.HeartString = HeartString;
        ClientSocket.setHreatbyte(HeartString);
    }
   
   private void ProduceHeartbeat(){
       if(ClientSocket.isReceivebyte()){
           this.Send(HeartString.getBytes(), 0, HeartString.length());
       }
       else
        this.Send(HeartString);
   }
   private int JugdeExistHeartbeat(int count) throws IOException{
       if(ClientSocket.isIsExistHeartbeat()){
            ClientSocket.setIsExistHeartbeat(false);
            return 0;
        }
       count++;
       return count;
   }

    public boolean isConnect() {
        return IsConnect;
    }
    
    public void setHeartBeatMode() throws IOException {
        if(IsConnect)
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
    
    
   class HeartbeatThread implements Runnable{
    private int count =0;
    
    @Override  
    @SuppressWarnings("empty-statement")
    public void run() {
        while(IsConnect)
        {
            try {
                Thread.sleep(HeartCheckTimeI);
                ProduceHeartbeat();
               count = JugdeExistHeartbeat(count);
                if(count>3){ 
                   LinkBreak();
                   break;
                }
            } catch (InterruptedException | IOException ex) {
                System.out.println(ex);
            } 
        }
    }  
    }
}
