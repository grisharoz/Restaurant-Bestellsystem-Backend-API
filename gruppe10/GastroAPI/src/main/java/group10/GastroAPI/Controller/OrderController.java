package group10.GastroAPI.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import group10.GastroAPI.DTO.CreateOrderDto;
import group10.GastroAPI.DTO.OrderDto;
import group10.GastroAPI.Exceptions.NotFoundException;
import group10.GastroAPI.Models.Dish;
import group10.GastroAPI.Models.Order;
import group10.GastroAPI.Models.OrderStatus;
import group10.GastroAPI.Models.PayMethod;
import group10.GastroAPI.Repositories.DishRepository;
import group10.GastroAPI.Repositories.OrderRepository;
import group10.GastroAPI.Services.PaymentMethodService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
   private final OrderRepository orderRepository;
   private final DishRepository dishRepository;
   private final PaymentMethodService paymentMethodService;
   public OrderController(OrderRepository orderRepository, DishRepository dishRepository, PaymentMethodService paymentMethodService){
      this.orderRepository = orderRepository;
      this.dishRepository = dishRepository;   
      this.paymentMethodService = paymentMethodService;
   }

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public OrderDto addOrder(@Valid @RequestBody CreateOrderDto dto){
      paymentMethodService.validatePaymentMethodAllowed(dto.payMethod());

      List<Dish> dishes = new ArrayList<>();

      for(Long id : dto.dishIds()){
         Dish dish = dishRepository.findById(id).orElseThrow(() -> new NotFoundException("Dish mit id: " + id + " nicht gefunden"));

         dishes.add(dish);
      }

      Order order = new Order(dto.payMethod(), dishes);
      
      Order savedOrder = this.orderRepository.save(order);

      return toDto(savedOrder);
   }

   @GetMapping
   public List<OrderDto> getAll(){
      List<Order> orders = orderRepository.findAllByOrderByCreatedAtAsc();
      if(orders.isEmpty()){
         throw new NotFoundException("Es gibt keine Orders");
      }

      List<OrderDto> dtos = new ArrayList<>(orders.size());

      for(Order order : orders){
         dtos.add(toDto(order));
      }

      return dtos;
   }

   @PutMapping("/{id}")
   public OrderDto putMethodName(@PathVariable("id") Long id, @Valid @RequestBody CreateOrderDto dto){

      if(id == null || id <= 0){
         throw new IllegalArgumentException("Gültige id ist nicht vorhanden");
      }

      paymentMethodService.validatePaymentMethodAllowed(dto.payMethod());

      List<Dish> dishes = new ArrayList<>(dto.dishIds().size());

      for(Long dishId : dto.dishIds()){
         dishes.add(dishRepository.findById(dishId).orElseThrow(() -> new NotFoundException("Dish mit id: " + dishId + " nicht gefunden")));
      }

      Order oldOrder = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order nicht gefunden"));


      oldOrder.setPayMethod(dto.payMethod());
      oldOrder.setDishes(dishes);

      Order newOrder = orderRepository.save(oldOrder);

      OrderDto result = toDto(newOrder);

      return result;
   }

   @GetMapping("/{id}")
   public OrderDto getById(@PathVariable("id") Long id){
      if(id == null || id <= 0){
         throw new IllegalArgumentException("id darf nicht gleich null oder kleiner 0 sein");
      }
      Order order = this.orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order nicht gefunden"));

      OrderDto dto = toDto(order);

      return dto;
   }

   @DeleteMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteById(@PathVariable("id") Long id){
      if(id == null || id <= 0){
         throw new IllegalArgumentException("id darf nicht gleich null oder kleiner 0 sein");
      }

      if(!orderRepository.existsById(id)){
         throw new NotFoundException("Order nicht gefunden");
      }

      this.orderRepository.deleteById(id);
   }

   @DeleteMapping
   public void deleteAll(){
      if(orderRepository.count() == 0){
         throw new NotFoundException("Keine Orders um zu löschen");
      }
      orderRepository.deleteAll();
   }

   @GetMapping("/status/{status}")
   public List<OrderDto> getByStatus(@PathVariable("status") OrderStatus status){
      if(status == null){
         throw new IllegalArgumentException("orderStatus darf nicht null sein");
      }

      List<Order> orders = orderRepository.findByOrderStatusOrderByCreatedAtAsc(status);

      if(orders.isEmpty()){
         throw new NotFoundException("Orders mit solchem Status nicht gefunden");
      }

      List<OrderDto> dtos = new ArrayList<>(orders.size());

      for(Order order : orders){
         dtos.add(toDto(order));
      }

      return dtos;
   }

   @GetMapping("/paymentmethod/{paymethod}")
   public List<OrderDto> getByPaymentMethod(@PathVariable("paymethod") PayMethod payMethod){
      if(payMethod == null){
         throw new IllegalArgumentException("payMethod darf nicht null sein");
      }

      List<Order> orders = orderRepository.findByPayMethod(payMethod);
      List<OrderDto> dtos = new ArrayList<>(orders.size());

      for(Order order : orders){
         dtos.add(toDto(order));
      }

      return dtos;
   }

   @PutMapping("/{id}/status/{status}")
   public OrderDto updateStatus(@PathVariable("id") Long id, @PathVariable("status") OrderStatus orderStatus){
      if(id == null || id <= 0 || orderStatus == null){
         throw new IllegalArgumentException("id und orderStatus dürfen nicht 0, kleiner 0 oder null sein");
      }
      
      Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Keine Bestellung mit dieser Nummer gefunden"));

      order.setOrderStatus(orderStatus);

      Order result = orderRepository.save(order);

      return toDto(result);
   }

   private OrderDto toDto(Order order){
      return new OrderDto(order.getId(), order.getPayMethod(), order.getOrderStatus(), order.getDishes().stream().map(Dish::getId).toList(), order.getCreatedAt());
   }
}
