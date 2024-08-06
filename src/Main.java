import FileHandlers.FileDataHandler;
import FileHandlers.FileHandler;

public class Main {
    public static void main(String[] args) {
        FileHandler fileHandler = new FileDataHandler("src/samlerkort.txt");
        fileHandler.processFileData();
    }
}