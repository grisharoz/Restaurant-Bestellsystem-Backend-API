package group10.GastroAPI.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;

/**
 * Bestellungen in unswerem System
 */
@Entity
@Table(name = "orders")
public class Order {

   /**
    * Spezifischer einzigarteige Code für jede Bestellung
    */
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   /**
    * Zeit, wann die Bestellung erstellt wurde
    */
   @Column(nullable = false, updatable = false)
   private LocalDateTime createdAt;

   /**
    * Liste der Speisen in dieser Bestellung
    */
   @ManyToMany
   private List<Dish> dishes = new ArrayList<>();

   /**
    * Status einer Bestellung
    */
   @Enumerated(EnumType.STRING)
   private OrderStatus orderStatus;

   /**
    * Bezahlmethode einer Bestellung
    */
   @Enumerated(EnumType.STRING)
   private PayMethod payMethod;

   @PrePersist
   public void prePersist(){
      if(this.orderStatus == null){
         this.orderStatus = OrderStatus.PLACED;
      }

      if(this.createdAt == null){
         this.createdAt = LocalDateTime.now();
      }
   }

   /**
    * Defaultkonstruktor, von JPA gefordert.
    * Muss im Code nicht verwendet werden
    */
   protected Order(){};

   /**
    * Ertellung einer neuen Bestellung mit einem Status, einer Bezahlart und Liste der Speisen
    * @param orderStatus ist aktuelle Status der Bestellung
    * @param payMethod wie Bezahlt wird
    * @param dishes eine Liste gewählter Speisen
    * @throws IllegarArgumentException wenn dishes leer oder null sind
    */
   public Order(PayMethod payMethod, List<Dish> dishes){
      this.orderStatus = OrderStatus.PLACED;
      this.payMethod = payMethod;
      this.setDishes(dishes);
   }

   /**
    * liefert die ID
    * @return ID der Bestellung
    */
   public Long getId(){
      return id;
   }

   /**
    * liefert Zeit der Erstellung
    * @return Zeit der Erstellung der Bestellung
    */
   public LocalDateTime getCreatedAt(){
      return createdAt;
   }

   /**
    * Liefert Liste der Speisen
    * @return Liste der Speisen
    */
   public List<Dish> getDishes(){
      return dishes;
   }

   /**
    * Setzt Liste der Speisen für eine Bestellung
    * @param dishes Liste der Speisen
    */
   public void setDishes(List<Dish> dishes){
      if(dishes == null || dishes.size() == 0){
         throw new IllegalArgumentException("Dish darf nicht leer oder null sein");
      }
      this.dishes = dishes;
   }

   /**
    * Liefert Bestellstatus
    * @return Bestellstatus
    */
   public OrderStatus getOrderStatus(){
      return orderStatus;
   }

   /**
    * Setzt Bestellstatus auf in_Bearbeitung
    * @param orderStatus Status einer Bestellung
    * @throws IllagalArgumentException wenn Status null ist
    */
   public void setOrderStatusInProgress(){
      this.orderStatus = OrderStatus.IN_PROGRESS;
   }

   /**
    * Setzt Bestellstatus auf Fertig
    * @param orderStatus Status einer Bestellung
    * @throws IllagalArgumentException wenn Status null ist
    */
   public void setOrderStatusCompleted(){
      this.orderStatus = OrderStatus.COMPLETED;
   }

   /**
    * Setzt Bestellstatus auf Bezahlt
    * @param orderStatus Status einer Bestellung
    * @throws IllagalArgumentException wenn Status null ist
    */
   public void setOrderStatusPaid(){
      this.orderStatus = OrderStatus.PAID;
   }

   /**
    * Setzt Bestellstatus
    * @param orderStatus2 Status einer Bestellung
    * @throws IllagalArgumentException wenn Status null ist
    */
   public void setOrderStatus(OrderStatus orderStatus){
      if(orderStatus == null){
         throw new IllegalArgumentException("Status darf nicht null sein");
      }
      this.orderStatus = orderStatus;
   }

   /**
    * Liefert die Bezahlmethode
    * @return Methode der Bezahlung
    */
   public PayMethod getPayMethod(){
      return payMethod;
   }

   /**
    * Setzt eine Methode der Bezahlung
    * @param payMethod Bezhalmetode
    * @throws IllegalArgumentException wenn Bezhalmethode null ist
    */
   public void setPayMethod(PayMethod payMethod){
      if(payMethod == null){
         throw new IllegalArgumentException("Pay-Methode darf nicht null sein");
      }
      this.payMethod = payMethod;
   }

   /**
    * Speisen in Speiseliste hinzufügen
    * @param dish ist eine zuhinzugügende Speise
    */
   public void addDish(Dish dish){
      if(dish != null){
         this.dishes.add(dish);
      }
   }

  
   /**
    * Übernimmt Werte aus einer anderen Bestellung
    * @param newValues Bestellung mit neuen Werten, darf nict null sein
    */
   public void edit(Order newValues){
      if(newValues == null){
         throw new IllegalArgumentException("newValues darf nicht null sein");
      }

      setOrderStatus(newValues.getOrderStatus());
      setPayMethod(newValues.getPayMethod());
      setDishes(newValues.getDishes());
   }
}
