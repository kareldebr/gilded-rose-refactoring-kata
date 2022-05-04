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

    public void updateQuality() {
        for (Item item : items) {
            updateItemQuality(item);
        }
    }

    private void updateItemQuality(Item item) {
        boolean itemQualityDoesNotUpdate = item.name.equals(SULFURAS);
        if (itemQualityDoesNotUpdate) {
            return;
        }

        boolean sellInPassed = item.sellIn < 1;
        boolean nameContainsConjured = item.name.contains(CONJURED);
        int adjustment = (sellInPassed || nameContainsConjured) ? DEFAULT_ADJUSTMENT_STEP * 2 : DEFAULT_ADJUSTMENT_STEP;

        switch (item.name) {
            case AGED_BRIE:
                handleBrie(item, adjustment);
                break;
            case BACKSTAGE_PASSES:
                handleBackstagePasses(item, sellInPassed, adjustment);
                break;
            default:
                handleItemsWithoutRequirements(item, adjustment);
        }
    }

    private void handleItemsWithoutRequirements(Item item, int adjustmentFactoringInSellInAndType) {
        decreaseQuality(item, adjustmentFactoringInSellInAndType);
        decreaseSellIn(item);
    }

    private void handleBrie(Item item, int adjustmentFactoringInSellInAndType) {
        increaseQuality(item, adjustmentFactoringInSellInAndType);
        decreaseSellIn(item);
    }

    private void handleBackstagePasses(Item item, boolean sellInPassed, int defaultAdjustment) {
        increaseQuality(item, defaultAdjustment);
        if (item.name.equals(BACKSTAGE_PASSES)) {
            if (item.sellIn < 11) {
                increaseQuality(item, defaultAdjustment);
            }
            if (item.sellIn < 6) {
                increaseQuality(item, defaultAdjustment);
            }
        }

        decreaseSellIn(item);

        if (sellInPassed) {
            setQualityToZero(item);
        }
    }

    private void setQualityToZero(Item item) {
        item.quality = 0;
    }

    private void decreaseSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    private void increaseQuality(Item item, int adjustment) {
        if (item.quality < 50) {
            item.quality = item.quality + adjustment;
        }
    }

    private void decreaseQuality(Item item, int adjustment) {
        if (item.quality > 0) {
            item.quality = item.quality - adjustment;
        }
    }
}
