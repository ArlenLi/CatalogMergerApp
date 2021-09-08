public class Barcode {
    private String barcodeIdentifier;

    private String catalogSKU;

    public Barcode(String barcodeIdentifier, String catalogSKU) {
        this.barcodeIdentifier = barcodeIdentifier;
        this.catalogSKU = catalogSKU;
    }

    public String getBarcodeIdentifier() {
        return barcodeIdentifier;
    }

    public String getCatalogSKU() {
        return catalogSKU;
    }
}
