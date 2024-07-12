package Model;

public class ImportedPhone extends Phone {
    private String importCountry;
    private String status;

    public ImportedPhone(String id, String name, double price, int quantity, String manufacturer,
                         String importCountry, String status) {
        super(id, name, price, quantity, manufacturer);
        this.importCountry = importCountry;
        this.status = status;
    }

    public String getImportCountry() {
        return importCountry;
    }

    public void setImportCountry(String importCountry) {
        this.importCountry = importCountry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString() + ", Import Country: " + importCountry + ", Status: " + status;
    }
}

