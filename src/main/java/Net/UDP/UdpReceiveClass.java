/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Net.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import Net.tool.SocketTool;

/**
 *
 * @author TOKGO
 */

public abstract class UdpReceiveClass {
    private final int MAXBUF = SocketTool.MAXBUF;
    private int PORT;
    private boolean IsOpenPort = false;
    private DatagramSocket UDPSocket;
    private DatagramPacket dataPacket;
    private final byte[] ReceiveByte ;
    
    protected abstract void Receive(DatagramPacket dataPack,byte[] Data ,int Length);
    
    public UdpReceiveClass() {
        this.ReceiveByte = new byte[MAXBUF];
    }
    
    public void CreatReceiveMonitor(int PORT) throws SocketException{
        this.PORT = PORT;
        UDPSocket = new DatagramSocket(PORT);
        dataPacket = new DatagramPacket(ReceiveByte, ReceiveByte.length);
        this.IsOpenPort = true;
        new ReciceveThread().start();
    }
    
      public void CreatReceiveMonitor(String IP,int PORT) throws Exception{
        this.PORT = PORT;
        UDPSocket = new DatagramSocket(PORT,InetAddress.getByName(IP));
        dataPacket = new DatagramPacket(ReceiveByte, ReceiveByte.length);
        this.IsOpenPort = true;
        new ReciceveThread().start();
    }
    
    public boolean Send(byte[] data,int off,int length,int Port, InetAddress IPAddress) {
        try {
            this.UDPSocket.send(new DatagramPacket(data,off,length ,IPAddress, Port));
            return true;
        } catch (IOException ex) {}
         return false;
    }
    
    public boolean Send(byte data,int Port, InetAddress IPAddress) {
        try {
             byte[] da = new byte[1];
             da[0] = data;
            this.UDPSocket.send(new DatagramPacket(da,1 ,IPAddress, Port));
            return true;
        } catch (IOException ex) {}
         return false;
    }
    
    public boolean Send(byte[] data,int length,int Port, InetAddress IPAddress){
        try {
            this.UDPSocket.send(new DatagramPacket(data,length ,IPAddress, Port));
            return true;
        } catch (IOException ex) {}
         return false;
    }
    
    public boolean Send(DatagramPacket Packet, byte[] data,int length){
        try {
            this.UDPSocket.send(new DatagramPacket(data,length ,Packet.getAddress(), Packet.getPort()));
            return true;
        } catch (IOException ex) {}
         return false;
    }
    
    public boolean Send(DatagramPacket Packet, byte[] data,int off,int length) {
        try {
            this.UDPSocket.send(new DatagramPacket(data,off,length ,Packet.getAddress(), Packet.getPort()));
            return true;
        } catch (IOException ex) {}
        return false;
    }
    
    public boolean Send(DatagramPacket Packet, byte data) {
        try {
            byte[] da = new byte[1];
            da[0] = data;
            this.UDPSocket.send(new DatagramPacket(da,1,Packet.getAddress(), Packet.getPort()));
            return true;
        } catch (IOException ex) {}
        return false;
    }
    
    public void Close(){
        this.IsOpenPort = false;
        this.UDPSocket.close();
    }
    public int getPORT() {
        return PORT;
    }

    public DatagramSocket getUDPSocket() {
        return UDPSocket;
    }
    
    class ReciceveThread extends Thread{
        private int ReciceveLength = 0;
        @Override
        public void run() {
            while(IsOpenPort){
                try {
                    UDPSocket.receive(dataPacket);
                    ReciceveLength = dataPacket.getLength();
                    if(ReciceveLength>0 && dataPacket.getPort()!= -1){
                        Receive(dataPacket,ReceiveByte,ReciceveLength);
                        ReciceveLength = 0; 
                    }
                } catch (IOException ex) {}
            }
        }
    
    }
    
}
