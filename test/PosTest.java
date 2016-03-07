import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PosTest {

    Pos pos;

    @Before
    public void setup(){

        pos = new Pos();
    }

    @Test
    public void verify_get_correct_count(){
        String input[] = {"ITEM000001","ITEM000001","ITEM000001","ITEM000001","ITEM000001",
                "ITEM000003-2","ITEM000005","ITEM000005", "ITEM000005"};
        float checkArr[] = {5f,2f,3f};
        Map<String,Float> result = pos.countNumber(input);
        Iterator iter = result.entrySet().iterator();
        int index = 0;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object count = entry.getValue();
            assertEquals(checkArr[index],count);
            index ++;
        }
    }

    @Test
    public void verify_get_correct_Item_detail(){
        String[] outputs = {
                "CartItem{item=Item{barcode='ITEM000004', name='电池', unit='个', price=2.0}, count=2.0, subTotal=0.0, subSaving=0.0}",
                "CartItem{item=Item{barcode='ITEM000001', name='雪碧', unit='瓶', price=3.0}, count=5.0, subTotal=0.0, subSaving=0.0}",
                "CartItem{item=Item{barcode='ITEM000002', name='苹果', unit='斤', price=5.5}, count=3.0, subTotal=0.0, subSaving=0.0}"};
        Map input = new HashMap<>();
        input.put("ITEM000001",5f);
        input.put("ITEM000002",3f);
        input.put("ITEM000004",2f);
        List<CartItem> cartItems = pos.getItemsDetail(input,Util.loadAllItem());
        for (int i = 0; i < cartItems.size(); i++) {
            assertEquals(outputs[i],cartItems.get(i).toString());
        }
    }

    @Test
    public void verify_get_correct_subSaving_and_subTotal(){
        String[] outputs = {
                "CartItem{item=Item{barcode='ITEM000004', name='电池', unit='个', price=2.0}, count=4.0, subTotal=8.0, subSaving=0.0}",
                "CartItem{item=Item{barcode='ITEM000001', name='雪碧', unit='瓶', price=3.0}, count=3.0, subTotal=6.0, subSaving=3.0}",
                "CartItem{item=Item{barcode='ITEM000005', name='方便面', unit='袋', price=5.5}, count=5.0, subTotal=22.0, subSaving=5.5}"};
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem1 = new CartItem(new Item("ITEM000004","电池","个",2f),4);
        cartItems.add(cartItem1);
        CartItem cartItem2 = new CartItem(new Item("ITEM000001","雪碧","瓶",3f),3);
        cartItems.add(cartItem2);
        CartItem cartItem3 = new CartItem(new Item("ITEM000005","方便面","袋",5.5f),5);
        cartItems.add(cartItem3);
        List<CartItem> result = pos.saleCalculate(cartItems,Util.loadPromotion());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(outputs[i],result.get(i).toString());
        }
    }

    @Test
    public void verify_print_receipt_is_correct(){
        PrintStream stream = mock(PrintStream.class);
        String expectText =
                        "***<没钱赚商店>收据***\n" +
                        "名称：雪碧，数量：5瓶，单价：3.00(元)，小计：12.00(元)\n" +
                        "名称：荔枝，数量：2斤，单价：15.00(元)，小计：30.00(元)\n" +
                        "名称：方便面，数量：3袋，单价：4.50(元)，小计：9.00(元)\n" +
                        "----------------------\n" +
                        "总计：51.00(元)\n" +
                        "节省：7.50(元)\n" +
                        "**********************";
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem1 = new CartItem(new Item("ITEM000001","雪碧","瓶",3f),5f,12f,3f);
        cartItems.add(cartItem1);
        CartItem cartItem2 = new CartItem(new Item("ITEM000003","荔枝","斤",15f),2f,30f,0f);
        cartItems.add(cartItem2);
        CartItem cartItem3 = new CartItem(new Item("ITEM000005","方便面","袋",4.5f),3f,9f,4.5f);
        cartItems.add(cartItem3);
        System.setOut(stream);
        pos.getReceipt(cartItems);
        verify(stream).print(expectText);
    }

    @Test
    public void verify_pos_works_correctly(){
        PrintStream mockStream = mock(PrintStream.class);
        String input[] = {"ITEM000001","ITEM000001","ITEM000001","ITEM000001","ITEM000001",
                "ITEM000003-2","ITEM000005","ITEM000005", "ITEM000005"};
        String expectText =
                "***<没钱赚商店>收据***\n" +
                        "名称：雪碧，数量：5瓶，单价：3.00(元)，小计：12.00(元)\n" +
                        "名称：荔枝，数量：2斤，单价：15.00(元)，小计：30.00(元)\n" +
                        "名称：方便面，数量：3袋，单价：4.50(元)，小计：9.00(元)\n" +
                        "----------------------\n" +
                        "总计：51.00(元)\n" +
                        "节省：7.50(元)\n" +
                        "**********************";
        System.setOut(mockStream);
        pos.execute(input);
        verify(mockStream).print(expectText);
    }

}
