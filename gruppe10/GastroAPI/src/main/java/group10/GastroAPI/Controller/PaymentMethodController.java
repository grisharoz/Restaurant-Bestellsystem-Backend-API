package group10.GastroAPI.Controller;

import group10.GastroAPI.DTO.PaymentMethodDTO;
import group10.GastroAPI.Exceptions.NotFoundException;
import group10.GastroAPI.Models.PaymentMethod;
import group10.GastroAPI.Models.PayMethod;
import group10.GastroAPI.Repositories.PaymentMethodRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodController(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @GetMapping
    public List<PaymentMethodDTO> getAll() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        if (paymentMethods.isEmpty()) {
            throw new NotFoundException("Keine Zahlungsmethoden gefunden");
        }

        List<PaymentMethodDTO> result = new ArrayList<>();
        for (PaymentMethod paymentMethod : paymentMethods) {
            result.add(convertToDTO(paymentMethod));
        }
        return result;
    }


    @GetMapping("/available")
    public List<PayMethod> getAvailableMethods() {
        PaymentMethod temp = new PaymentMethod();
        return temp.getAllAvailableMethods();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodDTO create(@Valid @RequestBody PaymentMethodDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("dto darf nicht null sein");
        }
        if (!paymentMethodRepository.findAll().isEmpty()) {
            throw new IllegalArgumentException("Zahlungsmethoden wurden bereits festgelegt. Verwenden Sie PUT zum Aktualisieren.");
        }

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setCafePaymentMethods(dto.cafePaymentMethods());

        PaymentMethod saved = paymentMethodRepository.save(paymentMethod);
        return convertToDTO(saved);
    }

    @PutMapping
    public PaymentMethodDTO update(@Valid @RequestBody PaymentMethodDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("dto darf nicht null sein");
        }

        List<PaymentMethod> existingMethods = paymentMethodRepository.findAll();
        if (existingMethods.isEmpty()) {
            throw new NotFoundException("Keine Zahlungsmethoden zum Aktualisieren gefunden. Verwenden Sie POST zum Erstellen.");
        }

        PaymentMethod existing = existingMethods.get(0);
        existing.setCafePaymentMethods(dto.cafePaymentMethods());

        PaymentMethod updated = paymentMethodRepository.save(existing);
        return convertToDTO(updated);
    }



    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        if (paymentMethodRepository.findAll().isEmpty()) {
            throw new NotFoundException("Keine Zahlungsmethoden zum Löschen");
        }

        paymentMethodRepository.deleteAll();
    }
    private PaymentMethodDTO convertToDTO(PaymentMethod paymentMethod) {
        return new PaymentMethodDTO(paymentMethod.getCafePaymentMethods());
    }
}