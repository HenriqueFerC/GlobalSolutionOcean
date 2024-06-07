package br.com.fiap.GlobalSolution.controller;

import br.com.fiap.GlobalSolution.dto.funcionario.AtualizarFuncionarioDto;
import br.com.fiap.GlobalSolution.dto.funcionario.DetalhesFuncionarioDto;
import br.com.fiap.GlobalSolution.dto.funcionario.ListagemFuncionarioDto;
import br.com.fiap.GlobalSolution.repository.ColetaRepository;
import br.com.fiap.GlobalSolution.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ColetaRepository coletaRepository;

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DetalhesFuncionarioDto> atualizarFuncionario(@PathVariable("id") Long id, @RequestBody @Valid AtualizarFuncionarioDto funcionarioDto){
        var funcionario  = funcionarioRepository.getReferenceById(id);
        funcionario.atualizarFuncionario(funcionarioDto);
        return ResponseEntity.ok(new DetalhesFuncionarioDto(funcionario));
    }

    @DeleteMapping("{id}/coleta/{idColeta}")
    @Transactional
    public ResponseEntity<Void> deletarFuncionario(@PathVariable("id") Long id, @PathVariable("idColeta") Long idColeta){
        try {
            var coleta = coletaRepository.getReferenceById(idColeta);
            var funcionario = funcionarioRepository.getReferenceById(id);
            coleta.removerFuncionario(funcionario);
            funcionarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesFuncionarioDto> buscarPorId(@PathVariable("id") Long id){
        try {
            var funcionario = funcionarioRepository.getReferenceById(id);
            return ResponseEntity.ok(new DetalhesFuncionarioDto(funcionario));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ListagemFuncionarioDto>> listar(Pageable pageable){
        var lista = funcionarioRepository.findAll(pageable).stream().map(ListagemFuncionarioDto::new).toList();
        if(lista.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

}
