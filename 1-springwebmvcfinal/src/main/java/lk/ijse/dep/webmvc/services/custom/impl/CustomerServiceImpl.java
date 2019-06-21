package lk.ijse.dep.webmvc.services.custom.impl;

import lk.ijse.dep.webmvc.dto.CustomerDTO;
import lk.ijse.dep.webmvc.entity.Customer;
import lk.ijse.dep.webmvc.repository.CustomerRepository;
import lk.ijse.dep.webmvc.services.custom.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDTO getCustomerById(String id) {

        Customer customer = customerRepository.getOne(id);
        CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress());
        return customerDTO;

    }

    @Override
    public boolean isCustomerExists(String id) {
        return customerRepository.existsById(id);
    }

    public List<CustomerDTO> getAllCustomers()  {

        List<CustomerDTO> customers = customerRepository.findAll().stream().map((Customer customer) -> {
            return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress());
        }).collect(Collectors.toList());

        return customers;
    }


    public String saveCustomer(CustomerDTO dto){
        if (isCustomerExists(dto.getId())){
            throw new RuntimeException("Customer already exits");
        }
        return (customerRepository.save(new Customer(dto.getId(), dto.getName(), dto.getAddress())).getId() + "");

    }

    public void updateCustomer(CustomerDTO dto) {

        customerRepository.save(new Customer(dto.getId(), dto.getName(), dto.getAddress()));

    }

    public void removeCustomer(String id) {

        customerRepository.deleteById(id);

    }

    public int customerCount(){
        return (int) customerRepository.count();
    }

}
