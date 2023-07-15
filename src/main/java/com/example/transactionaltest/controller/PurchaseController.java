package com.example.transactionaltest.controller;

import com.example.transactionaltest.model.Customer;
import com.example.transactionaltest.model.Purchase;
import com.example.transactionaltest.repository.CustomerRepository;
import com.example.transactionaltest.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;

    @PostMapping
    @Transactional
    public void makeOrder(@RequestBody Purchase purchase) {
        test(purchase);
    }

    @Transactional
    public void test(Purchase purchase) {
        purchaseRepository.save(purchase);
        fail();
        updateCustomerBalance(purchase.getCustomerId(), purchase.getPrice());
    }

    private void updateCustomerBalance(long customerId, double price) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new RuntimeException("Not found customer with id: " + customerId));
        customer.setBalance(customer.getBalance() - price);
        customerRepository.save(customer);
    }

    @SneakyThrows
    private void fail() {
        throw new RuntimeException();
    }
}
