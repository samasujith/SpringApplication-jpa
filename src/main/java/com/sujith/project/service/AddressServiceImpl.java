package com.sujith.project.service;

import com.sujith.project.dao.*;
import com.sujith.project.entity.*;
import com.sujith.project.exceptions.*;
import jakarta.transaction.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressDao addressDao;

    @Autowired
    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public List<Address> findAllAddress() {
        List<Address> addresses = addressDao.findAllAddress();
        if (addresses.isEmpty()) {
            throw new ApiRequestException("No Addresses were found");
        } else {
            return addresses;
        }
    }

    @Override
    @Transactional
    public Address insert(Address address) {
        return addressDao.insert(address);
    }

}
