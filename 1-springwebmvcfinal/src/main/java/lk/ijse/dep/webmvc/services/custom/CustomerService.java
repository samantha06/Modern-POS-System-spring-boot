package lk.ijse.dep.webmvc.services.custom;

import lk.ijse.dep.webmvc.dto.CustomerDTO;
import lk.ijse.dep.webmvc.services.SuperService;

import java.util.List;

public interface CustomerService extends SuperService {

    public List<CustomerDTO> getAllCustomers();

    public String saveCustomer(CustomerDTO dto);

    public void updateCustomer(CustomerDTO dto);

    public void removeCustomer(String id);

    public CustomerDTO getCustomerById(String id);

    public boolean isCustomerExists(String id);

    public int customerCount();

}
