package lk.ijse.dep.webmvc.services.custom.impl;

import lk.ijse.dep.webmvc.dto.CustomDTO;
import lk.ijse.dep.webmvc.dto.OrderDTO;
import lk.ijse.dep.webmvc.dto.OrderDetailDTO;
import lk.ijse.dep.webmvc.entity.*;
import lk.ijse.dep.webmvc.repository.*;
import lk.ijse.dep.webmvc.services.custom.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private QueryRepository queryRepository;

    @Transactional
    public void placeOrder(OrderDTO order) {

        if (isOrderExistById(order.getOrderId())){
            throw new RuntimeException("OrderId is already exist. please change orderId");
        }
        // Find the customer
        Customer customer = customerRepository.getOne(order.getCustomerId());
        // Save the order
        orderRepository.save(new Order(order.getOrderId(), order.getOrderDate(), customer));
        //  Save OrderDetails and Update the Qty.

        if (order.getOrderDetails().size()==0){
            throw new RuntimeException("There is no order details");
        }
        for (OrderDetailDTO dto : order.getOrderDetails()) {
            orderDetailRepository.save(new OrderDetail(dto.getOrderId(), dto.getItemCode(), dto.getQty(), dto.getUnitPrice()));
            // Find the item
            Item item = itemRepository.getOne(dto.getItemCode());
            // Calculate the qty. on hand
            int qty = item.getQtyOnHand() - dto.getQty();
            // Update the new qty.on hand
            item.setQtyOnHand(qty);

        }


    }

    public int generateOrderId() {
        try {
            return orderRepository.getTopOrdersByOrderByIdDesc().getId() + 1;
        } catch (NoResultException e) {
            return 1;
        }
    }

    @Override
    public List<OrderDTO> getAllOrders() {
         return orderRepository.findAll().stream().map(order -> new OrderDTO(order.getId(),order.getDate(),order.getCustomer().getId(),
                order.getOrderDetails().stream().map(orderDetail -> new OrderDetailDTO(orderDetail.getOrderDetailPK().getOrderId(),orderDetail.getOrderDetailPK().getItemCode(),
                        orderDetail.getQty(),orderDetail.getUnitPrice())).collect(Collectors.toList()))).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(int orderId) {
        Order order = orderRepository.getOne(orderId);
        return new OrderDTO(order.getId(),order.getDate(),order.getCustomer().getId(),order.getOrderDetails().stream().map(orderDetail -> new OrderDetailDTO(orderDetail.getOrderDetailPK().getOrderId(),orderDetail.getOrderDetailPK().getItemCode(),orderDetail.getQty(),orderDetail.getUnitPrice()))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean isOrderExistById(int orderId) {
        return orderRepository.existsById(orderId);
    }

    @Override
    public List<CustomDTO> getOrders() {
        List<CustomEntity> orders = queryRepository.getOrders();
        List<CustomDTO> customDTOS = new ArrayList<>();
        for (CustomEntity entity : orders) {
            customDTOS.add(new CustomDTO(entity.getOrderId(),entity.getCustomerId(),entity.getCustomerName(),entity.getOrderDate(),entity.getOrderTotal()));
        }
        return customDTOS;
    }

    public int ordersCount(){
        return (int) orderRepository.count();
    }


}
