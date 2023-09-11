package com.ccee.challenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="agentes")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Agente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String codigo;

    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]XXX")
    @JacksonXmlProperty
    private LocalDateTime data;

    @OneToMany(mappedBy = "agente", cascade=CascadeType.ALL)
    private List<AgenteRegiao> agentesRegiao = new ArrayList<>();

    @Override
    public String toString() {
        return "Agente{" +
                "codigo='" + codigo + '\'' +
                '}';
    }
}
