package com.redhat.coolstore.service;

import java.util.List;

import com.redhat.coolstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/services")
public class CatalogEndpoint {

    private CatalogRepository repository;

    @Autowired
    public CatalogEndpoint(CatalogRepository repository) {
        this.repository = repository;
    }

    @ResponseBody
    @GetMapping("/products")
    public List<Product> listAll() {
        return repository.list();
    }
}
