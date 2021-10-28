package payer.restservices.config;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import payer.restservices.filter.JWTAuthorizationFilter;
import payer.restservices.service.AuthEntryPointJwt;
import payer.restservices.service.UserDetailsServiceImpl;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public JWTAuthorizationFilter authenticationJwtTokenFilter() {
		return new JWTAuthorizationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		//return new BCryptPasswordEncoder();
		return new PasswordEncoder() {
	        @Override
	        public String encode(CharSequence charSequence) {
	            return getMd5(charSequence.toString());
	        }

	        @Override
	        public boolean matches(CharSequence charSequence, String s) {
	            return getMd5(charSequence.toString()).equals(s);
	        }
	    };
	}
	public static String getMd5(String input) {
	    try {
	        // Static getInstance method is called with hashing SHA
	        MessageDigest md = MessageDigest.getInstance("MD5");

	        // digest() method called
	        // to calculate message digest of an input
	        // and return array of byte
	        byte[] messageDigest = md.digest(input.getBytes());

	        // Convert byte array into signum representation
	        BigInteger no = new BigInteger(1, messageDigest);

	        // Convert message digest into hex value
	        String hashtext = no.toString(16);

	        while (hashtext.length() < 32) {
	            hashtext = "0" + hashtext;
	        }

	        return hashtext;
	    }

	    // For specifying wrong message digest algorithms
	    catch (NoSuchAlgorithmException e) {
	        System.out.println("Exception thrown"
	                + " for incorrect algorithm: " + e);
	        return null;
	    }
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			//.antMatchers(HttpMethod.POST, "/merchant","/payer/creation","/payer/getPayer").permitAll()
//			.antMatchers(HttpMethod.POST,"/merchant","/payer/creation","/payer/monthlyPayment",
//					"/payer/getPayer","/payer/getAllPayer","/paymentGateway","/payerservices/response").permitAll()
			.antMatchers(HttpMethod.POST,"/merchant","/payer/**","/paymentApi","/mishra","/modelTest",
					"/paymentGateway","/payerservices/response").permitAll()
			.antMatchers(HttpMethod.GET,"/login","/payerCreation","/signOut","/nasir","/form",
					"/merchantDetails","/css/**","/sabPaisa","/monthlyPaymentApi","/getPayerApi","/getAllPayerApi",
					"/getAllPayer","/payer/**","/getEMIDetails","/formTest","/variableTest",
                    "/v2/api-docs",
                    "/swagger-resources/**",
                    "/swagger-ui.html**",
                    "/webjars/**",
                    "/favicon.ico")
            .permitAll()
			.anyRequest().authenticated();
		
	}	
	
}
