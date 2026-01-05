package group10.GastroAPI.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import group10.GastroAPI.Models.Order;
import group10.GastroAPI.Models.OrderStatus;
import group10.GastroAPI.Models.PayMethod;

public interface OrderRepository extends JpaRepository<Order, Long> {   
   List<Order> findByPayMethod(PayMethod payMethod);

   List<Order> findByOrderStatusAndPayMethod(OrderStatus orderStatus, PayMethod payMethod);

   boolean existsByOrderStatus(OrderStatus orderStatus);

   List<Order> findAllByOrderByCreatedAtAsc();

   List<Order> findByOrderStatusOrderByCreatedAtAsc(OrderStatus orderStatus);
}
