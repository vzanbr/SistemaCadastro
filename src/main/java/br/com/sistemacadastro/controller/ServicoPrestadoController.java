package br.com.sistemacadastro.controller;

import br.com.sistemacadastro.model.entity.Cliente;
import br.com.sistemacadastro.model.entity.ServicoPrestado;
import br.com.sistemacadastro.model.repository.ClienteRepository;
import br.com.sistemacadastro.model.repository.ServicoPrestadoRepository;
import br.com.sistemacadastro.dto.ServicoPrestadoDTO;
import br.com.sistemacadastro.util.BigDecimalConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class ServicoPrestadoController {

    @Autowired
    private ServicoPrestadoRepository repository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private BigDecimalConvert convert;

    @PostMapping("/servicos/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto) {

        LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer idCliente = dto.getIdCliente();

        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado!"));

        ServicoPrestado servicoPrestado = new ServicoPrestado();
        servicoPrestado.setDescricao(dto.getDescricao());
        servicoPrestado.setData(data);
        servicoPrestado.setCliente(cliente);
        servicoPrestado.setValor(convert.converter(dto.getPreco()));

        return repository.save(servicoPrestado);
    }

    @GetMapping("/servicos")
    public List<ServicoPrestado> servicoPrestados
    (@RequestParam(value = "nome", required = false, defaultValue = "") String nome,
     @RequestParam(value = "mes", required = false) Integer mes)
    {
       return repository.findByNomeClienteAndMes("%" + nome + "%", mes);
    }

}
