package br.com.fiap.GlobalSolution.controller;

import br.com.fiap.GlobalSolution.dto.coleta.AtualizarColetaDto;
import br.com.fiap.GlobalSolution.dto.coleta.CadastrarColetaDto;
import br.com.fiap.GlobalSolution.dto.coleta.DetalhesColetaDto;
import br.com.fiap.GlobalSolution.dto.coleta.ListagemColetaDto;
import br.com.fiap.GlobalSolution.dto.funcionario.AtualizarFuncionarioDto;
import br.com.fiap.GlobalSolution.dto.funcionario.CadastrarFuncionarioDto;
import br.com.fiap.GlobalSolution.dto.funcionario.DetalhesFuncionarioDto;
import br.com.fiap.GlobalSolution.model.Coleta;
import br.com.fiap.GlobalSolution.model.Funcionario;
import br.com.fiap.GlobalSolution.repository.CentralRepository;
import br.com.fiap.GlobalSolution.repository.ColetaRepository;
import br.com.fiap.GlobalSolution.repository.FuncionarioRepository;
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
@RequestMapping("coletas")
public class ColetaController {

    @Autowired
    private ColetaRepository coletaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CentralRepository centralRepository;

    @PostMapping("{id}/funcionario")
    @Transactional
    public ResponseEntity<DetalhesFuncionarioDto> cadastrarFuncionario(@PathVariable("id") Long id, @RequestBody @Valid CadastrarFuncionarioDto funcionarioDto, UriComponentsBuilder uriBuilder){
        var coleta = coletaRepository.getReferenceById(id);
        var funcionario = new Funcionario(funcionarioDto, coleta);
        coleta.adicionarFuncionario(funcionario);
        funcionarioRepository.save(funcionario);
        var url = uriBuilder.path("funcionarios/{ìd}").buildAndExpand(funcionario.getId()).toUri();
        return ResponseEntity.created(url).body(new DetalhesFuncionarioDto(funcionario));
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DetalhesColetaDto> atualizarColeta(@PathVariable("id") Long id, @RequestBody @Valid AtualizarColetaDto coletaDto){
        var coleta = coletaRepository.getReferenceById(id);
        coleta.atualizarColeta(coletaDto);
        return ResponseEntity.ok(new DetalhesColetaDto(coleta));
    }

    @DeleteMapping("{id}/central/{idCentral}")
    @Transactional
    public ResponseEntity<Void> removerColeta(@PathVariable("id") Long id, @PathVariable("idCentral") Long idCentral){
        try {
            var central = centralRepository.getReferenceById(idCentral);
            var coleta = coletaRepository.getReferenceById(id);
            central.removerColeta(coleta);
            coletaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesColetaDto> buscarPorId(@PathVariable("id") Long id){
        try {
            var coleta = coletaRepository.getReferenceById(id);
            return ResponseEntity.ok(new DetalhesColetaDto(coleta));
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ListagemColetaDto>> listar(Pageable pageable){
        var lista = coletaRepository.findAll(pageable).stream().map(ListagemColetaDto::new).toList();
        if(lista.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("por-quantidade-menor")
    public ResponseEntity<Page<DetalhesColetaDto>> buscarPorQuantidadeMenor(@RequestParam("quantidade") Long quantidade, Pageable pageable){
        var coletas = coletaRepository.findByQuantidadeColetadaLessThan(quantidade, pageable).map(DetalhesColetaDto::new);
        return ResponseEntity.ok(coletas);
    }

    @GetMapping("por-quantidade-maior")
    public ResponseEntity<Page<DetalhesColetaDto>> buscarPorQuantidadeMaior(@RequestParam("quantidade") Long quantidade, Pageable pageable){
        var coletas = coletaRepository.findByQuantidadeColetadaGreaterThan(quantidade, pageable).map(DetalhesColetaDto::new);
        return ResponseEntity.ok(coletas);
    }

    @GetMapping("por-quantidade")
    public ResponseEntity<Page<DetalhesColetaDto>> buscarPorQuantidade(@RequestParam("quantidadeInicial") Long quantidadeInicial, @RequestParam("quantidadeFinal") Long quantidadeFinal, Pageable pageable){
        var coletas = coletaRepository.findByQuantidadeColetadaBetween(quantidadeInicial, quantidadeFinal, pageable).map(DetalhesColetaDto::new);
        return ResponseEntity.ok(coletas);
    }

    @GetMapping("por-data-anterior")
    public ResponseEntity<Page<DetalhesColetaDto>> buscarPorDataAnterior(@RequestParam("data")LocalDate data, Pageable pageable){
        var coletas = coletaRepository.findByDataColetadaBefore(data, pageable).map(DetalhesColetaDto::new);
        return ResponseEntity.ok(coletas);
    }

    @GetMapping("por-data-posterior")
    public ResponseEntity<Page<DetalhesColetaDto>> buscarPorDataFutura(@RequestParam("data")LocalDate data, Pageable pageable){
        var coletas = coletaRepository.findByDataColetadaAfter(data, pageable).map(DetalhesColetaDto::new);
        return ResponseEntity.ok(coletas);
    }

    @GetMapping("por-data")
    public ResponseEntity<Page<DetalhesColetaDto>> buscarPorData(@RequestParam("data-inicial") LocalDate dataInicial, @RequestParam("data-final") LocalDate dataFinal, Pageable pageable){
        var coletas = coletaRepository.findByDataColetadaBetween(dataInicial, dataFinal, pageable).map(DetalhesColetaDto::new);
        return ResponseEntity.ok(coletas);
    }

}
