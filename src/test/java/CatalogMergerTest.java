import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class CatalogMergerTest {
    private CatalogMerger catalogMerger = new CatalogMerger();

    @Test
    public void merge_WhenSKUsAreDiff_AndNoSameBarcodes_ReturnsBothCatalogs(){
        List<Catalog> mainCatalogs = new ArrayList<>();
        mainCatalogs.add(new Catalog("647-vyk-317", "Walkers Special Old Whiskey", "A"));
        List<Barcode> mainBarcodes = new ArrayList<>();
        mainBarcodes.add(new Barcode("z2783613083817", "647-vyk-317"));
        mainBarcodes.add(new Barcode("z2783613083880", "647-vyk-317"));
        mainBarcodes.add(new Barcode("z2783613083991", "647-vyk-317"));

        List<Catalog> toBeMergedCatalogs = new ArrayList<>();
        toBeMergedCatalogs.add(new Catalog("999-oad-768", "Bread - Raisin", "B"));
        List<Barcode> toBeMergedBarcodes = new ArrayList<>();
        toBeMergedBarcodes.add(new Barcode("w3744746803743", "999-oad-768"));
        toBeMergedBarcodes.add(new Barcode("w3744746803790", "999-oad-768"));
        toBeMergedBarcodes.add(new Barcode("w3744746803766", "999-oad-768"));

        List<Catalog> mergedCatalogs = catalogMerger.merge(mainCatalogs, toBeMergedCatalogs,
                mainBarcodes, toBeMergedBarcodes);

        assertThat(mergedCatalogs).extracting("sku", "description", "originalCompany")
                .containsOnly(tuple("647-vyk-317", "Walkers Special Old Whiskey", "A"),
                        tuple("999-oad-768", "Bread - Raisin", "B"));
    }

    @Test
    public void merge_WhenSKUsAreDiff_AndContainsSameBarcodes_ReturnsOneCatalog(){
        List<Catalog> mainCatalogs = new ArrayList<>();
        mainCatalogs.add(new Catalog("647-vyk-317", "Walkers Special Old Whiskey", "A"));
        List<Barcode> mainBarcodes = new ArrayList<>();
        mainBarcodes.add(new Barcode("z2783613083817", "647-vyk-317"));
        mainBarcodes.add(new Barcode("z2783613083880", "647-vyk-317"));
        mainBarcodes.add(new Barcode("z2783613083991", "647-vyk-317"));

        List<Catalog> toBeMergedCatalogs = new ArrayList<>();
        toBeMergedCatalogs.add(new Catalog("999-oad-768", "Bread - Raisin", "B"));
        List<Barcode> toBeMergedBarcodes = new ArrayList<>();
        toBeMergedBarcodes.add(new Barcode("w3744746803743", "999-oad-768"));
        toBeMergedBarcodes.add(new Barcode("w3744746803790", "999-oad-768"));
        toBeMergedBarcodes.add(new Barcode("z2783613083817", "999-oad-768"));

        List<Catalog> mergedCatalogs = catalogMerger.merge(mainCatalogs, toBeMergedCatalogs,
                mainBarcodes, toBeMergedBarcodes);

        assertThat(mergedCatalogs).extracting("sku", "description", "originalCompany")
                .containsOnly(tuple("647-vyk-317", "Walkers Special Old Whiskey", "A"));
    }

    @Test
    public void merge_WhenSKUsAreSame_AndNoSameBarcodes_ReturnsBothCatalogs(){
        List<Catalog> mainCatalogs = new ArrayList<>();
        mainCatalogs.add(new Catalog("647-vyk-317", "Walkers Special Old Whiskey", "A"));
        List<Barcode> mainBarcodes = new ArrayList<>();
        mainBarcodes.add(new Barcode("z2783613083817", "647-vyk-317"));
        mainBarcodes.add(new Barcode("z2783613083880", "647-vyk-317"));
        mainBarcodes.add(new Barcode("z2783613083991", "647-vyk-317"));

        List<Catalog> toBeMergedCatalogs = new ArrayList<>();
        toBeMergedCatalogs.add(new Catalog("647-vyk-317", "Bread - Raisin", "B"));
        List<Barcode> toBeMergedBarcodes = new ArrayList<>();
        toBeMergedBarcodes.add(new Barcode("w3744746803743", "647-vyk-317"));
        toBeMergedBarcodes.add(new Barcode("w3744746803790", "647-vyk-317"));
        toBeMergedBarcodes.add(new Barcode("w3744746803766", "647-vyk-317"));

        List<Catalog> mergedCatalogs = catalogMerger.merge(mainCatalogs, toBeMergedCatalogs,
                mainBarcodes, toBeMergedBarcodes);

        assertThat(mergedCatalogs).extracting("sku", "description", "originalCompany")
                .containsOnly(tuple("647-vyk-317", "Walkers Special Old Whiskey", "A"),
                        tuple("647-vyk-317", "Bread - Raisin", "B"));
    }

    @Test
    public void merge_WhenSKUsAreSame_AndContainsSameBarcodes_ReturnsOneCatalog(){
        List<Catalog> mainCatalogs = new ArrayList<>();
        mainCatalogs.add(new Catalog("647-vyk-317", "Walkers Special Old Whiskey", "A"));
        List<Barcode> mainBarcodes = new ArrayList<>();
        mainBarcodes.add(new Barcode("z2783613083817", "647-vyk-317"));
        mainBarcodes.add(new Barcode("z2783613083880", "647-vyk-317"));
        mainBarcodes.add(new Barcode("z2783613083991", "647-vyk-317"));

        List<Catalog> toBeMergedCatalogs = new ArrayList<>();
        toBeMergedCatalogs.add(new Catalog("647-vyk-317", "Bread - Raisin", "B"));
        List<Barcode> toBeMergedBarcodes = new ArrayList<>();
        toBeMergedBarcodes.add(new Barcode("w3744746803743", "647-vyk-317"));
        toBeMergedBarcodes.add(new Barcode("w3744746803790", "647-vyk-317"));
        toBeMergedBarcodes.add(new Barcode("z2783613083991", "647-vyk-317"));

        List<Catalog> mergedCatalogs = catalogMerger.merge(mainCatalogs, toBeMergedCatalogs,
                mainBarcodes, toBeMergedBarcodes);

        assertThat(mergedCatalogs).extracting("sku", "description", "originalCompany")
                .containsOnly(tuple("647-vyk-317", "Walkers Special Old Whiskey", "A"));
    }

}
