package me.pepe.Twittir.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import me.pepe.Twittir.Auth.JwtAuthFilter;
import me.pepe.Twittir.Auth.UserAuthenticationEntryPoint;
import me.pepe.Twittir.Auth.UserAuthenticationProvider;
import me.pepe.Twittir.Auth.UsernamePasswordAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
	private final UserAuthenticationProvider userAuthenticationProvider;
	public SecurityConfig(UserAuthenticationEntryPoint userAuthenticationEntryPoint, UserAuthenticationProvider userAuthenticationProvider) {
		this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
		this.userAuthenticationProvider = userAuthenticationProvider;
	}
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.exceptionHandling(handling -> handling.authenticationEntryPoint(userAuthenticationEntryPoint))
				.addFilterBefore(new UsernamePasswordAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
				.addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), UsernamePasswordAuthFilter.class)
				.csrf(csrf -> csrf.disable())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests((requests) -> requests.requestMatchers(HttpMethod.POST, "/api/login", "/api/register").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/**").authenticated()
						.anyRequest().permitAll());
		return http.build();
	}
}