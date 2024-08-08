package FileHandlers;

public abstract class IFileHandler {
    public abstract void executeSqlFileToSetupDb(String fileName);
    public abstract void processFileData();
}
