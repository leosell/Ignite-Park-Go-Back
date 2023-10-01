package br.com.ignite.dto;

import java.util.Date;

import br.com.ignite.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

	private Long id;
	private Boolean enabled;
	private String nomeResponsavel;
	private String cpfResponsavel;
	private String nomeFantasia;
	private String cnpj;
	private Integer qtdCarros;
	private String horarioFuncionamento;
	private String diaFuncionamento;
	private String telefone;
	private Date dateCreation;
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String cep;
}
