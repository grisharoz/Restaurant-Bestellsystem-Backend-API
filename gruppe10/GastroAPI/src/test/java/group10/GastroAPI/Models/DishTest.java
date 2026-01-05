package group10.GastroAPI.Models;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DishTest {

    @Test
    void constructorWithRequiredFieldsSetsDefaults() {
        Dish dish = new Dish("Pizza", 500, 12.99, "Main");

        assertEquals("Pizza", dish.getName());
        assertEquals(500, dish.getWeight());
        assertEquals(12.99, dish.getPrice());
        assertEquals("Main", dish.getCategory());
        assertTrue(dish.getFilterKeywords().isEmpty());
        assertFalse(dish.getIsInMenu());
    }

    @Test
    void constructorWithFiltersCreatesDefensiveCopy() {
        List<String> keywords = new ArrayList<>(List.of("vegan", "spicy"));

        Dish dish = new Dish("Curry", 400, 9.99, "Vegan", keywords);
        keywords.add("extra"); 

        assertIterableEquals(List.of("vegan", "spicy"), dish.getFilterKeywords());
    }

    @Test
    void constructorWithMenuFlagSetsState() {
        Dish dish = new Dish("Soup", 300, 4.99, "Starter", List.of("light"), true);

        assertTrue(dish.getIsInMenu());
    }

    @Test
    void validationRejectsInvalidName() {
        IllegalArgumentException nullName = assertThrows(
                IllegalArgumentException.class,
                () -> new Dish(null, 100, 1.00, "Cat"));
        assertEquals("Der Name darf nicht leer sein", nullName.getMessage());

        IllegalArgumentException blankName = assertThrows(
                IllegalArgumentException.class,
                () -> new Dish("   ", 100, 1.00, "Cat"));
        assertEquals("Der Name darf nicht leer sein", blankName.getMessage());

        IllegalArgumentException tooLong = assertThrows(
                IllegalArgumentException.class,
                () -> new Dish("a".repeat(151), 100, 1.00, "Cat"));
        assertEquals("Der Name darf nicht länger als 150 Zeichen sein", tooLong.getMessage());
    }

    @Test
    void validationRejectsInvalidCategory() {
        IllegalArgumentException nullCategory = assertThrows(
                IllegalArgumentException.class,
                () -> new Dish("Burger", 100, 1.00, null));
        assertEquals("Die Kategorie darf nicht leer sein", nullCategory.getMessage());

        IllegalArgumentException blankCategory = assertThrows(
                IllegalArgumentException.class,
                () -> new Dish("Burger", 100, 1.00, "   "));
        assertEquals("Die Kategorie darf nicht leer sein", blankCategory.getMessage());

        IllegalArgumentException tooLong = assertThrows(
                IllegalArgumentException.class,
                () -> new Dish("Burger", 100, 1.00, "a".repeat(151)));
        assertEquals("Die Kategorie darf nicht länger als 150 Zeichen sein", tooLong.getMessage());
    }

    @Test
    void validationRejectsNonPositiveNumbers() {
        IllegalArgumentException weightZero = assertThrows(
                IllegalArgumentException.class,
                () -> new Dish("Burger", 0, 1.00, "Cat"));
        assertEquals("Das Gewicht darf nicht 0 oder kleiner 0 sein", weightZero.getMessage());

        IllegalArgumentException weightNegative = assertThrows(
                IllegalArgumentException.class,
                () -> new Dish("Burger", -1, 1.00, "Cat"));
        assertEquals("Das Gewicht darf nicht 0 oder kleiner 0 sein", weightNegative.getMessage());

        IllegalArgumentException priceZero = assertThrows(
                IllegalArgumentException.class,
                () -> new Dish("Burger", 100, 0, "Cat"));
        assertEquals("Der Preis darf nicht 0 oder kleiner 0 sein", priceZero.getMessage());

        IllegalArgumentException priceNegative = assertThrows(
                IllegalArgumentException.class,
                () -> new Dish("Burger", 100, -5, "Cat"));
        assertEquals("Der Preis darf nicht 0 oder kleiner 0 sein", priceNegative.getMessage());
    }

    @Test
    void editUpdatesAllFieldsWithValidationAndDefensiveCopy() {
        Dish dish = new Dish("Old", 100, 100, "Cat");
        List<String> newKeywords = new ArrayList<>(List.of("new", "fresh"));
        Dish newValues = new Dish("New", 200, 300, "Hot", newKeywords, true);

        dish.edit(newValues);
        newKeywords.add("added");

        assertEquals("New", dish.getName());
        assertEquals(200, dish.getWeight());
        assertEquals(300, dish.getPrice());
        assertEquals("Hot", dish.getCategory());
        assertTrue(dish.getIsInMenu());
        assertIterableEquals(List.of("new", "fresh"), dish.getFilterKeywords());
    }

    @Test
    void editRejectsNullSource() {
        Dish dish = new Dish("Old", 100, 100, "Cat");

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> dish.edit(null));
        assertEquals("newValues darf nicht null sein", error.getMessage());
    }
}
