import java.util.List;

public class Promotion {

    private String type;
    private String barcodes[];

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(String[] barcodes) {
        this.barcodes = barcodes;
    }

    public Promotion(String type, String[] barcodes) {

        this.type = type;
        this.barcodes = barcodes;
    }
}
