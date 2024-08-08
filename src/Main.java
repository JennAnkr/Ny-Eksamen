import FileHandlers.FileHandler;
import FileHandlers.IFileHandler;

public class Main {
    public static void main(String[] args) {
        IFileHandler fileHandler = new FileHandler("src/samlerkort.txt");
        fileHandler.executeSqlFileToSetupDb("src/samlerkort.sql");
        fileHandler.processFileData();
    }
}