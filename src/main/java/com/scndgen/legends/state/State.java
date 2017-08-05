/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([http://www.scndgen.com]))).

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
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.state;

import io.github.subiyacryolite.enginev1.Overlay;
import io.github.subiyacryolite.jds.*;
import io.github.subiyacryolite.jds.annotations.JdsEntityAnnotation;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.SQLiteConfig;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.scndgen.legends.state.LoginFields.LAST_LOGIN_ACCOUNT;

/**
 * Created by ifunga on 22/04/2017.
 */
@JdsEntityAnnotation(entityName = "Game State", entityId = 1)
public class State extends JdsEntity {
    private static State instance;
    private static JdsDb jdsDb;
    public static final int DIFFICULTY_BASE = 8000;
    public static final int DIFFICULTY_SCALE = 1333;
    private final SimpleListProperty<Login> logins = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final SimpleStringProperty lastLoginGuid = new SimpleStringProperty("");

    public State() {
        instance = this;
        map(Login.class, logins);
        map(LAST_LOGIN_ACCOUNT, lastLoginGuid);
    }

    public static synchronized State get() {
        if (instance == null) {
            try {
                initGameState(getEmbeddedDatabaseAndCreateIfNotExist());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private static void initGameState(String databaseIfNotExists) throws Exception {
        createLocalDatabase(databaseIfNotExists);
        bindClasses();
        loadStateInstance();
    }

    private static void loadStateInstance() throws Exception {
        List<State> states = JdsLoad.load(jdsDb, State.class);
        State state;
        if (states.size() == 0) {
            state = new State();
            System.out.printf("Created game state %s\n", state.toString());
            JdsSave.save(jdsDb, 0, state);
        } else {
            state = states.get(0);
            System.out.printf("Loaded game state %s\n", state.toString());
        }

    }

    private static void createLocalDatabase(String databaseIfNotExists) {
        jdsDb = new JdsDbSqlite() {
            @Override
            public Connection getConnection() throws ClassNotFoundException, SQLException {
                Class.forName("org.sqlite.JDBC");
                SQLiteConfig sqLiteConfig = new SQLiteConfig();
                sqLiteConfig.enforceForeignKeys(true);
                return DriverManager.getConnection("jdbc:sqlite:" + databaseIfNotExists, sqLiteConfig.toProperties());
            }
        };
        jdsDb.init();
    }

    private static void bindClasses() {
        jdsDb.map(Login.class);
        jdsDb.map(State.class);
    }

    private static String getEmbeddedDatabaseAndCreateIfNotExist() {
        File databaseFileLocation = new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "legends");
        String fileLocation = databaseFileLocation.getAbsolutePath() + File.separator + "config.db";
        try {
            if (!new File(fileLocation).exists()) {
                databaseFileLocation.mkdirs();
                new File(fileLocation).createNewFile();
                System.err.println("Creating database");
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return fileLocation;
    }

    public void saveConfigFile() throws Exception {
        JdsSave.save(jdsDb, 1, this);
        Overlay.get().primaryNotice("Saved File");
    }

    public ObservableList<Login> getLogins() {
        return logins.get();
    }

    public void addLoginState(Login login) {
        this.logins.add(login);
    }

    public Login getLogin() {
        if (logins.size() == 0) {
            createLogin("Temp");
        }
        Optional<Login> optional = getLogins().stream().filter(elment -> elment.getEntityGuid().equals(lastLoginGuid.get())).findAny();
        return optional.isPresent() ? optional.get() : null;
    }

    public void setCurrentLogin(Login login) {
        lastLoginGuid.set(login.getEntityGuid());
    }

    public void createLogin(String accountName) {
        Login login = new Login(accountName);
        addLoginState(login);
        setCurrentLogin(login);
        setCurrentLogin(login);
    }

    @Override
    public String toString() {
        return "State{" +
                "logins=" + logins.get() +
                ", lastLoginGuid=" + lastLoginGuid.get() +
                '}';
    }
}
