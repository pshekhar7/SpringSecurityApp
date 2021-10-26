package com.pshekhar.securitymodule.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
class AuthoritiesDeserializer extends JsonDeserializer<Set<? extends GrantedAuthority>> {

    @Override
    public Set<? extends GrantedAuthority> deserialize(JsonParser p, DeserializationContext context) throws IOException, JsonProcessingException {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        try {
            ArrayNode arrayNode = (ArrayNode) p.readValueAsTree();
            arrayNode.forEach(
                    node -> {
                        authorities.add(new SimpleGrantedAuthority(node.get("authority").asText()));
                    }
            );
            return authorities;
        } catch (Exception e) {
            log.error("Exception occurred while deserializing EligibilityRuleMetadata. Error: [{}]", e.getLocalizedMessage());
        }
        return null;
    }
}

