package br.com.fiap.GlobalSolution.controller;

import br.com.fiap.GlobalSolution.dto.lixo.CadastrarLixoDto;
import br.com.fiap.GlobalSolution.dto.lixo.DetalhesLixoDto;
import br.com.fiap.GlobalSolution.dto.robo.AtualizarRoboDto;
import br.com.fiap.GlobalSolution.dto.robo.DetalhesRoboDto;
import br.com.fiap.GlobalSolution.dto.robo.ListagemRoboDto;
import br.com.fiap.GlobalSolution.model.Lixo;
import br.com.fiap.GlobalSolution.model.Status;
import br.com.fiap.GlobalSolution.repository.CentralRepository;
import br.com.fiap.GlobalSolution.repository.LixoRepository;
import br.com.fiap.GlobalSolution.repository.RoboRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("robos")
public class RoboController {

    @Autowired
    private RoboRepository roboRepository;

    @Autowired
    private LixoRepository lixoRepository;

    @Autowired
    private CentralRepository centralRepository;

    @PostMapping("{id}/lixo")
    @Transactional
    public ResponseEntity<DetalhesLixoDto> cadastrarLixo(@PathVariable("id") Long id, @RequestBody @Valid CadastrarLixoDto lixoDto, UriComponentsBuilder uriBuilder){
        var robo = roboRepository.getReferenceById(id);
        var lixo = new Lixo(lixoDto, robo);
        robo.adicionarLixo(lixo);
        lixoRepository.save(lixo);
        var url = uriBuilder.path("lixos/{id}").buildAndExpand(lixo.getId()).toUri();
        return ResponseEntity.created(url).body(new DetalhesLixoDto(lixo));
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DetalhesRoboDto> atualizarRobo(@PathVariable("id") Long id, @RequestBody @Valid AtualizarRoboDto atualizarRoboDto){
        var robo = roboRepository.getReferenceById(id);
        robo.atualizarRobo(atualizarRoboDto);
        return ResponseEntity.ok(new DetalhesRoboDto(robo));
    }

    @DeleteMapping("{id}/central/{idCentral}")
    @Transactional
    public ResponseEntity<Void> deletarRobo(@PathVariable("id") Long id, @PathVariable("idCentral") Long idCentral){
        try {
            var central = centralRepository.getReferenceById(idCentral);
            var robo = roboRepository.getReferenceById(id);
            central.removerRobo(robo);
            roboRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesRoboDto> buscarPorId(@PathVariable("id") Long id){
        try {
            var robo = roboRepository.getReferenceById(id);
            return ResponseEntity.ok(new DetalhesRoboDto(robo));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ListagemRoboDto>> listar (Pageable pageable){
        var lista = roboRepository.findAll(pageable).stream().map(ListagemRoboDto::new).toList();
        if(lista.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("por-data-manutencao-passadas")
    public ResponseEntity<Page<DetalhesRoboDto>> buscarPorDataPassada(@RequestParam("data")LocalDate data, Pageable pageable){
        var robos = roboRepository.findByUltimaManutencaoBefore(data, pageable).map(DetalhesRoboDto::new);
        return ResponseEntity.ok(robos);
    }

    @GetMapping("por-data-manutencao-futuras")
    public ResponseEntity<Page<DetalhesRoboDto>> buscarPorDataFutura(@RequestParam("data")LocalDate data, Pageable pageable){
        var robos = roboRepository.findByUltimaManutencaoAfter(data, pageable).map(DetalhesRoboDto::new);
        return ResponseEntity.ok(robos);
    }

    @GetMapping("por-status")
    public ResponseEntity<Page<DetalhesRoboDto>> buscarPorStatus(@RequestParam("status") Status status, Pageable pageable){
        var robos = roboRepository.findByStatusIs(status, pageable).map(DetalhesRoboDto::new);
        return ResponseEntity.ok(robos);
    }

}
