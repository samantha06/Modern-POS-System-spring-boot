package lk.ijse.dep.webmvc.repository;

import lk.ijse.dep.webmvc.entity.OrderDetail;
import lk.ijse.dep.webmvc.entity.OrderDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailPK> {

}
