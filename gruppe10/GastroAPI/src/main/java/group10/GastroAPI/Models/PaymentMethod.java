package group10.GastroAPI.Models;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod {

    public static final long SINGLETON_ID = 1L;

    @Id
    private Long id = SINGLETON_ID;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "cafe_payment_methods",
            joinColumns = @JoinColumn(name = "payment_method_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private Set<PayMethod> cafePaymentMethods = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public List<PayMethod> getCafePaymentMethods() {
        return new ArrayList<>(cafePaymentMethods);
    }

    public void setCafePaymentMethods(Collection<PayMethod> paymentMethods) {
        if (paymentMethods == null || paymentMethods.isEmpty()) {
            throw new IllegalArgumentException("Die Liste der Zahlungsmethoden darf nicht null oder leer sein");
        }
        if (paymentMethods.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Die Liste der Zahlungsmethoden darf keine null-Werte enthalten");
        }

        cafePaymentMethods.clear();
        cafePaymentMethods.addAll(paymentMethods);
    }

    public boolean isPaymentMethodAvailable(PayMethod method) {
        return cafePaymentMethods.contains(method);
    }

    public static List<PayMethod> getAllAvailableMethods() {
        return Arrays.asList(PayMethod.values());
    }
}