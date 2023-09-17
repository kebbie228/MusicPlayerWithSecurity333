package org.itstep.config;

import org.itstep.security.ListenerSecurity;
import org.itstep.services.ListenerDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
private final ListenerDetailsService listenerDetailsService;
@Autowired
    public SecurityConfig(ListenerDetailsService listenerDetailsService) {
        this.listenerDetailsService = listenerDetailsService;
    }

@Override
    protected void configure(HttpSecurity http) throws Exception{
http.authorizeRequests()
        //пускаем только на 2 страницы всех пользователей
         .antMatchers("/admin").hasRole("ADMIN")
         .antMatchers("/auth/login","/auth/registration","/error").permitAll()
          //не пускаем на другие страницы
    // когда роли добавляешь то убрать эту строку  .anyRequest().authenticated() на
     //   .antMatchers("/listener/{id}").access("isAuthenticated() and @listenerSecurity.checkUserId(authentication, #id)") // Проверка идентификатора пользователя
        .anyRequest().hasAnyRole("USER","ADMIN")
        .and()
        .formLogin().loginPage("/auth/login")
        .loginProcessingUrl("/process_login")
        .defaultSuccessUrl("/hello",true)
        .failureUrl("/auth/login?error")
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/auth/login");
    }

    // Настраивает аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(listenerDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
    return new BCryptPasswordEncoder();
    }
//    @Autowired
//    private ListenerSecurity listenerSecurity;
//
//    @Bean
//    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
//        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
//        handler.setPermissionEvaluator(new DenyAllPermissionEvaluator());
//        return handler;
//    }
}