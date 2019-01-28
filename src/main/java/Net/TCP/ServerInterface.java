
package Net.TCP;

import java.net.Socket;

/**
 *
 * @author TOKGO
 */
interface ServerInterface {
    public void Receive(Socket client, String Data);
    public void Receive(Socket client, byte[] data, int lenght);
    public void Accept(Socket client);
    public boolean Send(Socket client, String Data);
    public boolean Send(Socket client, byte[] Data, int off, int legth);
    public void ClientDiscon(Socket client);
    public void HeartbeatData(Socket client, byte[] data, int legth);
    public void HeartbeatData(Socket client, String data);
}
