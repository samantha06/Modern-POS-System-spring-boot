package lk.ijse.dep.webmvc.services.custom;

import lk.ijse.dep.webmvc.dto.CustomDTO;
import lk.ijse.dep.webmvc.dto.OrderDTO;
import lk.ijse.dep.webmvc.services.SuperService;

import java.util.List;

public interface OrderService extends SuperService {

    public void placeOrder(OrderDTO order);

    public int generateOrderId();

    public List<OrderDTO> getAllOrders();

    public OrderDTO getOrderById(int orderId);

    public boolean isOrderExistById(int orderId);

    public List<CustomDTO> getOrders();

    public int ordersCount();

}
