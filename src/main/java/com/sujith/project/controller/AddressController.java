package com.sujith.project.controller;

import com.sujith.project.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController

@RequestMapping("/api")

public class AddressController {
    AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address")
    public List<com.sujith.project.entity.Address> findAddress() {
        return addressService.findAllAddress();
    }


}
