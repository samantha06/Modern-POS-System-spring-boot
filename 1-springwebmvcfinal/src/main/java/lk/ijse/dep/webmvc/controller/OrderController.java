package lk.ijse.dep.webmvc.controller;

import lk.ijse.dep.webmvc.dto.CustomDTO;
import lk.ijse.dep.webmvc.dto.OrderDTO;
import lk.ijse.dep.webmvc.services.custom.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RequestMapping("/api/v1/orders")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

//    @GetMapping
//    public List<OrderDTO> getAllOrders(){
//         return orderService.getAllOrders();
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Count",String.valueOf(orderService.ordersCount()));
        httpHeaders.setAccessControlAllowHeaders(Arrays.asList("X-Count"));
        httpHeaders.setAccessControlExposeHeaders(Arrays.asList("X-Count"));
        return new ResponseEntity<List<OrderDTO>>(orderService.getAllOrders(),httpHeaders,HttpStatus.OK);
    }

    @GetMapping("{orderId:\\d*}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Integer orderId){
        OrderDTO orderDTO = null;
        if (orderService.isOrderExistById(orderId)){
            orderDTO = orderService.getOrderById(orderId);
        }
        return new ResponseEntity<OrderDTO>(orderDTO,(orderDTO!=null)? HttpStatus.OK: HttpStatus.NOT_FOUND);
    }

    @GetMapping("show")
    public List<CustomDTO> showOrders(){
        return orderService.getOrders();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> placeOrder(@RequestBody OrderDTO orderDTO){
        System.out.println("Working 1");
        System.out.println(new Date());
        System.out.println(orderDTO.getOrderDate());
        System.out.println(orderDTO.getOrderId());
        System.out.println(orderDTO.getCustomerId());
//        System.out.println(orderDTO.getOrderDetails().get(0).getOrderId());
//        System.out.println(orderDTO.getOrderDetails().get(0).getItemCode());
//        System.out.println(orderDTO.getOrderDetails().get(0).getQty());
//        System.out.println(orderDTO.getOrderDetails().get(0).getUnitPrice());

        if (orderDTO.getOrderId()<=0 || orderDTO.getOrderDate() == null || orderDTO.getCustomerId().isEmpty()){
            System.out.println("working 2");
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        for (int i = 0; i < orderDTO.getOrderDetails().size(); i++) {
            if (orderDTO.getOrderDetails().get(i).getOrderId()<=0 || orderDTO.getOrderDetails().get(i).getItemCode().isEmpty() ||
                    orderDTO.getOrderDetails().get(i).getQty()<=0 || orderDTO.getOrderDetails().get(i).getUnitPrice()<=0){
                System.out.println("Working 3");
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        }
        orderService.placeOrder(orderDTO);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }


}
