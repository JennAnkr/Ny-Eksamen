package Database;

public interface DatabaseHandler {
    void insertSamlerkortserier(int id, String publisher, int publish, String sport, int number);

    void insertFootballCard(int id, int serie, String state, String playerName, String club, int season, int matches, int seriesScores, int cupsScores);

    void insertBaseBallCard(int id, int serie, String state, String playerName, String club, int season, int matches, int homeruns);

    void insertBasketball(int id, int serie, String state, String playerName, String club, int season, int matches, int fgPercent, int ftPercent, double scoreAverage);
}
