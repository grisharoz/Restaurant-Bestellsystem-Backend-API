package group10.GastroAPI.Services;

import org.springframework.stereotype.Service;

import group10.GastroAPI.Exceptions.NotFoundException;
import group10.GastroAPI.Models.PayMethod;
import group10.GastroAPI.Models.PaymentMethod;
import group10.GastroAPI.Repositories.PaymentMethodRepository;

@Service
public class PaymentMethodService {
   private final PaymentMethodRepository paymentMethodRepository;
   public PaymentMethodService(PaymentMethodRepository paymentMethodRepository){
      this.paymentMethodRepository = paymentMethodRepository;
   }

   public PaymentMethod getPaymentConfig(){
      return paymentMethodRepository.findAll().stream().findFirst().orElseThrow(() -> new NotFoundException("PaymentMethodenen sind nicht gefunden"));
   }

   public void validatePaymentMethodAllowed(PayMethod payMethod){
      if(payMethod == null){
         throw new IllegalArgumentException("PaymentMethode darf nicht null sein");
      }

      PaymentMethod config = getPaymentConfig();

      if(!config.isPaymentMethodAvailable(payMethod)){
         throw new IllegalArgumentException("Ausgewälte PaymentMethod ist nicht vergügbar: " + payMethod);
      }
   }
}
