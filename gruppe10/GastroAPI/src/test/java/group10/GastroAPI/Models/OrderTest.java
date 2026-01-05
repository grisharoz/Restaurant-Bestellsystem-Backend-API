package group10.GastroAPI.Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class OrderTest {
   Order order;

   @Test
   void testEmptyConstructor(){
      order = new Order();

      assertNotNull(order);
      assertNotNull(order.getDishes());
      assertTrue(order.getDishes().isEmpty());
   }

   @Test
   void testFullConstructor(){
      Dish dish1 = new Dish("Suppe", 200, 10, "Suppen");
      List<Dish> dishes = new ArrayList<>();
      dishes.add(dish1);
      order = new Order(PayMethod.CARD, dishes);

      assertTrue(order.getDishes().contains(dish1));
      assertEquals(dishes, order.getDishes());
   }

   @Test
   void testValidationSetDishes(){
      order = new Order();
      assertThrows(IllegalArgumentException.class, () -> order.setDishes(null));
      assertThrows(IllegalArgumentException.class, () -> order.setDishes(new ArrayList<>()));
   }

   @Test
   void testValidationSetOrderStatus(){
      order = new Order();
      assertThrows(IllegalArgumentException.class, () -> order.setOrderStatus(null));
   }

   @Test
   void testValidationSetPayMethod(){
      order = new Order();
      assertThrows(IllegalArgumentException.class, () -> order.setPayMethod(null));
   }

   @Test
   void testAddDish(){
      order = new Order();
      Dish dish1 = new Dish("Pizza", 500, 12, "Pizzen");

      order.addDish(dish1);

      assertTrue(order.getDishes().contains(dish1));
      assertEquals(1, order.getDishes().size());
   }

   @Test
   public void testEditMethod(){
      order = new Order();
      Dish dish1 = new Dish("Pizza", 500, 12, "Pizzen");
      List<Dish> dishes = new ArrayList<>();
      dishes.add(dish1);

      Order order1 = new Order(PayMethod.CARD, dishes);

      order.edit(order1);

      assertEquals(OrderStatus.PLACED, order.getOrderStatus());
      assertEquals(PayMethod.CARD, order.getPayMethod());
      assertEquals(dishes, order.getDishes());
      assertTrue(order.getDishes().size() == 1);
   }

   @Test
   public void statusSetterCheck(){
      order = new Order();
      Dish dish1 = new Dish("Pizza2", 509, 22, "Pizzen");
      List<Dish> dishes = List.of(new Dish("Pizza", 500, 12, "Pizzen"), dish1);

      Order order1 = new Order(PayMethod.CASH, dishes);

      order.edit(order1);

      order.setOrderStatusInProgress();
      assertEquals(OrderStatus.IN_PROGRESS, order.getOrderStatus());

      order.setOrderStatusCompleted();
      assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());

      order.setOrderStatusPaid();
      assertEquals(OrderStatus.PAID, order.getOrderStatus());
   }
}
