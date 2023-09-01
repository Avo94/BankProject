package org.telran.bankproject.com.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.service.ClientService;

import java.util.Arrays;

@Service
public class ClientDetailService implements UserDetailsService {

    @Autowired
    private ClientService clientService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientService.getByLogin(username);
        if (client == null) throw new UsernameNotFoundException("User with login " + username + " " + "not found");
        return new User(client.getLogin(), client.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("user")));
    }
}