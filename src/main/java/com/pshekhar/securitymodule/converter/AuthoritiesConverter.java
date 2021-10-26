package com.pshekhar.securitymodule.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.AttributeConverter;
import java.util.Set;

@Slf4j
public class AuthoritiesConverter implements AttributeConverter<Set<? extends GrantedAuthority>, String> {
    private final static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public String convertToDatabaseColumn(Set<? extends GrantedAuthority> object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            log.error("Exception occurred while converting to DBColumn. Exception: [{}]", ex.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public Set<? extends GrantedAuthority> convertToEntityAttribute(String dbData) {
        log.info("Converting from DB: {}", dbData);
        if (dbData == "{}" || dbData == null) {
            return null;
        }
        try {
            SimpleModule module = new SimpleModule("ConverterModule");
            module.addDeserializer(Set.class, new AuthoritiesDeserializer());
            objectMapper.registerModule(module);
            return objectMapper.readValue(dbData, new TypeReference<Set<? extends GrantedAuthority>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
