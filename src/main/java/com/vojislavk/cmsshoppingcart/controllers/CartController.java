package com.vojislavk.cmsshoppingcart.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.vojislavk.cmsshoppingcart.models.Cart;
import com.vojislavk.cmsshoppingcart.models.ProductRepository;
import com.vojislavk.cmsshoppingcart.models.data.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@SuppressWarnings("unchecked")
public class CartController {

    @Autowired
    private ProductRepository productRepo;
    
    @GetMapping("/add/{id}")
    public String add(@PathVariable int id, HttpSession session, Model model) {
        
        Product product = productRepo.getOne(id);

        if ( session.getAttribute("cart") == null ) {

            HashMap<Integer, Cart> cart = new HashMap<>();
            cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
            session.setAttribute("cart", cart);
        } else {
            HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart");
            if (cart.containsKey(id)) {
                int qty = cart.get(id).getQuantity();
                cart.put(id, new Cart(id, product.getName(), product.getPrice(), ++qty, product.getImage()));
            } else {
                cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
                session.setAttribute("cart", cart);
            }
        }

        

        return null;
    }
}