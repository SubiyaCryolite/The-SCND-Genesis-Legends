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
    public static final int SERVER_LATENCY = 4; //ms (legacy; I/O uses short socket timeouts)
    public static final int TIMEOUT = 4000; //ms
    private static NetworkManager instance;
    public volatile int hostTimeLimit;
    private NetworkServer server;
    private NetworkClient client;
    private volatile boolean connectedToPartner;
    private boolean closing;

    private NetworkManager() {
        instance = this;
    }

    public static NetworkManager get() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public void asHost() {
        if (client != null) {
            closeTheClient(false);
        }
        server = new NetworkServer();
    }

    public void asClient(String ip) {
        if (server != null) {
            closeTheServer(false);
        }
        client = new NetworkClient(ip);
    }

    private void closeTheServer(boolean returnToMenu) {
        if (server != null) {
            server.close();
            server = null;
        }
        if (returnToMenu) {
            backToMainMenu();
        }
    }

    private void closeTheClient(boolean returnToMenu) {
        if (client != null) {
            client.close();
            client = null;
        }
        if (returnToMenu) {
            backToMainMenu();
        }
    }

    private void backToMainMenu() {
        setConnectedToPartner(false);
        var scndGenLegends = ScndGenLegends.get();
        scndGenLegends.setSubMode(SubMode.MAIN_MENU);
        scndGenLegends.loadMode(ModeEnum.MAIN_MENU);
    }

    public boolean isServer() {
        return server != null;
    }

    public boolean isClient() {
        return client != null;
    }

    public void send(String message) {
        if (isClient()) {
            client.sendData(message);
        }
        if (isServer()) {
            server.sendData(message);
        }
    }

    /**
     * Drain inbound protocol messages on the game/GLFW thread.
     *
     * @deprecated use {@link com.scndgen.legends.command.GameCommandBus#drainAndApply()}
     */
    @Deprecated
    public void drainInbound() {
        // no-op: network workers offer directly to GameCommandBus
    }

    public void close() {
        if (closing) {
            return;
        }
        closing = true;
        try {
            var wasOnline = isOnline();
            closeTheClient(false);
            closeTheServer(false);
            if (wasOnline) {
                backToMainMenu();
            }
        } finally {
            closing = false;
        }
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
        if (server != null) {
            server.playerFound();
        }
    }

    public String getHostName() {
        if (isServer()) {
            return server.getHostName();
        }
        return "";
    }

    public String getHostAddress() {
        if (isServer()) {
            return server.getHostAddress();
        }
        return "";
    }
}
