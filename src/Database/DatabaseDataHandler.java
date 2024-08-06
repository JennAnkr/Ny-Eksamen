package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDataHandler implements DatabaseHandler {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Funn";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Test1234!"; // Sett inn passord her eller bruk miljøvariabler

    public DatabaseDataHandler() {}

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    @Override
    public void insertSamlerkortserier(int id, String publisher, int publish, String sport, int number) {

    }

    @Override
    public void insertFootballCard(int id, int serie, String state, String playerName, String club, int season, int matches, int seriesScores, int cupsScores) {

    }

    @Override
    public void insertBaseBallCard(int id, int serie, String state, String playerName, String club, int season, int matches, int homeruns) {

    }

    @Override
    public void insertBasketball(int id, int serie, String state, String playerName, String club, int season, int matches, int fgPercent, int ftPercent, double scoreAverage) {

    }
}
