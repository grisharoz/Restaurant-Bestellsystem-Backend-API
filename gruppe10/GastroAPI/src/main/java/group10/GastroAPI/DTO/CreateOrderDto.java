package group10.GastroAPI.DTO;

import java.util.List;

import group10.GastroAPI.Models.PayMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateOrderDto(
    @NotNull PayMethod payMethod,
    @NotEmpty List<Long> dishIds
) {}

