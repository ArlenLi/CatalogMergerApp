import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CatalogMerger {
    /**
     * merger is used to merge one catalogs into the other catalogs and eliminate duplicate products if exist
     * @param mainCatalogs the main catalogs
     * @param toBeMergedCatalogs catalogs need to be merged into the main catalogs
     * @param mainBarcodes  barcodes of products in the main catalogs
     * @param toBeMergedBarcodes barcodes of products in to be merged catalogs
     * @return a list of merged catalogs
     */
    public List<Catalog> merge(List<Catalog> mainCatalogs, List<Catalog> toBeMergedCatalogs,
                               List<Barcode> mainBarcodes, List<Barcode> toBeMergedBarcodes){
        Set<String> mainBarcodeIdentifiers = mainBarcodes.stream().
                map(Barcode::getBarcodeIdentifier).
                collect(Collectors.toSet());

        Set<String> duplicateCatalogSKUs = toBeMergedBarcodes.stream().
                filter(b -> mainBarcodeIdentifiers.contains(b.getBarcodeIdentifier())).
                map(Barcode::getCatalogSKU).
                collect(Collectors.toSet());

        List<Catalog> nonDuplicateCatalogs = toBeMergedCatalogs.stream().
                filter(c -> !duplicateCatalogSKUs.contains(c.getSKU())).
                collect(Collectors.toList());

        List<Catalog> mergedCatalog = Stream.
                concat(mainCatalogs.stream(), nonDuplicateCatalogs.stream()).
                map(Catalog::new).
                collect(Collectors.toList());

        return mergedCatalog;
    }
}
