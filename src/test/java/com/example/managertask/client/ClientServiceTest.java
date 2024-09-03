package com.example.managertask.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;


public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientDtoMapper clientDtoMapper;

    @InjectMocks
    ClientService clientService;

    ClientDto dto;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void clientServiceShouldReturnClientDto(){

        //given
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("Someone");

        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        client.setFirstName(client.getFirstName());

        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        Mockito.when(clientDtoMapper.map(client)).thenReturn(dto);

        //when
        Optional<ClientDto> userById = clientService.getUserById(1L);

        //given
        verify(clientRepository, times(1)).findById(1L);
        assertThat(userById.get(), sameInstance(dto));
        assertThat(userById.get().getFirstName(), equalTo(dto.getFirstName()));
    }

    @Test
    public void shouldThrowExceptionIfRepoReturnEmptyOptional(){

        //given
        //when
        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        assertThrows(NoSuchElementException.class, () -> clientService.getUserById(1L).orElseThrow());
    }


}
