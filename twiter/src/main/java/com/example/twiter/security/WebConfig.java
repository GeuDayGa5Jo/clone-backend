//package com.example.twiter.security;
//
//
//import com.example.twiter.security.jwt.JwtFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//
//public class WebConfig implements WebMvcConfigurer {
//
////    private final JwtUtil jwtUtil;
//
//        @Override
//    public void addCorsMappings(CorsRegistry reg) {
//        reg
//                // 전부 허용
//                .addMapping("/**")
//                .allowedOrigins("http://localhost:3000", "http://localhost:3001")
//                .allowedMethods("GET","POST","PUT","DELETE")
//                .exposedHeaders("Authorization","RefreshToken")
//                .allowCredentials(true);
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public WebSecurityCustomizer ignoringCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/boards/");
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors();
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests().antMatchers("/api/**").permitAll()
//                .and().addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
//
//        http.authorizeRequests()
//                .antMatchers("/api/auth/**")
//                .authenticated()
//                .anyRequest()
//                .permitAll()
//                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
//
//
//        return http.build();
//    }
//}
//
//
