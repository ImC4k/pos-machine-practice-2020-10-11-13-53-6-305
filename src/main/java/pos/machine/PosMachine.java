package pos.machine;

import java.lang.reflect.Array;
import java.util.*;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ItemInfo> itemsDetail = ItemDataLoader.loadAllItemInfos();
        return generateReceipt(itemsDetail);
    }

    private String generateReceipt(List<ItemInfo> itemsDetail) {
        return null;
    }

    private String generateReceiptHeader() {
        return "***<store earning no money>Receipt***\n";
    }

    private String generateReceiptItemLines(ItemInfo[] itemsDetail, Map<String, Integer> itemsQuantity) {
        String[] receiptLines = (String[]) Arrays.stream(itemsDetail).map(itemDetail ->
                generateReceiptItemLine(itemDetail, itemsQuantity.get(itemDetail.getName()))).toArray();
        return String.join("\n", receiptLines);
    }

    private String generateReceiptItemLine(ItemInfo itemDetail, Integer quantity) {
        return String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)", itemDetail.getName(), quantity, itemDetail.getPrice(), calculateItemSubtotal(itemDetail, quantity));
    }

    private Map<String, Integer> countQuantity(String[] items) {
        Map<String, Integer> quantityMap = new HashMap<>();
        Arrays.stream(items).forEach(item -> {
            if (quantityMap.containsKey(item)) {
                quantityMap.put(item, quantityMap.get(item) + 1);
            }
            else {
                quantityMap.put(item, 1);
            }
        });
        return quantityMap;
    }
}
