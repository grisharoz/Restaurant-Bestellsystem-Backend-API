package group10.GastroAPI.DTO;

import java.time.LocalDateTime;
import java.util.List;

import group10.GastroAPI.Models.OrderStatus;
import group10.GastroAPI.Models.PayMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrderDto(
   @NotNull Long id,
   @NotNull PayMethod payMethod,
   @NotNull OrderStatus orderStatus,
   @NotEmpty List<Long> dishIds,
   LocalDateTime createdAt
){}