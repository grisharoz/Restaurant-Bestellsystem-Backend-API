package group10.GastroAPI.Services;

import java.util.List;

import group10.GastroAPI.Models.Dish;

public interface DishService {
    List<Dish> getFilteredMenuAllKeywords(List<String> keywords);

    List<Dish> getFilteredMenuAnyKeywords(List<String> keywords);

    List<Dish> getAllFromMenu();
}
