package lk.ijse.dep.webmvc.repository;

import lk.ijse.dep.webmvc.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {

}
