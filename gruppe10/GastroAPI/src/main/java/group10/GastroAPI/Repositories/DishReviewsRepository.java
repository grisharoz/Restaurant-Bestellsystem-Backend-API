package group10.GastroAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import group10.GastroAPI.Models.DishReviews;
import java.util.List;

public interface DishReviewsRepository extends JpaRepository<DishReviews, Long> {
    List<DishReviews> findByDishId(Long dishId);
}
