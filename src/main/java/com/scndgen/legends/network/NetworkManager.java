/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.scndgen.legends.network;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.SubMode;

public class NetworkManager {

    public static final int PORT = 3074;
    public static final int SERVER_LATENCY = 4; //ms
    public static final int TIMEOUT = 4000; //ms
    private static NetworkManager instance;
    public int hostTimeLimit;
    private NetworkServer server;
    private NetworkClient client;
    private boolean connectedToPartner;

    private NetworkManager() {
        instance = this;
    }

    public static NetworkManager get() {
        if (instance == null)
            instance = new NetworkManager();
        return instance;
    }

    public void asHost() {
        if (client != null)
            closeTheClient();
        server = new NetworkServer();
    }

    public void asClient(String ip) {
        if (server != null)
            closeTheServer();
        client = new NetworkClient(ip);
    }

    /**
     * Closes hosting NetworkServer
     */
    private void closeTheServer() {
        server.close();
        server.shutdownKillServer();
        server = null;
        backToMainMenu();
    }

    /**
     * Closes client
     */
    private void closeTheClient() {
        client.close();
        client.shutdownKillClient();
        client = null;
        backToMainMenu();
    }

    private void backToMainMenu() {
        setConnectedToPartner(false);
        ScndGenLegends.get().setSubMode(SubMode.MAIN_MENU);
        ScndGenLegends.get().loadMode(ModeEnum.MAIN_MENU);
    }

    public boolean isServer() {
        return server != null;
    }

    public boolean isClient() {
        return client != null;
    }

    public void send(String message) {
        if (isClient())
            client.sendData(message);
        if (isServer())
            server.sendData(message);
    }

    public void close() {
        if (isClient())
            closeTheClient();
        if (isServer())
            closeTheServer();
    }

    public boolean isOnline() {
        return isClient() || isServer();
    }

    public boolean isOffline() {
        return !isOnline();
    }

    public boolean isOnlineAndConnectedToPartner() {
        return isOnline() && isConnectedToPartner();
    }

    public void setConnectedToPartner(boolean value) {
        this.connectedToPartner = value;
    }

    public boolean isConnectedToPartner() {
        return this.connectedToPartner;
    }

    public void promptServer() {
        if (server != null)
            server.playerFound();
    }

    public String getHostName() {
        if (isServer())
            return server.getHostName();
        return "";
    }

    public String getHostAddress() {
        if (isServer())
            return server.getHostAddress();
        return "";
    }
}