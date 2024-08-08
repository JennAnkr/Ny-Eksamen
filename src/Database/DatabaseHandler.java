package Database;

import com.mysql.cj.jdbc.result.ResultSetImpl;

import java.sql.*;

public class DatabaseHandler implements IDatabaseHandler {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Kort";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Test1234!"; // Sett inn passord her eller bruk miljøvariabler

    public DatabaseHandler() {}

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    @Override
    public void insertSamlerkortserier(int id, String publisher, int publish, String sport, int number) {
        String sql = "INSERT INTO SamlerkortSerie(id, Utgiver, Utgitt, Sport,Antall) VALUES(?,?,?,?,?)";
        try (PreparedStatement preparedStatement = this.getPreparedStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, publisher);
            preparedStatement.setInt(3, publish);
            preparedStatement.setString(4, sport);
            preparedStatement.setInt(5, number);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void insertFootballCard(int id, int serie, String state, String playerName, String club, int season, int matches, int seriesScores, int cupsScores) {
        String sql = "INSERT INTO Fotballkort(id,Serie,Tilstand,Spillernavn,Klubb,Sesonger,Kamper,Seriescoringer,Cupscoringer) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = this.getPreparedStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, serie);
            preparedStatement.setString(3, state);
            preparedStatement.setString(4, playerName);
            preparedStatement.setString(5, club);
            preparedStatement.setInt(6, season);
            preparedStatement.setInt(7, matches);
            preparedStatement.setInt(8, seriesScores);
            preparedStatement.setInt(9, cupsScores);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertBaseBallCard(int id, int serie, String state, String playerName, String club, int season, int matches, int homeruns) {
        String sql = "INSERT INTO Baseballkort(id,Serie,Tilstand,Spillernavn,Klubb,Sesonger,Kamper,Homeruns) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = this.getPreparedStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, serie);
            preparedStatement.setString(3, state);
            preparedStatement.setString(4, playerName);
            preparedStatement.setString(5, club);
            preparedStatement.setInt(6, season);
            preparedStatement.setInt(7, matches);
            preparedStatement.setInt(8, homeruns);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertBasketball(int id, int serie, String state, String playerName, String club, int season, int matches, int fgPercent, int ftPercent, double scoreAverage) {
        String sql = "INSERT INTO Basketballkort(id,Serie,Tilstand,Spillernavn,Klubb,Sesonger,Kamper,FGPercent,FTPercent,Poengsnitt) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = this.getPreparedStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, serie);
            preparedStatement.setString(3, state);
            preparedStatement.setString(4, playerName);
            preparedStatement.setString(5, club);
            preparedStatement.setInt(6, season);
            preparedStatement.setInt(7, matches);
            preparedStatement.setInt(8, fgPercent);
            preparedStatement.setInt(9, ftPercent);
            preparedStatement.setDouble(10, scoreAverage);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getNumberOfRegisteredCards() {
        String sql = "SELECT id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper FROM Baseballkort \n" +
                "UNION ALL\n" +
                "SELECT id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper FROM Basketballkort \n" +
                "UNION ALL\n" +
                "SELECT id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper FROM Fotballkort;";
        try (PreparedStatement statement = this.getPreparedStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            return ((ResultSetImpl) resultSet).getUpdateCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getAllCardsBySport(String sport) {
        StringBuilder results = new StringBuilder();
        String sql = getSqlQueryBySport(sport);
        try (PreparedStatement statement = this.getPreparedStatement(sql)){
            statement.setString(1, sport);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.append(this.buildResultFromResultSet(resultSet).append("\n"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return results.toString();
    }

    @Override
    public String getAllCardsByState(String state) {
        StringBuilder results = new StringBuilder();
        String sql = "SELECT id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper FROM Baseballkort WHERE UPPER(Baseballkort.Tilstand) =UPPER(?)\n" +
                "UNION ALL\n" +
                "SELECT id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper FROM Basketballkort WHERE UPPER(Basketballkort.Tilstand) =UPPER(?)\n" +
                "UNION ALL\n" +
                "SELECT id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper FROM Fotballkort WHERE UPPER(Fotballkort.Tilstand) =UPPER(?);";
        try (PreparedStatement statement = this.getPreparedStatement(sql)){
            statement.setString(1, state);
            statement.setString(2, state);
            statement.setString(3, state);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.append(this.buildResultFromResultSet(resultSet).append("\n"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results.toString();
    }

    @Override
    public String getAllSamlerkortSerierBySport(String sport) {
        StringBuilder results = new StringBuilder();
        String sql = "SELECT * FROM SamlerkortSerie WHERE UPPER(Sport) = UPPER(?)";
        try (PreparedStatement statement = this.getPreparedStatement(sql)){
            statement.setString(1, sport);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.append(this.buildResultFromResultSet(resultSet).append("\n"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results.toString();
    }

    @Override
    public void executeQuery(StringBuilder query) {
        try (PreparedStatement preparedStatement = this.getPreparedStatement(query.toString())) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement getPreparedStatement(String sql) throws SQLException {
        Connection conn = this.connect();
        return conn.prepareStatement(sql);
    }

    private StringBuilder buildResultFromResultSet(ResultSet resultSet) throws SQLException {
        StringBuilder results = new StringBuilder();
        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i < columnCount; i++){
            results.append(resultSet.getString(i)).append(" ");
        }
        return results;
    }

    private String getSqlQueryBySport(String sport) {
        if (sport.equalsIgnoreCase("Fotball")) {
            return "SELECT * FROM Fotballkort where serie in (select id from SamlerkortSerie where UPPER(Sport) = UPPER(?));";
        } else if (sport.equalsIgnoreCase("Basketball")) {
            return "SELECT * FROM Basketballkort where serie in (select id from SamlerkortSerie where UPPER(Sport) = UPPER(?));";
        } else if (sport.equalsIgnoreCase("Baseball")) {
            return "SELECT * FROM Baseballkort where serie in (select id from SamlerkortSerie where UPPER(Sport) = UPPER(?));";
        }
        return null;
    }


}
