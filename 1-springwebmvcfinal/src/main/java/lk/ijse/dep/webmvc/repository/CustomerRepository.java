package lk.ijse.dep.webmvc.repository;

import lk.ijse.dep.webmvc.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {

//    Customer getTopCustomerByOrOrderByAddressDesc();

//    List<Customer> getCustomersByNameLike(String word);

//    @Query("SELECT c From Customer c Where c.name like ?1")
//    List<Customer> getCustomer(String word);

//    List<Customer> getCustomersByNameLikeAndAddressLikeOrderById(String name, String address);


    List<Customer> getCustomer(@Param("name") String name, @Param("address") String address);
}
