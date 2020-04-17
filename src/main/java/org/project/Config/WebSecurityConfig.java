package org.project.Config;

import org.project.Entity.User;
import org.project.Repository.UserRepository;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;


@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .mvcMatchers("/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .logout().permitAll().logoutSuccessUrl("/");
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepository repository){
        return map -> {
            String id = (String)map.get("sub");
            User user = repository.findById(id).orElseGet(() -> {
                User newUser = new User();
                newUser.setId(id);
                newUser.setName((String)map.get("name"));
                newUser.setEmail((String)map.get("email"));

                return newUser;
            });
            user.setLastVisit(LocalDateTime.now());
            return repository.save(user);
        };
    }
}
