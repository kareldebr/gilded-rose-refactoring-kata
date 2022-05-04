package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void givenItemWithSellInPassed_whenNextDay_thenQualityDecreasesTwiceAsFast() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", -2, 7) };

        GildedRose app = new GildedRose(items);
        app.updateItems();

        assertEquals(5, app.items[0].quality);
    }

    @Test
    void givenItemWithQualityOfZero_whenNextDay_thenQualityRemainsZero() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 5, 0) };

        GildedRose app = new GildedRose(items);
        app.updateItems();

        assertEquals(0, app.items[0].quality);
    }

    @Test
    void givenAgedBrie_whenNextDay_ThenQualityIncreases() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 0) };

        GildedRose app = new GildedRose(items);
        app.updateItems();

        assertEquals(1, app.items[0].quality);
    }

    @Test
    void givenAgedBrieWithQualityOfFifty_whenNextDay_ThenQualityRemainsFifty() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 50) };

        GildedRose app = new GildedRose(items);
        app.updateItems();

        assertEquals(50, app.items[0].quality);
    }

    @Test
    void givenSulfuras_whenNextDay_ThenSellInAndQualityRemainUnchanged() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };

        GildedRose app = new GildedRose(items);
        app.updateItems();

        assertEquals(0, app.items[0].sellIn);
        assertEquals(80, app.items[0].quality);
    }

    @Test
    void givenBackstagePassesWithSellInValueGreaterThanTen_whenNextDay_thenQualityIncreases() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };

        GildedRose app = new GildedRose(items);
        app.updateItems();

        assertEquals(21, app.items[0].quality);
    }

    @Test
    void givenBackstagePassesWithSellInOfZero_whenAfterConcert_thenQualityBecomesZero() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20) };

        GildedRose app = new GildedRose(items);
        app.updateItems();

        assertEquals(0, app.items[0].quality);
    }

    @Test
    void givenConjuredItem_whenNextDay_thenQualityDecreasesTwiceAsFast() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 3, 6) };

        GildedRose app = new GildedRose(items);
        app.updateItems();

        assertEquals(4, app.items[0].quality);
    }

    @ParameterizedTest
    @MethodSource("backstagePassesThatShouldIncreaseQualityByTwo")
    void givenBackstagePassesWithSellInOfTenOrLess_whenNextDay_thenQualityIncreasesByTwo(Item backstagePasses, int expectedQuality) {
        Item[] items = new Item[] { backstagePasses };

        GildedRose app = new GildedRose(items);
        app.updateItems();

        assertEquals(expectedQuality, app.items[0].quality);
    }

    @ParameterizedTest
    @MethodSource("backstagePassesThatShouldIncreaseQualityByThree")
    void givenBackstagePassesWithSellInOfFiveOrLess_whenNextDay_thenQualityIncreasesByThree(Item backstagePasses, int expectedQuality) {
        Item[] items = new Item[] { backstagePasses };

        GildedRose app = new GildedRose(items);
        app.updateItems();

        assertEquals(expectedQuality, app.items[0].quality);
    }

    @ParameterizedTest
    @MethodSource("itemsWithDefaultBehavior")
    void givenItemWithDefaultBehavior_whenNextDay_thenSellInAndQualityDecrease(Item itemWithDefaultBehavior, int expectedSellIn, int expectedQuality) {
        Item[] items = new Item[] { itemWithDefaultBehavior };

        GildedRose app = new GildedRose(items);
        app.updateItems();

        assertEquals(expectedSellIn, app.items[0].sellIn);
        assertEquals(expectedQuality, app.items[0].quality);
    }

    private static Stream<Arguments> backstagePassesThatShouldIncreaseQualityByTwo() {
        return Stream.of(
            Arguments.arguments(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20), 22),
            Arguments.arguments(new Item("Backstage passes to a TAFKAL80ETC concert", 9, 20), 22),
            Arguments.arguments(new Item("Backstage passes to a TAFKAL80ETC concert", 6, 20), 22)
        );
    }

    private static Stream<Arguments> backstagePassesThatShouldIncreaseQualityByThree() {
        return Stream.of(
            Arguments.arguments(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20), 23),
            Arguments.arguments(new Item("Backstage passes to a TAFKAL80ETC concert", 4, 20), 23),
            Arguments.arguments(new Item("Backstage passes to a TAFKAL80ETC concert", 1, 20), 23)
        );
    }

    private static Stream<Arguments> itemsWithDefaultBehavior() {
        return Stream.of(
            Arguments.arguments(new Item("+5 Dexterity Vest", 10, 20), 9, 19),
            Arguments.arguments(new Item("+5 Dexterity Vest", 4, 16), 3, 15),
            Arguments.arguments(new Item("Elixir of the Mongoose", 5, 7), 4, 6),
            Arguments.arguments(new Item("Elixir of the Mongoose", 2, 9), 1, 8)
        );
    }
}
