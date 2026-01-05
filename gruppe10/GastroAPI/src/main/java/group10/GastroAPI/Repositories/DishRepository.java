package group10.GastroAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import group10.GastroAPI.Models.Dish;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByIsInMenuTrue();
}
