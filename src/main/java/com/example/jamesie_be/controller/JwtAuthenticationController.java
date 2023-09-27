package com.example.jamesie_be.controller;

import com.example.jamesie_be.config.JwtTokenUtil;
import com.example.jamesie_be.model.Customers;
import com.example.jamesie_be.model.JwtRequest;
import com.example.jamesie_be.model.JwtResponse;
import com.example.jamesie_be.model.ShoppingCart;
import com.example.jamesie_be.service.ICustomerService;
import com.example.jamesie_be.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://10.10.10.84:3001" , allowedHeaders = "*", allowCredentials = "true")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private IShoppingCartService iShoppingCartService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletRequest httpServletRequest) {
        try {
            Authentication authentication =

                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            final UserDetails userDetails = jwtInMemoryUserDetailsService
                    .loadUserByUsername(authenticationRequest.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);
            GrantedAuthority grantedAuthority = userDetails.getAuthorities().stream().findFirst().orElse(null);
            HttpSession session = httpServletRequest.getSession();

            if (session.getAttribute("cart") != null){
                List<ShoppingCart> shoppingCartList = (List<ShoppingCart>) session.getAttribute("cart");
                Customers customers = iCustomerService.findByUsername(userDetails.getUsername());
                iShoppingCartService.deleteByCustomer(customers);
                for (int i = 0; i < shoppingCartList.size(); i++) {
                    ShoppingCart shoppingCart = new ShoppingCart(customers, shoppingCartList.get(i).getProducts(), shoppingCartList.get(i).getAmount());
                    iShoppingCartService.save(shoppingCart);
                }
                session.removeAttribute("cart");
                return ResponseEntity.ok(new JwtResponse(token, authenticationRequest.getUsername(), grantedAuthority != null ? grantedAuthority.getAuthority() : null));


            }
            else {

                return ResponseEntity.ok(new JwtResponse(token, authenticationRequest.getUsername(), grantedAuthority != null ? grantedAuthority.getAuthority() : null));

            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Wrong username or password");
        }
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
