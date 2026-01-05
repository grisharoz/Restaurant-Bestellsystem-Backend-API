package group10.GastroAPI.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import group10.GastroAPI.Models.Dish;
import group10.GastroAPI.Repositories.DishRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishServiceSimpleTest {

    @Mock DishRepository repo;
    @InjectMocks DishServiceImpl service;

    @Test
    void testGetAllFromMenu() {
        when(repo.findByIsInMenuTrue()).thenReturn(
                Arrays.asList(
                        new Dish("Pizza", 300, 12.99, "Main", true),
                        new Dish("Pasta", 350, 10.99, "Main", true)
                )
        );

        assertEquals(2, service.getAllFromMenu().size());
    }

    @Test
    void testGetFilteredMenuAllKeywords() {
        Dish dish = new Dish("Curry", 350, 13.99, "Main",
                Arrays.asList("vegan", "spicy"), true);

        when(repo.findAll()).thenReturn(Collections.singletonList(dish));

        List<Dish> result = service.getFilteredMenuAllKeywords(
                Arrays.asList("vegan", "spicy")
        );

        assertEquals(1, result.size());
    }

    @Test
    void testGetFilteredMenuAnyKeywords() {
        Dish dish1 = new Dish("Salad", 250, 8.99, "Salad",
                Arrays.asList("vegan"), true);
        Dish dish2 = new Dish("Pizza", 300, 12.99, "Main",
                Arrays.asList("spicy"), true);

        when(repo.findAll()).thenReturn(Arrays.asList(dish1, dish2));

        List<Dish> result = service.getFilteredMenuAnyKeywords(
                Arrays.asList("vegan", "spicy")
        );

        assertEquals(2, result.size());
    }

    @Test
    void testExceptionForNullKeywords() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.getFilteredMenuAllKeywords(null)
        );
    }
}