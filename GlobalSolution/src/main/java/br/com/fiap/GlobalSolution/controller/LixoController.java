package br.com.fiap.GlobalSolution.controller;

import br.com.fiap.GlobalSolution.dto.lixo.AtualizarLixoDto;
import br.com.fiap.GlobalSolution.dto.lixo.DetalhesLixoDto;
import br.com.fiap.GlobalSolution.dto.lixo.ListagemLixoDto;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("lixos")
public class LixoController {

    @Autowired
    private LixoRepository lixoRepository;

    @Autowired
    private RoboRepository roboRepository;

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DetalhesLixoDto> atualizarLixo(@PathVariable("id") Long id, @RequestBody @Valid AtualizarLixoDto atualizarLixoDto){
        var lixo = lixoRepository.getReferenceById(id);
        lixo.atualizarLixo(atualizarLixoDto);
        return ResponseEntity.ok(new DetalhesLixoDto(lixo));
    }

    @DeleteMapping("{id}/robo/{idRobo}")
    @Transactional
    public ResponseEntity<Void> deletarLixo(@PathVariable("id")Long id, @PathVariable("idRobo") Long idRobo){
        try {
            var lixo = lixoRepository.getReferenceById(id);
            var robo = roboRepository.getReferenceById(idRobo);
            robo.removerLixo(lixo);
            lixoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesLixoDto> buscarPorId(@PathVariable("id") Long id){
        try {
            var lixo = lixoRepository.getReferenceById(id);
            return ResponseEntity.ok(new DetalhesLixoDto(lixo));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ListagemLixoDto>> listar(Pageable pageable){
        var lista = lixoRepository.findAll(pageable).stream().map(ListagemLixoDto::new).toList();
        if(lista.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("por-data-periodo")
    public ResponseEntity<Page<DetalhesLixoDto>> buscarPorDataPeriodo(@RequestParam("data-inicial")LocalDateTime dataInicial, @RequestParam("data-final")LocalDateTime dataFinal, Pageable pageable){
        var lixos = lixoRepository.findByDataCapturaBetween(dataInicial, dataFinal, pageable).map(DetalhesLixoDto::new);
        return ResponseEntity.ok(lixos);
    }

    @GetMapping("por-data-passadas")
    public ResponseEntity<Page<DetalhesLixoDto>> buscarPorDataPassada(@RequestParam("data")LocalDateTime data, Pageable pageable){
        var lixos = lixoRepository.findByDataCapturaBefore(data, pageable).map(DetalhesLixoDto::new);
        return ResponseEntity.ok(lixos);
    }

    @GetMapping("por-data-futuras")
    public ResponseEntity<Page<DetalhesLixoDto>> buscarPorDataFutura(@RequestParam("data")LocalDateTime data, Pageable pageable){
        var lixos = lixoRepository.findByDataCapturaAfter(data, pageable).map(DetalhesLixoDto::new);
        return ResponseEntity.ok(lixos);
    }

}
