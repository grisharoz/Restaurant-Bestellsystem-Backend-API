package group10.GastroAPI.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record DishDto(

        @NotBlank
        @Size(max = 150)
        String name,

        @Min(1)
        int weight,

        @Min(1)
        double price,

        @NotBlank
        @Size(max = 150)
        String category,

        boolean isInMenu,

        List<String> filterKeywords

) {}
