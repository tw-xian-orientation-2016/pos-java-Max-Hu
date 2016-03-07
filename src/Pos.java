import java.text.DecimalFormat;
import java.util.*;

public class Pos {

    public Map countNumber(String[] tags){
        Map<String,Float> shopList = new LinkedHashMap<String,Float>();
        for (String tag : tags) {
            String splitTagArr[] = tag.split("-");
            String barCode = splitTagArr[0];
            if (splitTagArr.length > 1) {
                shopList.put(barCode,Float.valueOf(splitTagArr[1]));
            }else {
                if (shopList.get(barCode) == null) {
                    shopList.put(barCode,Float.valueOf(1));
                }else {
                    shopList.put(barCode,shopList.get(barCode) + 1);
                }
            }
        }
        return shopList;
    }


    public List<CartItem> getItemsDetail(Map<String,Float> shopList,Map<String,Item> allItems){

        List<CartItem> cartItems = new ArrayList<>();
        Iterator iter = shopList.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            float count = (Float) entry.getValue();
            String barCode = (String) entry.getKey();
            Item item = allItems.get(barCode);
            cartItems.add(new CartItem(item,count));
        }
        return cartItems;
    }

    public List<CartItem> saleCalculate(List<CartItem> cartItems,Map<String,Map> promotions){
        Map barcodes = promotions.get("BUY_TWO_GET_ONE_FREE");
        List<CartItem> cartItemsResult = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            if (barcodes.get(cartItem.getItem().getBarcode()) != null) {
                float count = cartItem.getCount();
                float buyNumber =  count -  (float)Math.floor(count/3);
                float subTotal = buyNumber*cartItem.getItem().getPrice();
                float subSaving = (count - buyNumber)*cartItem.getItem().getPrice();
                cartItem.setSubSaving(subSaving);
                cartItem.setSubTotal(subTotal);
                cartItemsResult.add(cartItem);
            } else {
                float count = cartItem.getCount();
                cartItem.setSubTotal(cartItem.getItem().getPrice()*count);
                cartItem.setSubSaving(0f);
                cartItemsResult.add(cartItem);
            }
        }
        return cartItemsResult;
    }

    public void getReceipt(List<CartItem> cartItems){
        String output = "***<没钱赚商店>收据***\n";
        float saving = 0f,total = 0f;
        for (CartItem cartItem : cartItems) {
            output += "名称：" + cartItem.getItem().getName() + "，" +
                    "数量：" + new DecimalFormat("#.##").format(cartItem.getCount()) + cartItem.getItem().getUnit() + "，" +
                    "单价：" + formatNumber(cartItem.getItem().getPrice()) + "(元)" + "，" +
                    "小计：" + formatNumber(cartItem.getSubTotal()) + "(元)\n";
            saving += cartItem.getSubSaving();
            total += cartItem.getSubTotal();
        }
        output += "----------------------\n" +
                "总计：" + formatNumber(total) + "(元)\n" +
                "节省：" + formatNumber(saving) + "(元)\n" +
                "**********************";
        System.out.print(output);
    }

    public String formatNumber(float number){
        return new DecimalFormat("#.00").format(number);
    }

    public void execute(String[] tags){
        Map shopList = countNumber(tags);
        List<CartItem> cartDeatils = getItemsDetail(shopList,Util.loadAllItem());
        List<CartItem> cartItems = saleCalculate(cartDeatils,Util.loadPromotion());
        getReceipt(cartItems);
    }
}
