/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Net.TCP;

import java.net.Socket;

/**
 *
 * @author TOKGO
 */
interface ClientInterface {
     void Receive(Socket client, String Data);
     void Receive(Socket client, byte[] data, int lenght);
     boolean Send(String Data);
     boolean Send(byte[] Data, int off, int legth);
     void LinkBreak();
}
