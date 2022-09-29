package com.likelion.stepstone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.*;

//@Configuration
public class SocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(
            MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpDestMatchers("/stomp/**").authenticated()
                .simpTypeMatchers(CONNECT, UNSUBSCRIBE, DISCONNECT).permitAll()
                .simpSubscribeDestMatchers("/stomp/**").authenticated()
                .anyMessage().authenticated();
    }
}
