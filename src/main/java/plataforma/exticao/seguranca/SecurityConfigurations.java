package plataforma.exticao.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.http.HttpMethod;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private DetalhesUser detalhesUser;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Injetando o encoder de outra classe


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

   // @Bean
   // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
   //     return http
        //        .csrf(csrf -> csrf.disable())
           //     .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           //     .authorizeHttpRequests(auth -> auth
                        // Rotas abertas (sem autenticação)
                      //  .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                     //   .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    //    .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        // Rotas públicas para consulta (pode ser liberado para todos ou só para autenticados)
                      //  .requestMatchers(HttpMethod.GET, "/api/**").permitAll() // ou .authenticated() se quiser só para usuários logados
                        /*.requestMatchers(HttpMethod.GET, "/api/denuncias").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/quizzes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/perguntas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/respostas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/comentarios/**").permitAll()
*/
                        // Rotas que só usuários autenticados podem acessar (USER e ADMIN)
                       /* .requestMatchers(HttpMethod.POST, "/api/especies/registrar-multipart").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/denuncias").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/comentarios").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/quizzes/criar").hasRole( "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/perguntas/criar").hasRole( "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/respostas/criar").hasRole( "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/{id}").hasRole( "ADMIN")*/
                        // Você pode ajustar os deletes e atualizações para ser só do dono do recurso, se quiser mais segurança

                        // Rotas restritas só para ADMIN
                        //.requestMatchers(HttpMethod.PUT, "/api/especies/**/aprovar").hasRole("ADMIN")
                       // .requestMatchers(HttpMethod.PUT, "/api/denuncias/**/aprovar").hasRole("ADMIN")
                        // Pode restringir outras ações administrativas aqui também
                        // Liberar todas as outras rotas temporariamente para teste
                        //.anyRequest().permitAll()
                    //    .anyRequest().authenticated()
             //   )
           //     .authenticationProvider(authenticationProvider())
           //     .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
           //     .build();
   // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // <--- Libera tudo!
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(detalhesUser);
        provider.setPasswordEncoder(passwordEncoder);  // Usa o bean injetado
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
