package FileHandlers;

import Database.DatabaseHandler;
import Database.IDatabaseHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileHandler extends IFileHandler {
    private String fileName;
    private final IDatabaseHandler databaseDataHandler = new DatabaseHandler();
    public FileHandler(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void executeSqlFileToSetupDb(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("Sql File not found!");
                return;
            }
            System.out.println("Sql File found, starting to read...");
            Scanner scanner = new Scanner(file);
            // String Builder to build the query line by line.
            StringBuilder query = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.trim().startsWith("-- ") || (line.trim().startsWith("/*") || line.trim().endsWith("*/"))) {
                    continue;
                }

                // Append the line into the query string and add a space after that
                query.append(line).append(" ");
                if(line.trim().endsWith(";")) {
                    // Execute the Query
                    databaseDataHandler.executeQuery(query);
                    // Empty the Query string to add new query from the file
                    query = new StringBuilder();
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void processFileData(){
        try {
            File file = new File(this.fileName);
            if (!file.exists()) {
                System.out.println("File not found!");
                return;
            }

            System.out.println("File found, starting to read...");
            Scanner scanner = new Scanner(file);
            String section = "";

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                System.out.println("Reading line: " + line);

                if (line.equals("Samlerkortserier:")) {
                    section = "Samlerkortserier";
                    scanner.nextLine(); // Skip the count line
                } else if (line.equals("Kort:")) {
                    section = "Kort";
                    scanner.nextLine(); // Skip the count line
                }  else if (line.equals("-------")) {
                    // Skip separator lines
                } else {
                    switch (section) {
                        case "Samlerkortserier":
                            System.out.println("Processing Samlerkortserier section...");
                            processSamlerkortserier(line, scanner);
                            break;
                        case "Kort":
                            System.out.println("Processing Kort section...");
                            processCard(line, scanner);
                            break;
                    }
                }
            }

            System.out.println("Finished reading file.");
            scanner.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void processCard(String firstLine, Scanner scanner) {
        int id = Integer.parseInt(firstLine);
        int serie = Integer.parseInt(scanner.nextLine().trim());
        String state = scanner.nextLine().trim();
        String playerName = scanner.nextLine().trim();
        String club = scanner.nextLine().trim();
        int season = Integer.parseInt(scanner.nextLine().trim());
        int matches = Integer.parseInt(scanner.nextLine().trim());
        String sportType = scanner.nextLine().trim();
        switch (sportType){
            case "Fotball":
                processFotball(id,serie,state,playerName, club, season,matches, sportType,scanner);
                break;
            case "Baseball":
                processBaseball(id,serie,state,playerName, club, season,matches, sportType,scanner);
                break;
            case "Basketball":
                processBasketball(id,serie,state,playerName, club, season,matches, sportType,scanner);
                break;
        }
    }

    private void processSamlerkortserier(String firstLine, Scanner scanner) {
        int id = Integer.parseInt(firstLine);
        String publisher = scanner.nextLine().trim();
        int publish = Integer.parseInt(scanner.nextLine().trim());
        String sport = scanner.nextLine().trim();
        int number = Integer.parseInt(scanner.nextLine().trim());
        System.out.println("Parsed Person: " + id + ", " + publisher + ", " + publish + ", " + sport + ", " + number);
        databaseDataHandler.insertSamlerkortserier(id, publisher, publish, sport, number);
    }

    private void processBasketball(int id, int serie, String state, String playerName, String club, int season, int matches, String sportType, Scanner scanner) {
        int fgPercent = Integer.parseInt(scanner.nextLine().trim());
        int ftPercent = Integer.parseInt(scanner.nextLine().trim());
        double scoreAverage = Double.parseDouble(scanner.nextLine().trim());
        System.out.println("Parsed Basketball: " + id + ", " + serie + ", " + state + ", " + playerName + ", " + club + ", " + season + ", " + matches + ", " + fgPercent + ", " + ftPercent + ", " + scoreAverage);
        databaseDataHandler.insertBasketball(id,serie,state,playerName,club,season,matches,fgPercent,ftPercent,scoreAverage);
    }

    private void processBaseball(int id, int serie, String state, String playerName, String club, int season, int matches, String sportType, Scanner scanner) {
        int homeruns = Integer.parseInt(scanner.nextLine().trim());
        System.out.println("Parsed Baseball: " + id + ", " + serie + ", " + state + ", " + playerName + ", " + club + ", " + season + ", " + matches + ", " + homeruns);
        databaseDataHandler.insertBaseBallCard(id,serie,state,playerName, club, season,matches,homeruns);;

    }

    private void processFotball(int id, int serie, String state, String playerName, String club, int season, int matches, String sportType, Scanner scanner) {
        int seriesScores = Integer.parseInt(scanner.nextLine().trim());
        int cupsScores = Integer.parseInt(scanner.nextLine().trim());
        System.out.println("Parsed football: " + id + ", " + serie + ", " + state + ", " + playerName + ", " + club + ", " + season + ", " + matches + ", " + seriesScores + ", " + cupsScores);
        databaseDataHandler.insertFootballCard(id, serie, state, playerName, club, season,  matches, seriesScores, cupsScores);
    }

}
