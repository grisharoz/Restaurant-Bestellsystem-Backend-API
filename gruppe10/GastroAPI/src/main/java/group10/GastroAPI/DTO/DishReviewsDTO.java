package group10.GastroAPI.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DishReviewsDTO(

        @Min(1)
        Long dishId,

        @NotBlank
        @Size(max = 100)
        String author,

        @NotBlank
        @Size(max = 200)
        String title,

        @NotBlank
        @Size(max = 1000)
        String description,

        @Min(1)
        @Max(5)
        double rating

) {}
