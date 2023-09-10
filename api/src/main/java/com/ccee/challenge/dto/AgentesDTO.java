package com.ccee.challenge.dto;

import com.ccee.challenge.model.Agente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Agentes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentesDTO {

    private String versao;

    @JacksonXmlProperty(localName = "agente")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Agente> agentes = new ArrayList<>();
}
