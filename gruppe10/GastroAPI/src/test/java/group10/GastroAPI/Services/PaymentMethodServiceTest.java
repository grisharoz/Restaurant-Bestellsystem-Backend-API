package group10.GastroAPI.Services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group10.GastroAPI.Exceptions.NotFoundException;
import group10.GastroAPI.Models.PayMethod;
import group10.GastroAPI.Models.PaymentMethod;
import group10.GastroAPI.Repositories.PaymentMethodRepository;

public class PaymentMethodServiceTest {
   private PaymentMethodRepository paymentMethodRepository;
   private PaymentMethodService paymentMethodService;
   
   @BeforeEach
   void setUp(){
      paymentMethodRepository = mock(PaymentMethodRepository.class);
      paymentMethodService = new PaymentMethodService(paymentMethodRepository);
   }

   @Test
   void getPaymentConfig_returnsConfig_whenExists(){
      PaymentMethod config = new PaymentMethod();
      config.setCafePaymentMethods(List.of(PayMethod.CARD));

      when(paymentMethodRepository.findAll()).thenReturn(List.of(config));

      PaymentMethod result = paymentMethodService.getPaymentConfig();

      assertNotNull(result);
      assertTrue(result.isPaymentMethodAvailable(PayMethod.CARD));
      assertFalse(result.isPaymentMethodAvailable(PayMethod.CASH));

      verify(paymentMethodRepository, times(1)).findAll();
   }

   @Test
   void getPaymentConfig_throwsNotFound_whenEmpty(){
      when(paymentMethodRepository.findAll()).thenReturn(List.of());

      assertThrows(NotFoundException.class, () -> paymentMethodService.getPaymentConfig());
      verify(paymentMethodRepository, times(1)).findAll();
   }

   @Test
   void validatePaymentMethodAllowed_throwsIllegalArgument_WhenNull(){
      assertThrows(IllegalArgumentException.class, () -> paymentMethodService.validatePaymentMethodAllowed(null));
      verify(paymentMethodRepository, never()).findAll();
   }

   @Test
   void validatePaymentMethodAllowed_passes_whenAllowed(){
      PaymentMethod config = new PaymentMethod();
      config.setCafePaymentMethods(List.of(PayMethod.CARD));

      when(paymentMethodRepository.findAll()).thenReturn(List.of(config));

      assertDoesNotThrow(() -> paymentMethodService.validatePaymentMethodAllowed(PayMethod.CARD));
      verify(paymentMethodRepository, times(1)).findAll();
   }

   @Test
   void validatePaymentMethodAllowed_throwsIllegalArgument_whenNotAllowed(){
      PaymentMethod config = new PaymentMethod();
      config.setCafePaymentMethods(List.of(PayMethod.CASH));

      when(paymentMethodRepository.findAll()).thenReturn(List.of(config));

      assertThrows(IllegalArgumentException.class, () -> paymentMethodService.validatePaymentMethodAllowed(PayMethod.CARD));

      verify(paymentMethodRepository, times(1)).findAll();
   }

}
