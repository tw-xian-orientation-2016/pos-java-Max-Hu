public class CartItem {
    private Item item;
    private float count;
    private float subTotal;
    private float subSaving;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public float getSubSaving() {
        return subSaving;
    }

    public void setSubSaving(float subSaving) {
        this.subSaving = subSaving;
    }

    public CartItem(Item item, float count, float subTotal, float subSaving) {
        this.item = item;
        this.count = count;
        this.subTotal = subTotal;
        this.subSaving = subSaving;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "item=" + item +
                ", count=" + count +
                ", subTotal=" + subTotal +
                ", subSaving=" + subSaving +
                '}';
    }

    public CartItem(Item item, float count) {
        this.item = item;
        this.count = count;
    }
}
