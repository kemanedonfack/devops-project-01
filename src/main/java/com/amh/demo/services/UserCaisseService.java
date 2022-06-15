package com.amh.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amh.demo.entities.GestionnaireCaisse;
import com.amh.demo.entities.UserPincipal;
import com.amh.demo.repository.GestionnaireCaisseRepository;

@Service
public class UserCaisseService implements UserDetailsService {
	
	@Autowired
	GestionnaireCaisseRepository gestionnaireCaisseRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		GestionnaireCaisse user=gestionnaireCaisseRepository.findByUtilisateur(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		return new UserPincipal(user);
	}

}
