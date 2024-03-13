package br.com.sistemacadastro.rest;

import br.com.sistemacadastro.model.entity.Cliente;
import br.com.sistemacadastro.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClienteRepository repository;

    @PostMapping("/salvar/cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvarCliente(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    @GetMapping("cliente/{id}")
    public Cliente acharId(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("clientes")
    public List<Cliente> clientes () {
       return repository.findAll();
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarId(@PathVariable Integer id) {
        repository.findById(id)
                .map(cliente -> {
                    repository.delete(cliente);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
