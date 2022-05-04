package com.gildedrose;

class GildedRose {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
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
        if (item.name.equals(SULFURAS)) {
            return;
        }

        boolean sellInPassed = item.sellIn < 1;
        boolean decreasesInQualityOverTime = !item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES);
        int defaultAdjustment = 1;
        int adjustmentFactoringInSellInValue = sellInPassed ? defaultAdjustment * 2 : defaultAdjustment;

        if (item.name.equals(BACKSTAGE_PASSES)) {
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

        if (item.name.equals(AGED_BRIE)) {
            increaseQuality(item, adjustmentFactoringInSellInValue);
            decreaseSellIn(item);
        }

        if (decreasesInQualityOverTime) {
            decreaseQuality(item, adjustmentFactoringInSellInValue);
            decreaseSellIn(item);
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
