import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

public class CSVFileWriter implements FileWriter {
    public void writeCatalogsToCSV(List<Catalog> catalogs, String path) throws IOException{
        File file = new File(path);

        try{
            Files.deleteIfExists(file.toPath());

            try(PrintWriter pw = new PrintWriter(file, "UTF-8")){
                pw.println("SKU,Description,Source");
                catalogs.stream().map(Catalog::toString).forEach(pw::println);
            }
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + file.toPath() + ". Please check if file is opened.");
            throw e;
        }
    }
}
