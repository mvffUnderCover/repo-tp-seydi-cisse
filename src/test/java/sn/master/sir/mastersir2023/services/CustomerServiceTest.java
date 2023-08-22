package sn.master.sir.mastersir2023.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.master.sir.mastersir2023.models.Customer;
import sn.master.sir.mastersir2023.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {


    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1L, "John"));
        customers.add(new Customer(2L, "Jane"));

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> foundCustomers = customerService.getAllCustomers();

        assertEquals(customers, foundCustomers);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testCreateCustomer() {
        Customer newCustomer = new Customer(3L, "Alice");
        String email = "alice@example.com";
        newCustomer.setEmail(email);

        when(customerRepository.findByEmail(email)).thenReturn(null);
        when(customerRepository.save(newCustomer)).thenReturn(newCustomer);

        Customer createdCustomer = customerService.createCustomer(newCustomer);

        assertEquals(newCustomer, createdCustomer);
        verify(customerRepository, times(1)).findByEmail(email);
        verify(customerRepository, times(1)).save(newCustomer);
    }

    @Test
    void testCreateCustomerWhenAlreadyExists() {
        Customer existingCustomer = new Customer(4L, "Existing");
        String email = "existing@example.com";
        existingCustomer.setEmail(email);

        when(customerRepository.findByEmail(email)).thenReturn(existingCustomer);

        assertThrows(RuntimeException.class, () -> {
            customerService.createCustomer(existingCustomer);
        });

        verify(customerRepository, times(1)).findByEmail(email);
        verify(customerRepository, never()).save(existingCustomer);
    }

    // Add tests for deleteCustomer, updateCustomer, and getCustomerById
    @Test
    void testDeleteCustomer() {
        Customer customer = new Customer(1L, "John");
        customerService.deleteCustomer(customer.getId());
        verify(customerRepository, times(1)).deleteById(customer.getId());
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = new Customer(1L, "John");
        customerService.updateCustomer(customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void getCustomerById(){
        Customer customer = new Customer(1L, "John");
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        Customer foundCustomer = customerService.getCustomerById(customer.getId());
        assertEquals(customer, foundCustomer);
        verify(customerRepository, times(1)).findById(customer.getId());
    }


}
