package com.example.managertask.client;

import com.example.managertask.client.invite.Invitation;
import com.example.managertask.client.invite.InvitationRepository;
import com.example.managertask.family.Family;
import com.example.managertask.family.FamilyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.*;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;


public class ClientServiceTest {
    @Mock
    private SecurityContext securityContext;
    @Mock
    private FamilyRepository familyRepository;
    @Mock
    private Authentication authentication;
    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientDtoMapper clientDtoMapper;

    @Spy
    @InjectMocks
    ClientService clientService;

    ClientDto dto;
    Client client;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        client = new Client();
        dto = new ClientDto();

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    public void clientServiceShouldReturnClientDto(){

        //given

        client.setId(1L);
        client.setFirstName("Someone");

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
    @Test
    public void shouldReturnUserDtoOnlyIfItMatchesJwtSubject(){

        //given
        Client client = new Client();
        client.setId(1L);
        client.setEmail("someone@mail.com");
        dto.setId(1L);


        when(clientService.getCurrentUsername()).thenReturn("someone@mail.com");

        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        Mockito.when(clientDtoMapper.map(client)).thenReturn(dto);

        //when
        Optional<ClientDto> userForCurrentUser = clientService.getUserForCurrentUser(1L);

        //then

      assertThat(userForCurrentUser.get().getId(), equalTo(1L));
      assertThat(userForCurrentUser, not(Optional.empty()));

    }

    @Test
    public void shouldThrowExceptionIfJwtNotMatches(){

        //given
        client.setId(1L);
        client.setEmail("lol@mail.com");
        dto.setId(1L);


        when(clientService.getCurrentUsername()).thenReturn("someone@mail.com");

        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        Mockito.when(clientDtoMapper.map(client)).thenReturn(dto);

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> clientService.getUserForCurrentUser(1L).orElseThrow());

    }
    @Test
    public void shouldThrowExceptionWhenUpdatingWrongUser(){

        //given
        client.setEmail("someone@mail.com");

        when(clientService.getCurrentUsername()).thenReturn("someoneElse@mail.com");
        Mockito.when(clientDtoMapper.map(dto)).thenReturn(client);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> clientService.updateUser(dto));
    }

    @Test
    public void shouldCallSaveMethodWhenSubjectMatches(){
        //given
        client.setEmail("someone@mail.com");

        when(clientService.getCurrentUsername()).thenReturn("someone@mail.com");
        Mockito.when(clientDtoMapper.map(dto)).thenReturn(client);

        //when
        clientService.updateUser(dto);
        //then
        verify(clientRepository, times(1)).save(client);

    }

    @Test
    public void doNotCallSaveInvitationWhenItAlreadyExists(){

        //given

        Client invitee = new Client();
        invitee.setFirstName("Ty");
        ClientDto inviteeDto = new ClientDto();
        Family family = new Family();
        client.setFamily(family);
        family.setId(1L);
        Invitation invitation = new Invitation();

        when(clientDtoMapper.map(dto)).thenReturn(client);
        when(clientDtoMapper.map(inviteeDto)).thenReturn(invitee);
        when(familyRepository.findById(1L)).thenReturn(Optional.of(family));
        when(invitationRepository.findByFamilyAndInvitee(family,invitee)).thenReturn(Optional.of(invitation));

        //when
        clientService.inviteClientToFamily(dto, inviteeDto);

        //then
        verify(invitationRepository, times(0)).save(any());

    }
    @Test
    public void doCallSaveInvitationWhenItNotExists(){

        //given

        Client invitee = new Client();
        invitee.setFirstName("Ty");
        ClientDto inviteeDto = new ClientDto();
        Family family = new Family();
        client.setFamily(family);
        family.setId(1L);


        when(clientDtoMapper.map(dto)).thenReturn(client);
        when(clientDtoMapper.map(inviteeDto)).thenReturn(invitee);
        when(familyRepository.findById(1L)).thenReturn(Optional.of(family));
        when(invitationRepository.findByFamilyAndInvitee(family,invitee)).thenReturn(Optional.empty());

        //when
        clientService.inviteClientToFamily(dto, inviteeDto);

        //then
        verify(invitationRepository, times(1)).save(any());

    }
    @Test
    public void shouldDeleteInvitationAfterJoining(){

        //given
        Family family = new Family();
        Invitation invitation = new Invitation();
        invitation.setFamily(family);

        when(familyRepository.findById(1L)).thenReturn(Optional.of(family));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(invitationRepository.findByFamilyAndInvitee(family, client)).thenReturn(Optional.of(invitation));

        //when
        clientService.joinFamily(1L, 1L);

        //then
        assertThat(client.getFamily(), equalTo(family));
        verify(clientRepository, times(1)).save(any());
        verify(invitationRepository, times(1)).delete(any());

    }


}
