package com.sujith.project.service;

import com.sujith.project.entity.*;

import java.util.*;

public interface AddressService {
    List<Address> findAllAddress();

    Address insert(Address address);


}
