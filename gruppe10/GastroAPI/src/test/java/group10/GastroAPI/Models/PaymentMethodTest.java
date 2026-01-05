package group10.GastroAPI.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodTest {

    private PaymentMethod paymentMethod;

    @BeforeEach
    void setUp() {
        paymentMethod = new PaymentMethod();
    }

    @Test
    void testGetId_ShouldReturnSingletonId() {
        assertEquals(PaymentMethod.SINGLETON_ID, paymentMethod.getId());
    }

    @Test
    void testSetCafePaymentMethods_WithValidList_ShouldSetMethods() {
        List<PayMethod> methods = Arrays.asList(PayMethod.CARD, PayMethod.CASH);

        paymentMethod.setCafePaymentMethods(methods);

        assertEquals(2, paymentMethod.getCafePaymentMethods().size());
        assertTrue(paymentMethod.getCafePaymentMethods().contains(PayMethod.CARD));
        assertTrue(paymentMethod.getCafePaymentMethods().contains(PayMethod.CASH));
    }

    @Test
    void testSetCafePaymentMethods_WithNull_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> paymentMethod.setCafePaymentMethods(null));

        assertEquals("Die Liste der Zahlungsmethoden darf nicht null oder leer sein",
                exception.getMessage());
    }

    @Test
    void testSetCafePaymentMethods_WithEmptyList_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> paymentMethod.setCafePaymentMethods(Collections.emptyList()));

        assertEquals("Die Liste der Zahlungsmethoden darf nicht null oder leer sein",
                exception.getMessage());
    }

    @Test
    void testSetCafePaymentMethods_WithNullInList_ShouldThrowException() {
        List<PayMethod> methods = Arrays.asList(PayMethod.CARD, null, PayMethod.CASH);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> paymentMethod.setCafePaymentMethods(methods));

        assertEquals("Die Liste der Zahlungsmethoden darf keine null-Werte enthalten",
                exception.getMessage());
    }

    @Test
    void testSetCafePaymentMethods_WithDuplicates_ShouldRemoveDuplicates() {
        List<PayMethod> methods = Arrays.asList(PayMethod.CARD, PayMethod.CASH, PayMethod.CARD);
        paymentMethod.setCafePaymentMethods(methods);
        assertEquals(2, paymentMethod.getCafePaymentMethods().size());
    }

    @Test
    void testIsPaymentMethodAvailable_WhenMethodIsAvailable_ShouldReturnTrue() {
        List<PayMethod> methods = Arrays.asList(PayMethod.CARD, PayMethod.CASH);
        paymentMethod.setCafePaymentMethods(methods);
        assertTrue(paymentMethod.isPaymentMethodAvailable(PayMethod.CARD));
        assertTrue(paymentMethod.isPaymentMethodAvailable(PayMethod.CASH));
    }

    @Test
    void testIsPaymentMethodAvailable_WhenMethodIsNotAvailable_ShouldReturnFalse() {
        List<PayMethod> methods = Arrays.asList(PayMethod.CARD);
        paymentMethod.setCafePaymentMethods(methods);
        PayMethod unavailableMethod = PayMethod.CASH;
        assertFalse(paymentMethod.isPaymentMethodAvailable(unavailableMethod));
    }

    @Test
    void testIsPaymentMethodAvailable_WithEmptyPaymentMethods_ShouldReturnFalse() {
        assertFalse(paymentMethod.isPaymentMethodAvailable(PayMethod.CARD));
    }

    @Test
    void testGetAllAvailableMethods_ShouldReturnAllEnumValues() {
        List<PayMethod> allMethods = PaymentMethod.getAllAvailableMethods();
        assertEquals(PayMethod.values().length, allMethods.size());
        assertTrue(allMethods.containsAll(Arrays.asList(PayMethod.values())));
    }

    @Test
    void testGetAllAvailableMethods_ShouldReturnUnmodifiableList() {
        List<PayMethod> allMethods = PaymentMethod.getAllAvailableMethods();
        assertThrows(UnsupportedOperationException.class,
                () -> allMethods.add(PayMethod.CARD));
    }

    @Test
    void testOrderPreservationInPaymentMethods() {
        List<PayMethod> methods = Arrays.asList(
                PayMethod.CARD,
                PayMethod.CASH
        );
        paymentMethod.setCafePaymentMethods(methods);
        List<PayMethod> returnedMethods = paymentMethod.getCafePaymentMethods();
        assertEquals(PayMethod.CARD, returnedMethods.get(0));
        assertEquals(PayMethod.CASH, returnedMethods.get(1));
    }

    @Test
    void testSingletonIdConstant() {
        assertEquals(1L, PaymentMethod.SINGLETON_ID);
    }
}