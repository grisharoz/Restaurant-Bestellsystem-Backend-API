package group10.GastroAPI.Services;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import group10.GastroAPI.Models.Dish;
import group10.GastroAPI.Repositories.DishRepository;

@Service
public class DishServiceImpl implements DishService{

    DishRepository _dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this._dishRepository = dishRepository;
    }


    /**
     * Filtert Gerichte basierend auf allen angegebenen Keywords.
     * @param keywords Die Liste von Schlüsselwörtern
     * @return Gefilterte Liste von Gerichten
     * @throws IllegalArgumentException Wenn keywords null oder leer ist
     */
    public List<Dish> getFilteredMenuAllKeywords(List<String> keywords) throws IllegalArgumentException {
        if (keywords == null) {
            throw new IllegalArgumentException("Keywords list cannot be null");
        }

        if (keywords.isEmpty()) {
            throw new IllegalArgumentException("Keywords list cannot be leer. Mindestens 1 keyword ist notwendig.");
        }

        List<Dish> filteredList = new ArrayList<>();
        List<Dish> dishes = _dishRepository.findByIsInMenuTrue();

        for (Dish dish : dishes) {
            List<String> dishKeywords = dish.getFilterKeywords();

            boolean containsAllKeywords = true;

            for (String searchKeyword : keywords) {
                if (searchKeyword == null) {
                    throw new IllegalArgumentException("Keyword cannot be null");
                }
                if (searchKeyword.trim().isEmpty()) {
                    throw new IllegalArgumentException("Keyword cannot be empty or whitespace");
                }

                String normalizedSearchKeyword = searchKeyword.trim().toLowerCase();
                boolean keywordFound = false;

                if (dishKeywords != null && !dishKeywords.isEmpty()) {
                    for (String dishKeyword : dishKeywords) {
                        if (dishKeyword != null) {
                            String normalizedDishKeyword = dishKeyword.trim().toLowerCase();
                            if (normalizedSearchKeyword.equals(normalizedDishKeyword)) {
                                keywordFound = true;
                                break;
                            }
                        }
                    }
                }

                if (!keywordFound) {
                    containsAllKeywords = false;
                    break;
                }
            }

            if (containsAllKeywords) {
                filteredList.add(dish);
            }
        }
        return filteredList;
    }

    /**
     * Filtert Gerichte basierend auf mindestens einem der angegebenen Keywords.
     * @param keywords Die Liste von Schlüsselwörtern
     * @return Gefilterte Liste von Gerichten
     */
    public List<Dish> getFilteredMenuAnyKeywords(List<String> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            return new ArrayList<>();
        }

        List<Dish> results = new ArrayList<>();
        List<Dish> dishes = _dishRepository.findByIsInMenuTrue();


        for (Dish dish : dishes) {
            List<String> dishKeywords = dish.getFilterKeywords();
            if (dishKeywords == null) {
                continue;
            }

            boolean foundAny = false;
            for (String keyword : keywords) {
                if (keyword == null || keyword.trim().isEmpty()) {
                    continue;
                }

                String normalizedKeyword = keyword.trim().toLowerCase();
                for (String dishKeyword : dishKeywords) {
                    if (dishKeyword != null &&
                            dishKeyword.trim().toLowerCase().equals(normalizedKeyword)) {
                        foundAny = true;
                        break;
                    }
                }

                if (foundAny) {
                    break;
                }
            }

            if (foundAny) {
                results.add(dish);
            }
        }

        return results;
    }

    @Override
    public List<Dish> getAllFromMenu() {
        return _dishRepository.findByIsInMenuTrue();
    }
}
