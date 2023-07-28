package com.sujith.project.service;

import com.sujith.project.dao.AddressDao;
import com.sujith.project.entity.Address;
import com.sujith.project.exceptions.ApiRequestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
 class AddressServiceTest {
    static List<Address> validAddresses = new ArrayList<>();
    static Address validAddress = Address.builder().id(1).pin(507115).street("ambedker").city("pvc").build();
    static List<Address> invalidAddresses = new ArrayList<>();
    static Address invalidAddress = new Address();
    @MockBean
    AddressDao addressDao;
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    MockMvc mockMvc;

    @BeforeAll
     static void beforeAll() {

        validAddresses.add(Address.builder().id(1).pin(507115).street("ambedker").city("pvc").build());
        validAddresses.add(Address.builder().id(2).pin(500100).street("hitech city").city("hyderabad").build());
        invalidAddresses.add(new Address());
        invalidAddresses.add(new Address());
    }

    @Test
    @DisplayName("findAllAddress_valid")
     void findAllAddress_valid() {
        when(addressDao.findAllAddress()).thenReturn(validAddresses);
        assertEquals(validAddresses, addressService.findAllAddress());
    }

    @Test
    @DisplayName("findAllAddress_throwApiRequestException")
     void findAllAddress_throwApiRequestException() {
        when(addressDao.findAllAddress()).thenReturn(new ArrayList<>());
        assertThrows(ApiRequestException.class, () -> addressService.findAllAddress());
    }

    @Test
    @DisplayName("insertAddress_valid")
     void insertAddress_valid() {
        when(addressDao.insert(validAddress)).thenReturn(validAddress);
        assertEquals(validAddress, addressService.insert(validAddress));
    }

    @Test
    @DisplayName("insertAddress_throwApiRequestException")
     void insertAddress_throwApiRequestException() {
        when(addressDao.insert(invalidAddress)).thenThrow(ApiRequestException.class);
        assertThrows(ApiRequestException.class, () -> addressService.insert(invalidAddress));
    }
}
