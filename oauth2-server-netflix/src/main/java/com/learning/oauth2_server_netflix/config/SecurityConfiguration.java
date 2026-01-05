package com.learning.oauth2_server_netflix.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception{
        http
                .oauth2AuthorizationServer((authorizationServer) -> {
                    http.securityMatcher(authorizationServer.getEndpointsMatcher());
                    authorizationServer.oidc(Customizer.withDefaults());
                })
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                );

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/css/**").permitAll()
                    .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", false)
                        .failureUrl("/login?error=true")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        UserDetails user1 = User.withUsername("stutya")
                .password("user@123")
                .authorities("ROLE_USER")
                .build();

        UserDetails user2 = User.withUsername("adhrit")
                .password("user@123")
                .authorities("ROLE_USER", "ROLE_ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ return NoOpPasswordEncoder.getInstance();}

    @Bean
    public RegisteredClientRepository registeredClientRepository(){

        RegisteredClient ccFlowClient = RegisteredClient.withId(String.valueOf(UUID.randomUUID()))
                .clientId("cc_flow_clientID")
                .clientSecret("cc_flow_secret")
                .clientAuthenticationMethod (ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod (ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType (AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://localhost:8910") /* No app on this port */
                .scope (OidcScopes.OPENID)
                .tokenSettings(
                        TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(30)).build()
                )
                .clientSettings(
                        ClientSettings.builder().requireProofKey(false).build()
                )
                .build();

        /* Client configuration for PKCE Flow */
        RegisteredClient publicClient = RegisteredClient.withId(String.valueOf(UUID.randomUUID()))
                .clientId("pkce_flow_clientID")
                .clientSecret("pkce_flow_secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://localhost:8920") /* No app on this port */
                .scope (OidcScopes.OPENID)
                .tokenSettings(
                        TokenSettings.builder().accessTokenTimeToLive (Duration.ofMinutes(30)).build()
                )
                .clientSettings(
                        ClientSettings.builder().requireProofKey(true).build()
                )
                .build();

        /* Proxy Client with grant type auth code flow */
        RegisteredClient proxyClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client")
                .clientSecret("secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:8080/login/oauth2/code/proxy-client")
                .scope (OidcScopes.OPENID)
                .clientSettings(
                        ClientSettings.builder()
                                .requireProofKey(false)
                                .requireAuthorizationConsent(true)
                                .build()
                )
                .build();

        return new InMemoryRegisteredClientRepository(ccFlowClient, publicClient, proxyClient);
    }
    @Bean
    public AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);

        KeyPair keys = keyPairGenerator.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keys.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keys.getPrivate();

        RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey)
                .keyID(UUID.randomUUID().toString()).build();

        JWKSet jwkSet = new JWKSet(rsaKey);

        return new ImmutableJWKSet<>(jwkSet);

    }
}
