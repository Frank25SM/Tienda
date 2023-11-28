
package com.Tienda;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import com.Tienda.service.UsuarioDetailsService;

//Solo para prueba de replicación
@Configuration //Definición de clase configuración
public class ProjectConfig implements WebMvcConfigurer{
    // Estos metodos son para la implementación para la internacionalización
    @Bean
    public LocaleResolver localeResolver(){
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.getDefault());
        slr.setLocaleAttributeName("session.current.locale");
        slr.setTimeZoneAttributeName("session.current.timezone");
        return slr;
    }
    
    /* LocaleChangeInterceptor se utiliza para crear un interceptor de cambio de idioma*/
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
    var lci = new LocaleChangeInterceptor();
    lci.setParamName("lang");
    return lci;
    }
    
    //Sobreescribe un metodo que ya existe.
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }
    
    /* Bean para poder acceder a los Messages.properties en código Java*/
    @Bean("messageSource")
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }            
    
    @Override
    //El ViewControllerRegistry se utiliza para simplificar la configuración de los controladores de vista, que solo tienen como objetivo devolver una vista sin procesar ninguna lógica adicional.
    public void addViewControllers(ViewControllerRegistry registry){
    //Si alguien va a la ruta definida en el addViewController, lo redirigue a la pantalla que definamos en setViewName
    registry.addViewController("/").setViewName("index");
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/registro/nuevo").setViewName("/registro/nuevo");
}
    
//    /* El siguiente método se utiliza para completar la clase no es 
//    realmente funcional, la próxima semana se reemplaza con usuarios de BD */
//    @Bean
//    public UserDetailsService users() {
//        UserDetails admin = User.builder()
//                .username("juan")
//                //noop: es para que la contraseña no se encripte
//                .password("{noop}123")
//                .roles("USER", "VENDEDOR", "ADMIN")
//                .build();
//        UserDetails sales = User.builder()
//                .username("rebeca")
//                .password("{noop}456")
//                .roles("USER", "VENDEDOR")
//                .build();
//        UserDetails user = User.builder()
//                .username("pedro")
//                .password("{noop}789")
//                .roles("USER")
//                .build();
//        //Este metodo InMemoryUserDetailsManager espera que se retorne un dato del tipo UserDetailsService. La cantidad de parametros puede ser la cantidad necesaria que se requiera
//        return new InMemoryUserDetailsManager(user, sales, admin);
//    }
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    //Un Bean es un objeto que cuando la aplicación se carga, la aplicacióin lo maneja en memoria
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Asignación de permisos según el rol
        http.authorizeHttpRequests((request) -> request
                //El ** indica que es todo lo que haya después de /errores/...
                .requestMatchers("/", "/index", "/errores/**", "/error", "/error/**", "/carrito/**", "/pruebas/**", "/reportes/**", "/registro/**", 
                        //Estos son las librerías de tipo js y webjars, si esto no se habilita la página levanta sin formato
                        "/js/**", "/webjars/**")
                .permitAll()
                
                .requestMatchers(
                        "/producto/nuevo", "/producto/guardar", "/producto/modificar/**", "/producto/eliminar/**", "/categoria/nuevo", 
                        "/categoria/guardar", "/categoria/modificar/**", "/categoria/eliminar/**",
                        "/usuario/nuevo", "/usuario/guardar", "/usuario/modificar/**", "/usuario/eliminar/**", "/reportes/**")
                .hasRole("ADMIN")
                
                .requestMatchers(
                        "/producto/listado",
                        "/categoria/listado",
                        "/usuario/listado")
                .hasAnyRole("VENDEDOR")
                
                .requestMatchers("/facturar/carrito")
                .hasRole("USER")
                )
                .formLogin(
                        (form) -> form.loginPage("/login").permitAll()
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    
}
