package com.ccee.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "Regiao")
public class Regiao {
    @JacksonXmlProperty
    private String sigla;

    @JacksonXmlProperty(localName = "geracao")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Geracao> geracao = new ArrayList<>();
}
