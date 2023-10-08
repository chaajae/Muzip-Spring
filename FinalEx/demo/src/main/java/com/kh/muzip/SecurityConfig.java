package com.kh.muzip;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic().disable();
		http.csrf().disable(); // 외부 POST 요청을 받아야하니 csrf는 꺼준다.
		http.cors(); // ⭐ CORS를 커스텀하려면 이렇게
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		//http.authorizeHttpRequests().requestMatchers("/**").permitAll().anyRequest().authenticated();
		
		return http.build();
	}
	
	@Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true); // `//` 문자열 허용
        return firewall;
    }

}
