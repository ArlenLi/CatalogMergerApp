import java.io.IOException;
import java.util.List;

public interface FileProcessor {
    /**
     * Process file to return a list of Barcode
     * @param path the path of file
     * @return a list of Barcode in the file
     * @throws IOException throws IOException if error happens when reading file
     */
    List<Barcode> processBarcodeFile(String path) throws IOException;

    /**
     * Process file to return a list of Catalog
     * @param path the path of file
     * @return a list of Catalog in the file
     * @throws IOException throws IOException if error happens when reading file
     */
    List<Catalog> processCatalogFile(String path) throws IOException;
}
