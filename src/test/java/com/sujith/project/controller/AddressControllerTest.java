package com.sujith.project.controller;


import com.sujith.project.controller_test_files.AddressDtoObject;
import com.sujith.project.exceptions.ApiRequestException;
import com.sujith.project.service.AddressServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
 class AddressControllerTest {


    @Autowired
    AddressController addressController;

    @MockBean
    AddressServiceImpl addressService;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("getAllAddress_valid")
     void getAllAdddress_valid() throws Exception {
        when(addressService.findAllAddress()).thenReturn(AddressDtoObject.getAllAddress());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/address"))
                .andExpect(status().isOk()).andExpect(result -> {
                    AddressDtoObject.getAllAddress();
                });
    }

    @Test
    @DisplayName("getAllAddress_notValid")
     void getAllAdddress_notValid() throws Exception {
        when(addressService.findAllAddress()).thenThrow(new ApiRequestException("No Addresses were found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/address")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No Addresses were found"));
    }


}
