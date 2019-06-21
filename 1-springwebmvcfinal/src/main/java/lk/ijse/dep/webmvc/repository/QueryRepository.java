package lk.ijse.dep.webmvc.repository;

import lk.ijse.dep.webmvc.entity.CustomEntity;

import java.util.List;

public interface QueryRepository {

    List<CustomEntity> getOrders();

}
