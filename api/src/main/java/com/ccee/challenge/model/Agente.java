package com.ccee.challenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Agente")
public class Agente {

    @JacksonXmlProperty(isAttribute = true)
    private String codigo;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]XXX")
    @JacksonXmlProperty
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime data;

    @JacksonXmlProperty(localName = "regiao")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Regiao> regiao = new ArrayList<>();
}
