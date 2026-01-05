package group10.GastroAPI.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import group10.GastroAPI.DTO.DishDto;
import group10.GastroAPI.Exceptions.NotFoundException;
import group10.GastroAPI.Models.Dish;
import group10.GastroAPI.Repositories.DishRepository;
import group10.GastroAPI.Services.DishServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/dishes")
public class DishController {
    
    DishRepository _dishRepository;
    DishServiceImpl _dishServiceImpl;

    DishController(DishRepository dishRepository,  DishServiceImpl dishServiceImpl) {
        _dishRepository = dishRepository;
        _dishServiceImpl = dishServiceImpl;
    }

    @GetMapping
    public List<Dish> getAll() {
        List<Dish> dishes = _dishRepository.findAll();
        if (dishes.isEmpty()) {
            throw new NotFoundException("Keine Gerichte gefunden");
        }
        return dishes;
    }

    @GetMapping("/{id}")
    public Dish getDishById(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id darf nicht gleich null oder kleiner 0 sein");
        }
        Dish dish = _dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gericht nicht gefunden"));
        return dish;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DishDto createDish(@Valid @RequestBody DishDto dishDto) {
        if (dishDto == null) {
            throw new IllegalArgumentException("dishDto darf nicht null sein");
        }

        Dish saved = _dishRepository.save(convertToEntity(dishDto));
        return convertToDTO(saved);
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public List<DishDto> createDishes(@Valid @RequestBody List<DishDto> dishDtos) {
        if (dishDtos == null || dishDtos.isEmpty()) {
            throw new IllegalArgumentException("dishDtos darf nicht null oder leer sein");
        }

        List<Dish> dishes = new ArrayList<>(dishDtos.size());
        for (DishDto dto : dishDtos) {
            dishes.add(convertToEntity(dto));
        }

        List<Dish> saved = _dishRepository.saveAll(dishes);
        List<DishDto> savedDtos = new ArrayList<>(saved.size());
        for (Dish dish : saved) {
            savedDtos.add(convertToDTO(dish));
        }

        return savedDtos;
    }

    @PutMapping("/{id}")
    public DishDto editDish(@PathVariable("id") Long id, @Valid @RequestBody DishDto newDish) {
        if (newDish == null) {
            throw new IllegalArgumentException("dish darf nicht null sein");
        }

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id darf nicht gleich null oder kleiner 0 sein");
        }

        Dish dish = _dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gericht nicht gefunden"));

        dish.edit(convertToEntity(newDish));
        Dish saved = _dishRepository.save(dish);

        return convertToDTO(saved);
    }

    @GetMapping("/menu")
    public List<Dish> getDishesInMenu() {
        List<Dish> menuDishes = _dishServiceImpl.getAllFromMenu();
        if (menuDishes.isEmpty()) {
            throw new NotFoundException("Keine Gerichte auf der Speisekarte");
        }
        return menuDishes;
    }

    @PutMapping("/add-to-menu/{id}")
    public DishDto addDishToMenu(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id darf nicht gleich null oder kleiner 0 sein");
        }
        Dish dish = _dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gericht nicht gefunden"));

        if (!dish.getIsInMenu()) {
            dish.setIsInMenu(true);
            _dishRepository.save(dish);
        }

        return convertToDTO(dish);
    }

    @PutMapping("/remove-from-menu/{id}")
    public DishDto removeDishFromMenu(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id darf nicht gleich null oder kleiner 0 sein");
        }
        Dish dish = _dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gericht nicht gefunden"));

        if (dish.getIsInMenu()) {
            dish.setIsInMenu(false);
            _dishRepository.save(dish);
        }

        return convertToDTO(dish);
    }

    @GetMapping("/keywords/any")
    public List<Dish> getDishesByAnyFilterKeywords(@RequestBody List<String> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            throw new IllegalArgumentException("keywords darf nicht null oder leer sein");
        }
        List<Dish> filtered = _dishServiceImpl.getFilteredMenuAnyKeywords(keywords);
        return filtered;
    }

    @GetMapping("/keywords/all")
    public List<Dish> getDishesByAllFilterKeywords(@RequestBody List<String> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            throw new IllegalArgumentException("keywords darf nicht null oder leer sein");
        }
        List<Dish> filtered = _dishServiceImpl.getFilteredMenuAllKeywords(keywords);
        return filtered;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id darf nicht gleich null oder kleiner 0 sein");
        }

        if (!_dishRepository.existsById(id)) {
            throw new NotFoundException("Gericht nicht gefunden");
        }

        _dishRepository.deleteById(id);
    }

    private DishDto convertToDTO(Dish dish) {
        return new DishDto(
                dish.getName(),
                dish.getWeight(),
                dish.getPrice(),
                dish.getCategory(),
                dish.getIsInMenu(),
                dish.getFilterKeywords()
        );
    }

    private Dish convertToEntity(DishDto dto) {
        return new Dish(
                dto.name(),
                dto.weight(),
                dto.price(),
                dto.category(),
                dto.filterKeywords(),
                dto.isInMenu()
        );
    }
}