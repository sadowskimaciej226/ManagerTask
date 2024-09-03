package com.example.managertask.client;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {
    @MockBean
    ClientRepository clientRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldGetStatusOkAfterGetClientMethod(){

        //given
        //Mockito.when(clientRepository.)
    }

}
