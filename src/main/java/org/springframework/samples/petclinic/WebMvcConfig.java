/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic;

/**
 *
 * @author ACER
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
}

/* https://stackoverflow.com/questions/47552835/the-type-webmvcconfigureradapter-is-deprecated */
/*
	@Configuration
	public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			return bCryptPasswordEncoder;
		}
	
	}

*/
