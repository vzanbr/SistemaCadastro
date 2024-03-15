package br.com.sistemacadastro.rest;

import br.com.sistemacadastro.model.entity.Cliente;
import br.com.sistemacadastro.model.entity.ServicoPrestado;
import br.com.sistemacadastro.model.repository.ClienteRepository;
import br.com.sistemacadastro.model.repository.ServicoPrestadoRepository;
import br.com.sistemacadastro.rest.dto.ServicoPrestadoDTO;
import br.com.sistemacadastro.util.BigDecimalConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
public class ServicoPrestadoController {

    @Autowired
    private ServicoPrestadoRepository repository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private BigDecimalConvert convert;

    @PostMapping("/salvar/servico")
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto) {

        LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer idCliente = dto.getIdCliente();

        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado!"));

        ServicoPrestado servicoPrestado = new ServicoPrestado();
        servicoPrestado.setDesc(dto.getDescricao());
        servicoPrestado.setData(data);
        servicoPrestado.setCliente(cliente);
        servicoPrestado.setValor(convert.converter(dto.getPreco()));

        return repository.save(servicoPrestado);
    }


//    @GetMapping("/servico/{id}")
//    public Cliente acharId(@PathVariable Integer id) {
//        return repository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
//    }
//
    @GetMapping("/servico")
    public List<ServicoPrestado> servicoPrestados
    (@RequestParam(value = "nome", required = false, defaultValue = "") String nome,
     @RequestParam(value = "mes", required = false) String mes)
    {
       return repository.findByNomeClienteAndMes("%" + nome + "%", mes);
    }
//
//    @DeleteMapping("/deletar/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deletarId(@PathVariable Integer id) {
//        repository.findById(id)
//                .map(cliente -> {
//                    repository.delete(cliente);
//                    return Void.TYPE;
//                })
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
//    }
//
//    @PutMapping("/atualizar/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void atualizar(@PathVariable Integer id, @RequestBody Cliente clienteAtualizado) {
//        repository.findById(id)
//                .map(cliente -> {
//                    cliente.setNome(clienteAtualizado.getNome());
//                    cliente.setCpf(clienteAtualizado.getCpf());
//                    return repository.save(cliente);
//                })
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
//    }
}
