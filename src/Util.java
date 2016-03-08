import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    public static Map<String,Item> loadAllItem(){
        Map<String,Item> items = new HashMap<>();
        items.put("ITEM000000",new Item("ITEM000000","可口可乐","瓶",3f));
        items.put("ITEM000001",new Item("ITEM000001","雪碧","瓶",3f));
        items.put("ITEM000002",new Item("ITEM000002","苹果","斤",5.5f));
        items.put("ITEM000003",new Item("ITEM000003","荔枝","斤",15f));
        items.put("ITEM000004",new Item("ITEM000004","电池","个",2f));
        items.put("ITEM000005",new Item("ITEM000005","方便面","袋",4.5f));
        return items;
    }

    public static Map<String,Map> loadPromotion(){
        Map<String,Map> promotions = new HashMap<>();
        Map<String,Integer> barcodeMap = new HashMap<>();
        barcodeMap.put("ITEM000000",1);
        barcodeMap.put("ITEM000001",1);
        barcodeMap.put("ITEM000005",1);
        promotions.put("BUY_TWO_GET_ONE_FREE",barcodeMap);
        return promotions;
    }
}