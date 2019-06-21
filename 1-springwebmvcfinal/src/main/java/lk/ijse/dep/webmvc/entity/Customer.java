package lk.ijse.dep.webmvc.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NamedQuery(name = "Customer.getCustomer", query = "Select c from Customer c where c.name Like :name And c.address Like :address")
@Entity
public class Customer extends SuperEntity {

    @Id
    private String id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order){
        this.getOrders().add(order);
        order.setCustomer(this);
    }

}
