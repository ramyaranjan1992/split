package payer.restservices.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import payer.restservices.modal.MerchantBO;
import payer.restservices.repository.MerchantRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	MerchantRepository merchantRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MerchantBO merchant = merchantRepository.findByMerchantId(username)
				.orElseThrow(() -> new UsernameNotFoundException("Merchant Not Found with merchant id: " + username));

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));

		return new org.springframework.security.core.userdetails.User(merchant.getMerchantId(), merchant.getSecretkey(), authorities);
	}

}
