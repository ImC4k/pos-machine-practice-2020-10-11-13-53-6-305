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
