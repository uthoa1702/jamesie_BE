package com.example.jamesie_be.controller;

import com.example.jamesie_be.config.JwtTokenUtil;
import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.Products;
import com.example.jamesie_be.model.ShoppingCart;
import com.example.jamesie_be.service.ICustomerService;
import com.example.jamesie_be.service.IProductService;
import com.example.jamesie_be.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/shopping")
@CrossOrigin(origins = {"http://10.10.10.84:3001" } , allowedHeaders = "*", allowCredentials = "true")
public class ShoppingCartRestController {
    @Autowired
    private IShoppingCartService iShoppingCartService;
    @Autowired
    private IProductService iProductService;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Transactional
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    public ResponseEntity<?> addToCart(@RequestParam("size") Long sizeId,
                                       @RequestParam("productName") String productName,

                                       @RequestParam("quantity") Integer quantity) {
        UserDetails userDetails = getUserDetails();
        try {
            Products products = iProductService.findByNameAndSize(productName, sizeId);
            Customers customers = iCustomerService.findByUsername(userDetails.getUsername());
            return iShoppingCartService.add(sizeId, products, customers, quantity);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
        }

    }

    private static UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    @GetMapping("/myCart")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getMyCart() {
        List<ShoppingCart> shoppingCartList = iShoppingCartService.findByUsername(getUserDetails().getUsername());


        return new ResponseEntity<>(shoppingCartList, HttpStatus.OK);

    }

    @GetMapping("/total")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getTotal() {
        Double total = iShoppingCartService.getToTal(getUserDetails().getUsername());
        return new ResponseEntity<>(total, HttpStatus.OK);

    }

    @PostMapping("/changeQuantity")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    public void change(
            @RequestParam("productId") Long productId,
            @RequestParam("addOrMinus") String addOrMinus, HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        String token = header.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        iShoppingCartService.changeQuantity(username, productId, addOrMinus);

    }

    @PostMapping("/addToCartSession")
    public ResponseEntity<?> saveCartSession(
            @RequestParam("size") Long sizeId,
            @RequestParam("productName") String productName,

            @RequestParam("quantity") Integer quantity
            , HttpServletRequest httpServletRequest) {
        Products products = iProductService.findByNameAndSize(productName, sizeId);
        ShoppingCart shoppingCart = new ShoppingCart(products, quantity);
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        HttpSession session = httpServletRequest.getSession();
        session.setMaxInactiveInterval(1800); // set thoi gian song cua seesion tinh bang second
        if (session.getAttribute("cart") != null) {
            shoppingCartList = (List<ShoppingCart>) session.getAttribute("cart");
            int count = 0;
            for (int i = 0; i < shoppingCartList.size(); i++) {
                if (Objects.equals(shoppingCart.getProducts().getId(), shoppingCartList.get(i).getProducts().getId())) {
                    shoppingCartList.get(i).setAmount(shoppingCartList.get(i).getAmount() + shoppingCart.getAmount());
                    count++;
                }
            }
            if (count == 0) {
                shoppingCartList.add(shoppingCart);
            }
        } else {
            session.setAttribute("cart", shoppingCartList);
            shoppingCartList.add(shoppingCart);
        }
        session.setAttribute("cart", shoppingCartList);
        return new ResponseEntity<>(session.getAttribute("cart"), HttpStatus.OK);
    }

    @GetMapping("/listSession")
    public ResponseEntity<?> getList(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        return new ResponseEntity<>(httpSession.getAttribute("cart"), HttpStatus.OK);
    }

    @GetMapping("/totalSession")
    public ResponseEntity<?> getTotalSession(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        List<ShoppingCart> shoppingCartList = (List<ShoppingCart>) httpSession.getAttribute("cart");
        double sum = 0.0;

        if (shoppingCartList.size() > 0) {
            for (int i = 0; i < shoppingCartList.size(); i++) {
                double a = shoppingCartList.get(i).getAmount() * shoppingCartList.get(i).getProducts().getPrice();
                sum = sum + a;
            }
            return new ResponseEntity<>(sum, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/changeQuantitySession")
    @Transactional
    public void changeQuantity(@RequestParam("productId") Long productId,
                               @RequestParam("addOrMinus") String addOrMinus,
                               HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        List<ShoppingCart> shoppingCartList = (List<ShoppingCart>) httpSession.getAttribute("cart");
        for (int i = 0; i < shoppingCartList.size(); i++) {
            if (Objects.equals(shoppingCartList.get(i).getProducts().getId(), productId)) {
                switch (addOrMinus) {
                    case "plus": {
                        if (shoppingCartList.get(i).getProducts().getAmount() > shoppingCartList.get(i).getAmount()) {
                            int amount = shoppingCartList.get(i).getAmount() + 1;
                            shoppingCartList.get(i).setAmount(amount);
                            break;
                        }
                        break;
                    }
                    case "minus": {
                        if (shoppingCartList.get(i).getAmount() == 1) {
                            shoppingCartList.remove(i);
                            break;
                        }
                        int amount = shoppingCartList.get(i).getAmount() - 1;
                        shoppingCartList.get(i).setAmount(amount);
                        break;
                    }
                    default:
                }
            }
        }

    }

    @PostMapping("/SessionDelete")
    public void delete(@RequestParam("productId") Long productId, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        List<ShoppingCart> shoppingCartList = (List<ShoppingCart>) httpSession.getAttribute("cart");
        for (int i = 0; i < shoppingCartList.size(); i++) {
            if (shoppingCartList.get(i).getProducts().getId() == productId) {
                shoppingCartList.remove(i);
                break;
            }
        }

    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    public void delete(@RequestParam("productId") Long productId) {
        iShoppingCartService.deleteProductInCart(getUserDetails().getUsername(),productId);
    }
}
