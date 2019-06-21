package lk.ijse.dep.webmvc.repository.impl;

import lk.ijse.dep.webmvc.entity.CustomEntity;
import lk.ijse.dep.webmvc.repository.QueryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class QueryRepositoryImpl implements QueryRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public List<CustomEntity> getOrders(){
        List<Object[]> list = entityManager.createNativeQuery("SELECT o.id, o.customerId, c.name, o.date, SUM(OD.qty * OD.unitPrice) AS Total FROM `Order` o INNER JOIN\n" +
                "  OrderDetail OD ON o.id = OD.orderId INNER JOIN customer c ON o.customerId = c.id GROUP BY o.id").getResultList();
       // "SELECT o.id,o.customer_Id,o.date,sum(i.qty*i.unitPrice) from orders o INNER join itemdetail i where o.id=i.orderId and o.id like ?1 group by o.id"
        List<CustomEntity> al = new ArrayList<CustomEntity>();

        for (Object[] objects : list) {
            al.add(new CustomEntity((Integer) objects[0],(Integer) objects[1], (String) objects[2],  (Date) objects[3], ((BigDecimal) objects[4]).doubleValue()));
            System.out.println((Date)objects[3]);
        }
        return al;
    }


}
