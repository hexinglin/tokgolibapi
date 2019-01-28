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
class ClientNode {
    private Socket client;
    private boolean IsExitHeartbeat;

    ClientNode(Socket client) {
        this.client = client;
        this.IsExitHeartbeat = true;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public boolean isIsExitHeartbeat() {
        return IsExitHeartbeat;
    }

    public void setIsExitHeartbeat(boolean IsExitHeartbeat) {
        this.IsExitHeartbeat = IsExitHeartbeat;
    }
}
