import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CSVFileProcessorTest {
    private final CSVFileProcessor csvFileProcessor = new CSVFileProcessor();

    @Nested
    class processBarcodeFile{
        String path = "barcodes.csv";
        @BeforeEach
        public void beforeEach(){
            File file = new File(path);

            try{
                Files.deleteIfExists(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        public void whenFileNotFound_ReturnsIOException(){
            assertThatIOException().isThrownBy(() -> csvFileProcessor.processBarcodeFile(path));
        }

        @Test
        public void whenFileIsValid_ReturnsBarcodes(){
            File file = new File(path);

            try(PrintWriter pw = new PrintWriter(file)){
                pw.println("SupplierID,SKU,Barcode");
                pw.println("00001,647-vyk-317,z2783613083817");
                pw.println("00001,999-vyk-317,n7405223693844");
                pw.println("00004,999-eol-949,x9858014383660");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            List<Barcode> barcodes = new ArrayList<>();
            try{
                barcodes = csvFileProcessor.processBarcodeFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            assertThat(barcodes).extracting("barcodeIdentifier", "catalogSKU").
                    containsOnly(tuple("z2783613083817", "647-vyk-317"),
                            tuple("n7405223693844", "999-vyk-317"),
                            tuple("x9858014383660","999-eol-949"));
        }
    }

    @Nested
    class processCatalogFile{
        String path = "catalogABC.csv";

        @BeforeEach
        public void beforeEach(){
            File file = new File(path);

            try{
                Files.deleteIfExists(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        public void whenFileNotFound_ReturnsIOException(){
            assertThatIOException().isThrownBy(() -> csvFileProcessor.processCatalogFile(path));
        }

        @Test
        public void whenFileIsValid_ReturnsCatalogs(){
            File file = new File(path);

            try(PrintWriter pw = new PrintWriter(file)){
                pw.println("SKU,Description");
                pw.println("647-vyk-317,Walkers Special Old Whiskey");
                pw.println("999-eol-949,Cheese - Grana Padano");
                pw.println("999-epd-782,Carbonated Water - Lemon Lime");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            List<Catalog> catalogs = new ArrayList<>();
            try {
                catalogs = csvFileProcessor.processCatalogFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            assertThat(catalogs).extracting("sku", "description", "originalCompany").
                    containsOnly(tuple("647-vyk-317", "Walkers Special Old Whiskey", "ABC"),
                            tuple("999-eol-949", "Cheese - Grana Padano", "ABC"),
                            tuple("999-epd-782", "Carbonated Water - Lemon Lime", "ABC"));
        }
    }

    @Nested
    class ExtractCompanyName{
        @Test
        public void whenFilenameIsValid_ReturnsCompanyName(){
            String filename = "catalogA.csv";

            String companyName = csvFileProcessor.extractCompanyName(filename);

            assertThat(companyName).isEqualTo("A");
        }

        @Test
        public void whenFilenameIsValid_AndContainsUppercase_ReturnsCompanyName(){
            String filename = "CatalogA.csv";

            String companyName = csvFileProcessor.extractCompanyName(filename);

            assertThat(companyName).isEqualTo("A");
        }

        @Test
        public void whenFilenameIsInvalid_ThrowsInvalidFileNameException(){
            String filename = "CataA.csv";

            assertThatThrownBy(() -> csvFileProcessor.extractCompanyName(filename)).
                    isInstanceOf(InvalidFileNameException.class);
        }
    }
}
