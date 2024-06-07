package br.com.fiap.GlobalSolution.controller;

import br.com.fiap.GlobalSolution.dto.central.AtualizarCentralDto;
import br.com.fiap.GlobalSolution.dto.central.CadastrarCentralDto;
import br.com.fiap.GlobalSolution.dto.central.DetalhesCentralDto;
import br.com.fiap.GlobalSolution.dto.central.ListagemCentralDto;
import br.com.fiap.GlobalSolution.dto.coleta.CadastrarColetaDto;
import br.com.fiap.GlobalSolution.dto.coleta.DetalhesColetaDto;
import br.com.fiap.GlobalSolution.dto.robo.CadastrarRoboDto;
import br.com.fiap.GlobalSolution.dto.robo.DetalhesRoboDto;
import br.com.fiap.GlobalSolution.model.Central;
import br.com.fiap.GlobalSolution.model.Coleta;
import br.com.fiap.GlobalSolution.model.Robo;
import br.com.fiap.GlobalSolution.repository.CentralRepository;
import br.com.fiap.GlobalSolution.repository.ColetaRepository;
import br.com.fiap.GlobalSolution.repository.RoboRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("centrais")
public class CentralController {

    @Autowired
    private CentralRepository centralRepository;

    @Autowired
    private ColetaRepository coletaRepository;

    @Autowired
    private RoboRepository roboRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DetalhesCentralDto> cadastrarCentral(@RequestBody @Valid CadastrarCentralDto centralDto, UriComponentsBuilder uriBuilder){
        var central = new Central(centralDto);
        centralRepository.save(central);
        var url = uriBuilder.path("centrais/{id}").buildAndExpand(central.getId()).toUri();
        return ResponseEntity.created(url).body(new DetalhesCentralDto(central));
    }

    @PostMapping("{id}/coleta")
    @Transactional
    public ResponseEntity<DetalhesColetaDto> cadastrarColeta(@PathVariable("id") Long id, @RequestBody @Valid CadastrarColetaDto coletaDto, UriComponentsBuilder uriBuilder){
        var central = centralRepository.getReferenceById(id);
        var coleta = new Coleta(coletaDto, central);
        central.adicionarColeta(coleta);
        coletaRepository.save(coleta);
        var url = uriBuilder.path("coletas/{ìd}").buildAndExpand(coleta.getId()).toUri();
        return ResponseEntity.created(url).body(new DetalhesColetaDto(coleta));
    }

    @PostMapping("{id}/robo")
    @Transactional
    public ResponseEntity<DetalhesRoboDto> cadastrarRobo(@PathVariable("id") Long id, @RequestBody @Valid CadastrarRoboDto roboDto, UriComponentsBuilder uriBuilder){
        var central = centralRepository.getReferenceById(id);
        var robo = new Robo(roboDto, central);
        central.adicionarRobo(robo);
        roboRepository.save(robo);
        var url = uriBuilder.path("robos/{id}").buildAndExpand(robo.getId()).toUri();
        return ResponseEntity.created(url).body(new DetalhesRoboDto(robo));
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DetalhesCentralDto> atualizarCentral(@PathVariable("id")Long id, @RequestBody @Valid AtualizarCentralDto centralDto){
        var central = centralRepository.getReferenceById(id);
        central.atualizarCentral(centralDto);
        return ResponseEntity.ok(new DetalhesCentralDto(central));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> deletarCentral(@PathVariable("id") Long id){
        try {
            centralRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesCentralDto> buscarPorId(@PathVariable("id") Long id){
        try {
            var central = centralRepository.getReferenceById(id);
            return ResponseEntity.ok(new DetalhesCentralDto(central));
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ListagemCentralDto>> listar(Pageable pageable){
        var lista = centralRepository.findAll(pageable).stream().map(ListagemCentralDto::new).toList();
        if(lista.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

}
