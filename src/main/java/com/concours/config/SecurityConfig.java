package com.concours.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("candidat")
                .password(passwordEncoder().encode("password"))
                .roles("CANDIDAT", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                
                //les uRls sans authentification du candidats plus les URLS de l'admin
                .antMatchers("/api/user/signIn").permitAll()
                .antMatchers("/api/user/signUp").permitAll()
                .antMatchers("/api/user/reset-password").permitAll()
                //.antMatchers("/api/user/update/{id}").permitAll()
                //.antMatchers("/api/user/update").permitAll()
                .antMatchers("/api/user/{id}").permitAll()
                .antMatchers("/api/user/all").permitAll()
                

                      
                
                .antMatchers("/api/concours/all").permitAll()
                .antMatchers("/api/concours/{id}").permitAll()
                .antMatchers("/api/concours/update/{id}").permitAll()
                .antMatchers("/api/concours/add").permitAll()
                .antMatchers("/api/concours/delete/{id}").permitAll()
                .antMatchers("/api/concours/{id}/candidats").permitAll()
                
                
                
                .antMatchers("/etablissement/all").permitAll()
                .antMatchers("/etablissement/add").permitAll()
                .antMatchers("/etablissement/update").permitAll()
                .antMatchers("/etablissement/{id}").permitAll()
                .antMatchers("/etablissement/update/{id}").permitAll()
                
                
                .antMatchers("/api/action").permitAll()
                .antMatchers("/api/historiquesConcours").permitAll()
                .antMatchers("/api/all").permitAll()
                .antMatchers("/api/actionReclamation").permitAll()
                .antMatchers("/api/historiquesReclamations").permitAll()
                .antMatchers("/api/deleteHistoriqueReclamation/{id}").permitAll()
                .antMatchers("/api/deleteHistoriqueConcours/{id}").permitAll()



                
                
                
                
                .antMatchers("/api/sendEmails/{concoursId}").permitAll()
                .antMatchers("/api/candidates/sexes").permitAll()
                .antMatchers("/api/candidates/ages").permitAll()
                .antMatchers("/api/addCandidat").permitAll()
                .antMatchers("/api/candidats/{id}").permitAll()
                .antMatchers("/api/candidat").permitAll()
                .antMatchers("/api/all").permitAll()
                .antMatchers("/api/diplomes").permitAll()
                .antMatchers("/api/{id}").permitAll()
                .antMatchers("/api/add").permitAll()
                .antMatchers("/api/delete/{id}").permitAll()
                .antMatchers("/api/{id}/diplome").permitAll()
                .antMatchers("/api/{id}").permitAll()
                .antMatchers("/api/successful-candidates").permitAll()
                .antMatchers("/api/reussite/{id}").permitAll()
                
                
                
                .antMatchers("/candidats/{candidatId}/diplomes").permitAll()
                
                
               
                .antMatchers("/api/reclamation/all").permitAll()
                .antMatchers("/api/reclamation/add").permitAll()
                .antMatchers("/api/reclamation/{id}").permitAll()
                .antMatchers("/api/reclamation/delete/{id}").permitAll()

              
                
                .antMatchers("/api/response/answer/{id}").permitAll()
  
                //et tout les autres URLs avec l'authentification du candidat 
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
}
