package br.com.sistemacadastro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ServicoPrestadoDTO {
    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String descricao;

    @NotEmpty(message = "{campo.preco.obrigatorio}")
    private String preco;

    @NotEmpty(message = "{campo.data.obrigatorio}")
    private String data;

    @NotNull(message = "{campo.cliente.obrigatorio}")
    private Integer idCliente;
}
