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

/**
 *
 * @author TOKGO
 */
public class UdpSendClass {
    private int Port;
    private InetAddress IPAddress;
    private DatagramSocket dataSocket = null;
    private DatagramPacket dataPacket = null;
    private boolean IsSetTargetIPPort=false;

    public UdpSendClass(int LocalPort) throws SocketException { 
        dataSocket = new DatagramSocket(LocalPort);
    }
    
    public void SetTargetIPPort(int Port, InetAddress IPAddress){
         this.Port = Port;
        this.IPAddress = IPAddress;
        this.IsSetTargetIPPort=true;
    }
    
    public void Close(){
        dataSocket.close();
    }
    
    public void SendData(byte[] data,int off,int length) throws IOException{
        if(!this.IsSetTargetIPPort)
            throw new IOException("don't set target ip and port");
        this.dataPacket = new DatagramPacket(data,off,length 
                ,this.IPAddress, this.Port);
        this.dataSocket.send(this.dataPacket);
    }
    public void SendData(byte[] data,int off,int length,int Port, InetAddress IPAddress) throws IOException{
        this.dataPacket = new DatagramPacket(data,off,length ,IPAddress, Port);
        this.dataSocket.send(this.dataPacket);
    }
    
    
}
