package cn.lliiooll.mt.config;

import cn.lliiooll.mt.auth.AuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MTWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService detailsService;
    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(passEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/auth/register").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .formLogin().permitAll()
                .loginProcessingUrl("/auth/login")
                .usernameParameter("userName")
                .passwordParameter("passWord")
                .failureHandler(AuthHandler.INSTANCE)
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .and()
                .csrf().disable()
        ;
    }
}
