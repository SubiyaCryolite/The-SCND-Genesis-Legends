package com.scndgen.legends;

import io.github.subiyacryolite.jds.*;
import io.github.subiyacryolite.jds.annotations.JdsEntityAnnotation;
import io.github.subiyacryolite.jds.enums.JdsImplementation;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import org.sqlite.SQLiteConfig;

import java.io.File;
import java.util.List;

/**
 * Created by ifunga on 22/04/2017.
 */
@JdsEntityAnnotation(entityName = "Game State", entityId = 1)
public class GameState extends JdsEntity {
    private static GameState instance;
    private static JdsDataBase jdsDatabase;
    private final SimpleListProperty<GameSave> gameSaves = new SimpleListProperty<>();
    private int currentGameSave;
    private boolean newAccount;

    private GameState() {
        instance = this;
        map(GameSave.class, gameSaves);
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
        jdsDatabase.setConnectionProperties(databaseIfNotExists, sqLiteConfig.toProperties());
    }

    private static void bindClasses() {
        JdsEntityClasses.map(GameSave.class);
        JdsEntityClasses.map(GameState.class);
    }

    private static String getEmbeddedDatabaseAndCreateIfNotExist() {
        File databaseFileLocation = new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen"+ File.separator + "legends");
        String fileLocation = databaseFileLocation.getAbsolutePath() + File.separator + "config.db";
        if (!new File(fileLocation).exists()) {
            try {
                databaseFileLocation.mkdirs();
                new File(fileLocation).createNewFile();
                System.err.println("Creating database");
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
        return fileLocation;
    }

    public void saveConfigFile() {
        JdsSave.save(jdsDatabase, 1, this);
    }

    public ObservableList<GameSave> getGameSaves() {
        return gameSaves.get();
    }

    public void setGameSaves(ObservableList<GameSave> gameSaves) {
        this.gameSaves.clear();
        if (gameSaves == null) return;
        this.gameSaves.addAll(gameSaves);
    }

    public GameSave getLogin() {
        if (gameSaves.size() == 0) {
            currentGameSave = 0;
            gameSaves.add(new GameSave());
            newAccount = true;
        }
        return gameSaves.get(currentGameSave);
    }

    public void setCurrentAccount(GameSave gameSave) {
        for (int i = 0; i < gameSaves.size(); i++) {
            if (gameSaves.get(i) == gameSave) {
                currentGameSave = i;
                return;
            }
        }
        //account didn't exist, so add it here
        currentGameSave = gameSaves.size();
        gameSaves.add(gameSave);
    }

    public boolean isNewAccount() {
        return newAccount;
    }

    public void isNewAccount(boolean value) {
        newAccount = value;
    }
}
