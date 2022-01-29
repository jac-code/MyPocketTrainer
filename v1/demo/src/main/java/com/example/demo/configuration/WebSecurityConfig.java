package com.example.demo.configuration;

import javax.sql.DataSource;

import com.example.demo.security.handlers.MyAuthenticationSuccessHandler;
import com.example.demo.service.impl.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private DataSource dataSource;

    // Injected custom CustomUserDetails or UserDetailsServiceImpl into our DaoAuthentificationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(encoder);
        return authenticationProvider;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/resources/**", "/static/**","/css/**", "/js/**").permitAll();
        http.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
        
        // The pages do not require login
        http.authorizeRequests().antMatchers("/", "/console/**", "/sign-in", "/sign-up").permitAll();
 
        // for CLIENTS only
        http.authorizeRequests().antMatchers("/clients/client-premium/**").access("hasRole('ROLE_CLIENT_PREMIUM')");
        http.authorizeRequests().antMatchers("/clients/client-free/**").access("hasRole('ROLE_CLIENT_FREE')");
        
        // For PROFESSIONALS only.
        http.authorizeRequests().antMatchers("/professionals/professional-basic/**").access("hasRole('ROLE_PROFESSIONAL_BASIC')");
        http.authorizeRequests().antMatchers("/professionals/professional-business/**").access("hasRole('ROLE_PROFESSIONAL_BUSINESS')");
 
        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will be thrown.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
        
        //Setting HTTPS for my account
        // http.authorizeRequests().and().requiresChannel().anyRequest().requiresSecure();

        // Remember me configurations
        http.authorizeRequests().and()
            .rememberMe().tokenRepository(persistentTokenRepository())
            .userDetailsService(this.userDetailsService)
            .tokenValiditySeconds(2000)
            .useSecureCookie(true);

        // Config for Login Form
        http.authorizeRequests().and().formLogin()
            .loginPage("/sign-in")
            .successHandler(myAuthenticationSuccessHandler())
            .failureUrl("/sign-in?error=true");
                
        // Config for Logout Page --> go back to sign-in page
        http.authorizeRequests().and()
            .logout().logoutUrl("/sign-out")
            // .deleteCookies("")
            .logoutSuccessUrl("/sign-in");
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MyAuthenticationSuccessHandler();
    }

    /**
     * Using this to persist the remember-me token in the database for more secure approach.
     * We are not usin gthe memory based remember-me cookie which is not very secure but saving the token in the
     * DB for better security and secure validation.
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
}