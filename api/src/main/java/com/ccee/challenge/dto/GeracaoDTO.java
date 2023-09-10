package com.ccee.challenge.dto;

import com.ccee.challenge.model.ValorGeracao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@JacksonXmlRootElement(localName = "Geracao")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeracaoDTO {

    @JacksonXmlProperty(localName = "valor")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Double> valor= new ArrayList<>();
}
