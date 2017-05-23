package com.scndgen.legends.state;

import io.github.subiyacryolite.enginev1.Overlay;
import io.github.subiyacryolite.jds.*;
import io.github.subiyacryolite.jds.annotations.JdsEntityAnnotation;
import io.github.subiyacryolite.jds.enums.JdsImplementation;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.SQLiteConfig;

import java.io.File;
import java.util.List;

/**
 * Created by ifunga on 22/04/2017.
 */
@JdsEntityAnnotation(entityName = "Game State", entityId = 1)
public class GameState extends JdsEntity {
    public static final int MAX_TIME = 1000;
    private static GameState instance;
    private static JdsDataBase jdsDatabase;
    public static final int DIFFICULTY_BASE = 8000;
    public static final int DIFFICULTY_SCALE = 1333;
    private final SimpleListProperty<LoginState> loginStates = new SimpleListProperty<>(FXCollections.observableArrayList());
    private int currentGameSave;
    private boolean newAccount;
    private String lanHostIp;

    public GameState() {
        instance = this;
        map(LoginState.class, loginStates);
    }

    public static synchronized GameState getInstance() {
        if (instance == null) {
            initGameState(getEmbeddedDatabaseAndCreateIfNotExist());
        }
        return instance;
    }

    private static void initGameState(String databaseIfNotExists) {
        createLocalDatabase(databaseIfNotExists);
        bindClasses();
        loadStateInstance();
    }

    private static void loadStateInstance() {
        List<GameState> gameStates = JdsLoad.load(jdsDatabase, GameState.class);
        GameState gameState = (gameStates.size() == 0) ? new GameState() : gameStates.get(0);
        System.out.printf("Created game state %s\n", gameState.toString());
    }

    private static void createLocalDatabase(String databaseIfNotExists) {
        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        sqLiteConfig.enforceForeignKeys(true);
        jdsDatabase = JdsDataBase.getImplementation(JdsImplementation.SQLITE);
        jdsDatabase.setConnectionProperties("jdbc:sqlite:" +databaseIfNotExists, sqLiteConfig.toProperties());
        jdsDatabase.init();
    }

    private static void bindClasses() {
        JdsEntityClasses.map(LoginState.class);
        JdsEntityClasses.map(GameState.class);
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

    public void saveConfigFile() {
        JdsSave.save(jdsDatabase, 1, this);
        Overlay.getInstance().primaryNotice("Saved File");
    }

    public ObservableList<LoginState> getLoginStates() {
        return loginStates.get();
    }

    public void setLoginStates(ObservableList<LoginState> loginStates) {
        this.loginStates.clear();
        if (loginStates == null) return;
        this.loginStates.addAll(loginStates);
    }

    public LoginState getLogin() {
        if (loginStates.size() == 0) {
            currentGameSave = 0;
            getLoginStates().add(new LoginState());
            newAccount = true;
        }
        return getLoginStates().get(currentGameSave);
    }

    public void setCurrentAccount(LoginState loginState) {
        for (int i = 0; i < loginStates.size(); i++) {
            if (loginStates.get(i) == loginState) {
                currentGameSave = i;
                return;
            }
        }
        //account didn't exist, so add it here
        currentGameSave = loginStates.size();
        getLoginStates().add(loginState);
    }

    public boolean isNewAccount() {
        return newAccount;
    }

    public void isNewAccount(boolean value) {
        newAccount = value;
    }

    public String getLanHostIp() {
        return lanHostIp;
    }

    public void setLanHostIp(String value) {
        lanHostIp = value;
    }
}
