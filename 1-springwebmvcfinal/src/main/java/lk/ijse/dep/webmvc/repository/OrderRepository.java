package lk.ijse.dep.webmvc.repository;

import lk.ijse.dep.webmvc.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    //int getLastOrderId()throws Exception;
    Order getTopOrdersByOrderByIdDesc();



}
