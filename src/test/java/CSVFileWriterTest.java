import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

public class CSVFileWriterTest {
    private String path = "output.csv";

    @AfterEach
    public void afterEach(){
        File file = new File(path);

        try{
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeCatalogsToCSV_WhenPathIsValid_WriteToFileSuccessfully(){
        List<Catalog> catalogs = new ArrayList<>();
        catalogs.add(new Catalog("647-vyk-317", "Walkers Special Old Whiskey", "A"));
        catalogs.add(new Catalog("165-rcy-650", "Tea - Decaf 1 Cup", "A"));
        catalogs.add(new Catalog("650-epd-782", "Carbonated Water - Lemon Lime", "B"));

        CSVFileWriter csvFileWriter = new CSVFileWriter();

        try{
            csvFileWriter.writeCatalogsToCSV(catalogs, path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(path);
        assertThat(file.exists()).isTrue();

        List<String> lines = new ArrayList<>();

        try{
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(lines.size()).isEqualTo(4);
        assertThat(lines).containsOnly("SKU,Description,Source",
                "647-vyk-317,Walkers Special Old Whiskey,A",
                "165-rcy-650,Tea - Decaf 1 Cup,A",
                "650-epd-782,Carbonated Water - Lemon Lime,B");
    }
}
