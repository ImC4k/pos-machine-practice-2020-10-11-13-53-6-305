package pos.machine;

import java.util.*;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ItemInfo> itemsDetail = ItemDataLoader.loadAllItemInfos();
        return generateReceipt(barcodes, itemsDetail);
    }

    private String generateReceipt(List<String> ids, List<ItemInfo> itemsDetail) {
        String receipt = generateReceiptHeader();
        Map<String, Integer> itemsQuantity = countQuantity(ids.toArray(new String[0]));
        receipt += generateReceiptItemLines(itemsDetail, itemsQuantity) + "\n";
        Integer total = calculateTotal(itemsDetail, itemsQuantity);
        receipt += generateReceiptFooter(total);
        return receipt;
    }

    private String generateReceiptHeader() {
        return "***<store earning no money>Receipt***\n";
    }

    private String generateReceiptItemLines(List<ItemInfo> itemsDetail, Map<String, Integer> itemsQuantity) {
        List<String> receiptLines = new ArrayList<>();
        Arrays.stream(itemsQuantity.keySet().toArray(new String[0])).sorted().forEach(itemId -> {
            ItemInfo itemDetail = getItemInfoById(itemId, itemsDetail);
            receiptLines.add(generateReceiptItemLine(itemDetail, itemsQuantity.get(itemDetail.getBarcode())));
        });
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

    private Integer calculateItemSubtotal(ItemInfo itemDetail, Integer quantity) {
        return itemDetail.getPrice() * quantity;
    }

    private Integer calculateTotal(List<ItemInfo> itemsDetail, Map<String, Integer> itemsQuantity) {
        Integer total = 0;
        for (String itemId: itemsQuantity.keySet()) {
            total += calculateItemSubtotal(getItemInfoById(itemId, itemsDetail), itemsQuantity.get(itemId));
        }
        return total;
    }

    private ItemInfo getItemInfoById(String id, List<ItemInfo> allItemsDetail) {
        for (ItemInfo item: allItemsDetail) {
            if (item.getBarcode().equals(id)) return item;
        }
        return null;
    }

    private String generateReceiptFooter(Integer total) {
        return String.format(
                "----------------------\n" +
                "Total: %d (yuan)\n" +
                "**********************", total);
    }
}
