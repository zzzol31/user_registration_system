package com.user_registration_system.user_registration_system.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//用來管理專案的設定
@Configuration
public class AppConfig {
    @Bean
    //用來管理Spring Security的權限設定
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                //權限管理，符合/users開頭的內容需要登入才能存取
                //其餘的可以直接通過，無需登入
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/users/**")
                        .authenticated()
                        .anyRequest()
                        .permitAll())
                //使用預設的Spring Security驗證方式
                .httpBasic(Customizer.withDefaults())
                //登入表單的位置在/login
                .formLogin(form -> form.loginPage("/login")
                        //登入的username欄位是email，這邊需要和login.html中欄位的id和name對應，否則找不到username
                        .usernameParameter("email")
                        //登入的password欄位是password，這邊需要和login.html中欄位的id和name對應，否則找不到password
                        .passwordParameter("password")
                        //Spring Security會攔截POST /login的request
                        .loginProcessingUrl("/login")
                        //登入成功後，會轉移到/users
                        .defaultSuccessUrl("/users"))
                //登出
                .logout(logout -> logout
                        //登出的網址是/logout
                        .logoutUrl("/logout")
                        //登出後會轉址到/login?logout
                        .logoutSuccessUrl("/login?logout"));
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}