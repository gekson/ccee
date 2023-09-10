package com.ccee.challenge.dto;

import com.ccee.challenge.model.Geracao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "Regiao")
public class RegiaoDTO {
    @JacksonXmlProperty
    @Column(unique=true)
    private String sigla;

    @JacksonXmlProperty(localName = "geracao")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<GeracaoDTO> geracao = new ArrayList<>();
}
