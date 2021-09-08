public class Catalog {
    private String sku;

    private String description;

    private String originalCompany;

    public Catalog(String sku, String description, String originalCompany) {
        this.sku = sku;
        this.description = description;
        this.originalCompany = originalCompany;
    }

    public Catalog(Catalog catalog) {
        this.sku = catalog.sku;
        this.description = catalog.description;
        this.originalCompany = catalog.originalCompany;
    }

    public String getSKU() {
        return sku;
    }

    public String getDescription() {
        return description;
    }

    public String getOriginalCompany() {
        return originalCompany;
    }
}
