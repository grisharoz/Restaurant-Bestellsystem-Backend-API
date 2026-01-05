package group10.GastroAPI.Controller;

import group10.GastroAPI.DTO.DishReviewsDTO;
import group10.GastroAPI.Exceptions.NotFoundException;
import group10.GastroAPI.Models.DishReviews;
import group10.GastroAPI.Repositories.DishRepository;
import group10.GastroAPI.Repositories.DishReviewsRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dish-reviews")
public class ReviewController {

    private final DishReviewsRepository dishReviewRepository;
    private final DishRepository dishRepository;

    public ReviewController(DishReviewsRepository dishReviewRepository, DishRepository dishRepository) {
        this.dishReviewRepository = dishReviewRepository;
        this.dishRepository = dishRepository;
    }

    @GetMapping
    public List<DishReviews> getAll() {
        List<DishReviews> reviews = dishReviewRepository.findAll();
        if (reviews.isEmpty()) {
            throw new NotFoundException("Keine Bewertungen gefunden");
        }

        return reviews;
    }

    @GetMapping("/{id}")
    public DishReviewsDTO getById(@PathVariable("id") Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id darf nicht 0 oder kleiner 0 sein");
        }

        DishReviews review = dishReviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bewertung nicht gefunden"));
        return convertToDTO(review);
    }

    @GetMapping("/dish/{dishId}")
    public List<DishReviewsDTO> getByDishId(@PathVariable("dishId") Long dishId) {
        if (dishId <= 0) {
            throw new IllegalArgumentException("dishId muss größer als 0 sein");
        }

        List<DishReviews> reviews = dishReviewRepository.findByDishId(dishId);
        if (reviews.isEmpty()) {
            throw new NotFoundException("Keine Bewertungen für dieses Gericht gefunden");
        }

        List<DishReviewsDTO> result = new ArrayList<>();
        for (DishReviews review : reviews) {
            result.add(convertToDTO(review));
        }
        return result;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DishReviewsDTO create(@Valid @RequestBody DishReviewsDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("dto darf nicht null sein");
        }

        dishRepository.findById(dto.dishId()).orElseThrow(() -> new NotFoundException("Dish mit id: " + dto.dishId() + " nicht gefunden"));

        DishReviews entity = new DishReviews();
        entity.setDishId(dto.dishId());
        entity.setAuthor(dto.author());
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setRating(dto.rating());

        DishReviews saved = dishReviewRepository.save(entity);
        return convertToDTO(saved);
    }

    @PutMapping("/{id}")
    public DishReviewsDTO update(@PathVariable("id") Long id, @Valid @RequestBody DishReviewsDTO dto) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id darf nicht null oder kleiner 0 sein");
        }

        if (dto == null) {
            throw new IllegalArgumentException("dto darf nicht null sein");
        }
        dishRepository.findById(dto.dishId()).orElseThrow(() -> new NotFoundException("Dish mit id: " + dto.dishId() + " nicht gefunden"));
        DishReviews entity = dishReviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bewertung nicht gefunden"));

        entity.setDishId(dto.dishId());
        entity.setAuthor(dto.author());
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setRating(dto.rating());

        DishReviews updated = dishReviewRepository.save(entity);
        return convertToDTO(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id darf nicht null oder kleiner 0 sein");
        }

        if (!dishReviewRepository.existsById(id)) {
            throw new NotFoundException("Bewertung nicht gefunden");
        }

        dishReviewRepository.deleteById(id);
    }

    @DeleteMapping("/dish/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByDishId(@PathVariable("dishId") Long dishId) {
        if (dishId <= 0) {
            throw new IllegalArgumentException("dishId muss größer als 0 sein");
        }

        List<DishReviews> reviews = dishReviewRepository.findByDishId(dishId);
        if (reviews.isEmpty()) {
            throw new NotFoundException("Keine Bewertungen für dieses Gericht gefunden");
        }

        dishReviewRepository.deleteAll(reviews);
    }

    private DishReviewsDTO convertToDTO(DishReviews entity) {
        return new DishReviewsDTO(
                entity.getDishId(),
                entity.getAuthor(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getRating()
        );
    }
}