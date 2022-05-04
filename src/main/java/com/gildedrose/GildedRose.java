package com.gildedrose;

class GildedRose {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED = "Conjured";
    public static final int DEFAULT_ADJUSTMENT_STEP = 1;
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateItems() {
        for (Item item : items) {
            boolean itemDoesNotUpdate = item.name.equals(SULFURAS);
            if (itemDoesNotUpdate) {
                continue;
            }

            boolean sellInPassed = item.sellIn < 1;

            updateItemQuality(item, sellInPassed);
            updateItemSellInAndQualityBasedOnSellIn(item, sellInPassed);
        }
    }

    private void updateItemQuality(Item item, boolean sellInPassed) {
        boolean nameContainsConjured = item.name.contains(CONJURED);
        int adjustment = (sellInPassed || nameContainsConjured) ? DEFAULT_ADJUSTMENT_STEP * 2 : DEFAULT_ADJUSTMENT_STEP;

        switch (item.name) {
            case AGED_BRIE:
                increaseQuality(item, adjustment);
                break;
            case BACKSTAGE_PASSES:
                increaseQualityOfBackstagePasses(item, adjustment);
                break;
            default:
                decreaseQuality(item, adjustment);
        }
    }

    private void increaseQualityOfBackstagePasses(Item backstagePasses, int adjustment) {
        if (backstagePasses.sellIn <= 10) {
            adjustment++;
        }
        if (backstagePasses.sellIn <= 5) {
            adjustment++;
        }
        increaseQuality(backstagePasses, adjustment);
    }

    private void setQualityToZero(Item item) {
        item.quality = 0;
    }

    private void updateItemSellInAndQualityBasedOnSellIn(Item item, boolean sellInPassed) {
        item.sellIn -= 1;

        if (sellInPassed && item.name.equals(BACKSTAGE_PASSES)) {
            setQualityToZero(item);
        }
    }

    private void increaseQuality(Item item, int adjustment) {
        if (item.quality < 50) {
            item.quality += adjustment;
        }
    }

    private void decreaseQuality(Item item, int adjustment) {
        if (item.quality > 0) {
            item.quality -= adjustment;
        }
    }
}
