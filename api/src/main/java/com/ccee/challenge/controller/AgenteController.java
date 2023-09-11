package com.ccee.challenge.controller;

import com.ccee.challenge.model.Agente;
import com.ccee.challenge.dto.AgentesDTO;
import com.ccee.challenge.service.AgenteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AgenteController {

    private final AgenteService agenteService;
    private final String TEMP_STORAGE = System.getProperty("java.io.tmpdir");

    @RequestMapping(value = "/uploadJson", method = RequestMethod.POST)
    public String uploadAgentes(@RequestBody Agente agente) {
        System.out.println(agente);
        return "Agente salvo " + agente;
    }

    @PostMapping(path = "/fileUpload")
    public void fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        try {
            String originalFileName = multipartFile.getOriginalFilename();
            ResourceUtils.getURL("classpath:application.properties");
            File fileToImport = new File(TEMP_STORAGE + originalFileName);
            multipartFile.transferTo(fileToImport);

            XmlMapper xmlMapper = new XmlMapper();
            AgentesDTO agentes = xmlMapper.readValue(fileToImport, AgentesDTO.class);
            agenteService.salvarAgentes(agentes.getAgentes());
            System.out.println(agentes);

            ResponseEntity.ok("Agentes cadastrados com sucesso.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/findDadosPorRegiao/{sigla}", method = RequestMethod.GET)
    public ResponseEntity findDadosPorRegiao(@PathVariable(name = "sigla") String sigla) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArray = objectMapper.writeValueAsString(agenteService.findDadosPorRegiao(sigla));
        return ResponseEntity.ok(jsonArray);
    }

    @RequestMapping(value = "/findConsolidadoValorGeracao", method = RequestMethod.GET)
    public ResponseEntity findConsolidadoValorGeracao() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArray = objectMapper.writeValueAsString(agenteService.findConsolidadoValorGeracao());
        return ResponseEntity.ok(jsonArray);
    }

    @RequestMapping(value = "/findConsolidadoValorCompra", method = RequestMethod.GET)
    public ResponseEntity findConsolidadoValorCompra() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArray = objectMapper.writeValueAsString(agenteService.findConsolidadoValorCompra());
        return ResponseEntity.ok(jsonArray);
    }

    @RequestMapping(value = "/findConsolidadoValorTotal", method = RequestMethod.GET)
    public ResponseEntity findConsolidadoValorTotal() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArray = objectMapper.writeValueAsString(agenteService.findConsolidadoValorTotal());
        return ResponseEntity.ok(jsonArray);
    }
}
