package com.example.managertask.client;

import com.example.managertask.client.invite.Invitation;
import com.example.managertask.family.Family;
import com.example.managertask.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "client")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name can't be empty")
    private String firstName;
    @NotBlank(message = "Name can't be empty")
    private String lastName;
    @Email
    private String email;
    @NotBlank(message = "password can't be empty")
    private String password;
    @Enumerated(EnumType.STRING)
    @NotBlank
    private Role role;
    @PositiveOrZero
    private int points;
    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;
    @OneToMany(mappedBy = "client")
    private List<Task> tasks = new ArrayList<>();
    @OneToMany(mappedBy = "inviter")
    private Set<Invitation> sentInvitations;

    @OneToMany(mappedBy = "invitee")
    private Set<Invitation> receivedInvitations;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
