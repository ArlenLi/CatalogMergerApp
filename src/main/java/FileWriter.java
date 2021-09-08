import java.io.IOException;
import java.util.List;

public interface FileWriter {
    /**
     * Write catalogs to a file at provided path
     * @param catalogs a list of Catalog
     * @param path the path of file
     * @throws IOException throws IOException if error happens when writing
     */
    void writeCatalogsToCSV(List<Catalog> catalogs, String path) throws IOException;
}
