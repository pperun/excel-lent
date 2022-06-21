package com.app.excellent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.Random;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/questions", "/answers")
                .setHandshakeHandler(new RandomUsernameHandler())
                .withSockJS();
    }



    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
                .setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/topic", "/queue");
    }

    private class RandomUsernameHandler extends DefaultHandshakeHandler {
        private String[] adjectives = {"big", "little", "american", "pink", "black", "white", "beautiful", "elegant", "sleepy"};
        private String[] animals ={"cat", "dog", "kitten", "crab", "python", "anaconda", "gorilla", "beaver", "penguin", "seagull"};
        private Random random = new Random();

        @Override
        protected Principal determineUser(ServerHttpRequest request,
                                          WebSocketHandler wsHandler,
                                          Map<String, Object> attributes) {
            String username = this.getRandom(adjectives) + "-" + this.getRandom(animals) + "-" + random.nextInt(100);
            return new UsernamePasswordAuthenticationToken(username, null);
        }

        private String getRandom(String[] array) {
            return array[random.nextInt(array.length)];
        }


    }
}
