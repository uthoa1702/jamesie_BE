package com.example.jamesie_be.service.impl;

import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.Products;
import com.example.jamesie_be.model.ShoppingCart;
import com.example.jamesie_be.repository.IShoppingCartRepository;
import com.example.jamesie_be.service.ICustomerService;
import com.example.jamesie_be.service.IProductService;
import com.example.jamesie_be.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ShoppingCartService implements IShoppingCartService {
    @Autowired
    private IShoppingCartRepository iShoppingCartRepository;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private ICustomerService iCustomerService;

    @Override
    public void save(ShoppingCart shoppingCart) {
        iShoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<ShoppingCart> findAll() {
        return iShoppingCartRepository.findAll();
    }


    @Override
    public ResponseEntity<?> add(Long sizeId, Products products, Customers customers, Integer quantity) {
        if (sizeId != null && products.getName() != null && quantity > 0) {
            if (products.getAmount() >= quantity) {
                ShoppingCart shoppingCart = iShoppingCartRepository.findByCustomersAndProducts(customers, products);

                if (shoppingCart != null) {
                    Integer amount = shoppingCart.getAmount() + quantity;
                    shoppingCart.setAmount(amount);
                    return ResponseEntity.status(HttpStatus.OK).body("Added Successfully");
                }

                ShoppingCart shoppingCartNew = new ShoppingCart(customers, products, quantity);
                iShoppingCartRepository.save(shoppingCartNew);
                return ResponseEntity.status(HttpStatus.OK).body("Added Successfully");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough quantity, please try again.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please check quantity or size");
    }

    @Override
    public List<ShoppingCart> findByUsername(String username) {
        return iShoppingCartRepository.findByUsername(username);
    }

    @Override
    public Double getToTal(String username) {
        List<ShoppingCart> shoppingCartList = iShoppingCartRepository.findByUsername(username);
        Double total = 0.0;
        for (int i = 0; i < shoppingCartList.size(); i++) {
            total = total + (shoppingCartList.get(i).getAmount() * shoppingCartList.get(i).getProducts().getPrice());
        }
        return total;

    }

    @Override
    public void changeQuantity(String username, Long productId, String addOrMinus) {
        Customers customers = iCustomerService.findByUsername(username);
        Products products = iProductService.findById(productId);
        ShoppingCart shoppingCart = iShoppingCartRepository.findByCustomersAndProducts(customers, products);

        switch (addOrMinus) {
            case "plus": {
                Integer amount = shoppingCart.getAmount() + 1;
                if (products.getAmount() >= amount) {
                    shoppingCart.setAmount(amount);
                    iShoppingCartRepository.save(shoppingCart);
                    break;
                }
                break;
            }
            case "minus": {
                Integer amount = shoppingCart.getAmount() - 1;
                if (amount < 1) {
                    iShoppingCartRepository.delete(shoppingCart);
                    break;
                }
                shoppingCart.setAmount(amount);
                iShoppingCartRepository.save(shoppingCart);
                break;
            }
            default: {

            }
        }
    }

    @Override
    public void deleteByCustomer(Customers customers) {
        iShoppingCartRepository.deleteAllByCustomers(customers);
    }

    @Override
    public void deleteProductInCart(String username, Long productId) {
        Customers customers = iCustomerService.findByUsername(username);
        Products products = iProductService.findById(productId);
        iShoppingCartRepository.deleteAllByCustomersAndProducts(customers, products);
    }
}
