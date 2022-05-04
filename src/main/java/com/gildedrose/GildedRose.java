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

        if (item.name.equals(BACKSTAGE_PASSES)) {
            increaseQuality(item);

            if (item.name.equals(BACKSTAGE_PASSES)) {
                if (item.sellIn < 11) {
                    increaseQuality(item);
                }

                if (item.sellIn < 6) {
                    increaseQuality(item);
                }
            }

            decreaseSellIn(item);

            if (sellInPassed) {
                setQualityToZero(item);
            }
        }

        if (item.name.equals(AGED_BRIE)) {
            increaseQuality(item);
            decreaseSellIn(item);

            if (sellInPassed) {
                increaseQuality(item);
            }
        }

        if (decreasesInQualityOverTime) {
            decreaseQuality(item);
            decreaseSellIn(item);

            if (sellInPassed) {
                decreaseQuality(item);
            }
        }
    }

    private void setQualityToZero(Item item) {
        item.quality = 0;
    }

    private void decreaseSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    private void increaseQuality(Item item) {
        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }
    }

    private void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality = item.quality - 1;
        }
    }
}
