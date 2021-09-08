import java.io.File;
import java.util.List;

public class CatalogMergerApp {
    public static void main(String[] arguments){
        try{
            FileProcessor fileProcessor = new CSVFileProcessor();
            List<Catalog> mainCatalogs = fileProcessor.
                    processCatalogFile(composeRelativeFilePath("catalogA.csv"));
            List<Catalog> toBeMergedCatalogs = fileProcessor.
                    processCatalogFile(composeRelativeFilePath("catalogB.csv"));
            List<Barcode> mainBarcodes = fileProcessor.
                    processBarcodeFile(composeRelativeFilePath("barcodesA.csv"));
            List<Barcode> toBeMergedBarcodes = fileProcessor.
                    processBarcodeFile(composeRelativeFilePath("barcodesB.csv"));

            CatalogMerger merger = new CatalogMerger();
            List<Catalog> mergedCatalog = merger.merge(mainCatalogs, toBeMergedCatalogs, mainBarcodes, toBeMergedBarcodes);

            FileWriter fileWriter = new CSVFileWriter();
            String outputPath = ".." + File.separator + "output" + File.separator + "result_output.csv";
            fileWriter.writeCatalogsToCSV(mergedCatalog, outputPath);
        }  catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private static String composeRelativeFilePath(String filename) {
        String relativeFilePath = ".." + File.separator + "input" + File.separator + filename;

        return relativeFilePath;
    }
}
