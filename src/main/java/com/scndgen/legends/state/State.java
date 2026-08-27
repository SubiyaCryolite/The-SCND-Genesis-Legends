/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.scndgen.legends.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Game save root. Persisted as JSON under {@code ~/.config/scndgen/legends/state.json}.
 */
public class State {
    private static final ObjectMapper MAPPER = JsonMapper.builder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();
    private static State instance;

    public static final int DIFFICULTY_BASE = 8000;
    public static final int DIFFICULTY_SCALE = 1333;

    private List<Login> logins = new ArrayList<>();
    private String lastLoginGuid = "";

    public State() {
    }

    public static synchronized State get() {
        if (instance == null) {
            instance = loadOrCreate();
        }
        return instance;
    }

    private static State loadOrCreate() {
        File file = saveFile();
        try {
            if (file.isFile() && file.length() > 0) {
                State loaded = MAPPER.readValue(file, State.class);
                if (loaded.logins == null) {
                    loaded.logins = new ArrayList<>();
                }
                if (loaded.lastLoginGuid == null) {
                    loaded.lastLoginGuid = "";
                }
                System.out.printf("Loaded game state from %s (%d login(s))%n", file, loaded.logins.size());
                return loaded;
            }
        } catch (JacksonException ex) {
            System.err.println("Failed to load save file, creating a new one: " + ex.getMessage());
        }
        State created = new State();
        try {
            created.saveConfigFile();
            System.out.printf("Created game state at %s%n", file);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        return created;
    }

    private static File saveFile() {
        File dir = new File(System.getProperty("user.home")
                + File.separator + ".config"
                + File.separator + "scndgen"
                + File.separator + "legends");
        if (!dir.exists() && !dir.mkdirs()) {
            System.err.println("Unable to create save directory: " + dir);
        }
        return new File(dir, "state.json");
    }

    public void saveConfigFile() throws Exception {
        File file = saveFile();
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            Files.createDirectories(parent.toPath());
        }
        MAPPER.writeValue(file, this);
        System.out.println("Saved File");
    }

    public List<Login> getLogins() {
        return logins;
    }

    public void setLogins(List<Login> logins) {
        this.logins = logins != null ? logins : new ArrayList<>();
    }

    public String getLastLoginGuid() {
        return lastLoginGuid;
    }

    public void setLastLoginGuid(String lastLoginGuid) {
        this.lastLoginGuid = lastLoginGuid != null ? lastLoginGuid : "";
    }

    public void addLoginState(Login login) {
        this.logins.add(login);
    }

    @JsonIgnore
    public Login getLogin() {
        if (logins.isEmpty()) {
            createLogin("Temp");
        }
        Optional<Login> optional = logins.stream()
                .filter(element -> element.getId().equals(lastLoginGuid))
                .findAny();
        return optional.orElse(logins.get(0));
    }

    public void setCurrentLogin(Login login) {
        if (login != null) {
            lastLoginGuid = login.getId();
        }
    }

    public void createLogin(String accountName) {
        Login login = new Login(accountName);
        addLoginState(login);
        setCurrentLogin(login);
    }

    @Override
    public String toString() {
        return "State{" +
                "logins=" + logins +
                ", lastLoginGuid=" + lastLoginGuid +
                '}';
    }
}
