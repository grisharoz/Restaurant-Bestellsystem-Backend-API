package group10.GastroAPI.Services;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group10.GastroAPI.Models.Dish;
import group10.GastroAPI.Repositories.DishRepository;

public class DishServiceImplTest {
   private DishRepository dishRepository;
   private DishServiceImpl dishServiceImpl;

   @BeforeEach
   void setUp(){
      dishRepository = mock(DishRepository.class);
      dishServiceImpl = new DishServiceImpl(dishRepository);
   }

   @Test
   void getFilteredMenuAllKeywords_check(){
      List<Dish> dishes = new ArrayList<>();
      Dish spaghettiBolognese = new Dish("Spaghetti Bolognese", 450, 12.90, "Hauptgericht", List.of("fleisch", "italienisch", "nudeln", "klassisch"), true);
      Dish quinoaSalat = new Dish("Veganer Quinoa Salat", 350, 9.50, "Salat", List.of("vegan", "gesund", "glutenfrei", "leicht"), true);
      Dish cheeseburger = new Dish("Cheeseburger", 500, 11.90, "Burger", List.of("fleisch", "burger", "käse", "fastfood"), true);
      
      dishes.add(spaghettiBolognese);
      dishes.add(quinoaSalat);
      dishes.add(cheeseburger);

      when(dishRepository.findAll()).thenReturn(dishes);

      assertTrue(dishServiceImpl.getFilteredMenuAllKeywords(List.of("vegan", "gesund", "glutenfrei")).contains(quinoaSalat));
      assertFalse(dishServiceImpl.getFilteredMenuAllKeywords(List.of("fleisch", "italienisch")).contains(cheeseburger));
      assertThrows(IllegalArgumentException.class, () -> dishServiceImpl.getFilteredMenuAllKeywords(null));
      assertThrows(IllegalArgumentException.class, () -> dishServiceImpl.getFilteredMenuAllKeywords(List.of()));

      verify(dishRepository, times(2)).findAll();
   }

   @Test
   void getFilteredMenuAnyKeywords_check(){
      List<Dish> dishes = new ArrayList<>();
      Dish spaghettiBolognese = new Dish("Spaghetti Bolognese", 450, 12.90, "Hauptgericht", List.of("fleisch", "italienisch", "nudeln", "klassisch"), true);
      Dish quinoaSalat = new Dish("Veganer Quinoa Salat", 350, 9.50, "Salat", List.of("vegan", "gesund", "glutenfrei", "leicht"), true);
      Dish cheeseburger = new Dish("Cheeseburger", 500, 11.90, "Burger", List.of("fleisch", "burger", "käse", "fastfood"), true);

      dishes.add(spaghettiBolognese);
      dishes.add(quinoaSalat);
      dishes.add(cheeseburger);

      when(dishRepository.findAll()).thenReturn(dishes);

      assertTrue(dishServiceImpl.getFilteredMenuAnyKeywords(List.of("fleisch")).containsAll(List.of(spaghettiBolognese, cheeseburger)));

      assertFalse(dishServiceImpl.getFilteredMenuAnyKeywords(List.of("fleisch")).contains(quinoaSalat));

      assertTrue(dishServiceImpl.getFilteredMenuAnyKeywords(List.of()).isEmpty());

      verify(dishRepository, times(2)).findAll();
   }

   @Test
   void getAllFromMenu_check(){
      List<Dish> dishes = new ArrayList<>();
      Dish spaghettiBolognese = new Dish("Spaghetti Bolognese", 450, 12.90, "Hauptgericht", List.of("fleisch", "italienisch", "nudeln", "klassisch"), true);
      Dish quinoaSalat = new Dish("Veganer Quinoa Salat", 350, 9.50, "Salat", List.of("vegan", "gesund", "glutenfrei", "leicht"), true);
      Dish cheeseburger = new Dish("Cheeseburger", 500, 11.90, "Burger", List.of("fleisch", "burger", "käse", "fastfood"), true);

      dishes.add(spaghettiBolognese);
      dishes.add(quinoaSalat);
      dishes.add(cheeseburger);

      when(dishRepository.findByIsInMenuTrue()).thenReturn(dishes);

      assertTrue(dishServiceImpl.getAllFromMenu().containsAll(List.of(spaghettiBolognese, cheeseburger, quinoaSalat)));
      assertNotEquals(List.of(cheeseburger, quinoaSalat), dishServiceImpl.getAllFromMenu());

      verify(dishRepository, times(2)).findByIsInMenuTrue();
   }
}
