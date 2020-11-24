package pos.machine;

import java.util.List;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ItemInfo> itemsDetail = ItemDataLoader.loadAllItemInfos();
        return generateReceipt(itemsDetail);
    }

    private String generateReceipt(List<ItemInfo> itemsDetail) {
        return null;
    }
}
