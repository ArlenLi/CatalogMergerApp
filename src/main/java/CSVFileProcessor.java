import org.omg.CORBA.DynAnyPackage.Invalid;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVFileProcessor implements FileProcessor{
    public List<Barcode> processBarcodeFile(String path) throws IOException{
        List<Barcode> barcodes;

        try(Stream<String> stream = Files.lines(Paths.get(path)).skip(1)){
            barcodes = stream.map(s -> covertStringToBarcode(s)).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            System.out.println("File at " + path + " could not be found.");
            throw e;
        } catch (IOException e){
            System.out.println("An I/O error happens. please refer to error detail: " + e.getMessage());
            throw e;
        }

        return barcodes;
    }

    public List<Catalog> processCatalogFile(String path) throws IOException{
        List<Catalog> catalogs;

        try(Stream<String> stream = Files.lines(Paths.get(path)).skip(1)){
            String companyName = extractCompanyName(Paths.get(path).getFileName().toString());
            catalogs = stream.map(s -> convertStringToCatalog(s, companyName)).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            System.out.println("File at " + path + " could not be found.");
            throw e;
        } catch (IOException e){
            System.out.println("An I/O error happens. please refer to error detail: " + e.getMessage());
            throw e;
        }

        return catalogs;
    }

    private Barcode covertStringToBarcode(String barcodeString){
        String[] barcodeElements = barcodeString.split(",");

        if(barcodeElements.length < 3){
            throw new InvalidFileException("Each line in the barcode file should contain SupplierID, SKU, Barcode " +
                    "in order. Please check your csv file and pay attention to the last line, which could be empty.");
        }

        if(isEmptyOrWhiteSpaces(barcodeElements[1]) || isEmptyOrWhiteSpaces(barcodeElements[2])){
            throw new InvalidFileException("SKU or Barcode in the barcode file is empty or only contains whitespaces." +
                    " Please revise the barcode file");
        }

        return new Barcode(barcodeElements[2], barcodeElements[1]);
    }

    String extractCompanyName(String filename) throws InvalidFileNameException {
        String companyName;
        Pattern p = Pattern.compile("catalog(.*)(.csv)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(filename);

        if(m.find() && !isEmptyOrWhiteSpaces(m.group(1))){
            companyName = m.group(1);
        }else{
            throw new InvalidFileNameException("Catalog file name: " + filename + "is invalid. It must conform to the " +
                    "form \"catalog\" suffixed with companyName like catalogA");
        }

        return companyName;
    }

    private Catalog convertStringToCatalog(String catalogString, String companyName) throws InvalidFileException{
        String[] catalogElements = catalogString.split(",");

        if(catalogElements.length < 2){
            throw new InvalidFileException("Each line in the catalog file should contain SKU, Description in order. " +
                    "Please check your csv file and pay attention to the last line, which could be empty.");
        }

        if(isEmptyOrWhiteSpaces(catalogElements[0])){
            throw new InvalidFileException("SKU in the catalog file is empty or only contains whitespaces. " +
                    "Please revise the catalog file");
        }

        return new Catalog(catalogElements[0], catalogElements[1], companyName);
    }

    private boolean isEmptyOrWhiteSpaces(String s){
        return s.trim().isEmpty();
    }
}
