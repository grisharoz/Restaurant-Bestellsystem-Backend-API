package group10.GastroAPI.DTO;

import java.util.List;

import group10.GastroAPI.Models.PayMethod;

public record PaymentMethodDTO(
        List<PayMethod> cafePaymentMethods
) {}