/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (http://www.scndgen.sf.net).

 The SCND Genesis: Legends  © 2011 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.network;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.enums.SubMode;

public class NetworkManager {

    public static final int PORT = 5555;
    public static final int serverLatency = 10; //ms
    public static final int serverTimeout = 2000; //ms
    private static NetworkManager instance;
    public int hostTime;
    private NetworkServer server;
    private NetworkClient client;
    private String remoteIpAddress = "";

    private NetworkManager() {
        instance = this;
    }

    public static NetworkManager getInstance() {
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
        this.remoteIpAddress = ip;
        client = new NetworkClient(remoteIpAddress);
        client.sendData(NetworkConstants.CONNECT_TO_HOST);
    }

    /**
     * Closes hosting NetworkServer
     */
    private void closeTheServer() {
        server.close();
        server = null;
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST)
            ScndGenLegends.getInstance().setSubMode(SubMode.MAIN_MENU);
        System.out.println("Closed server");
    }

    /**
     * Closes client
     */
    private void closeTheClient() {
        client.close();
        client = null;
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT)
            ScndGenLegends.getInstance().setSubMode(SubMode.MAIN_MENU);
        System.out.println("Closed client");
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

    public void closePipes() {
        if (isClient())
            closeTheClient();
        if (isServer())
            closeTheServer();
    }

    public boolean isOnline() {
        return isClient() && isServer();
    }
}