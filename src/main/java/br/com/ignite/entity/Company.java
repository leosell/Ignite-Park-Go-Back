package br.com.ignite.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ENABLED")
	private Boolean enabled;
	
	@Column(name = "NOME_RESPONSAVEL")
	private String nomeResponsavel;

	@Column(name = "CPF_RESPONSAVEL")
	private String cpfResponsavel;

	@Column(name = "NOME_FANTASIA")
	private String nomeFantasia;
	
	@Column(name = "CNPJ")
	private String cnpj;

	@Column(name = "QTD_CARROS")
	private Integer qtdCarros;

	@Column(name = "HORARIO_FUNCIONAMENTO")
	private String horarioFuncionamento;

	@Column(name = "DIA_FUNCIONAMENTO")
	private String diaFuncionamento;
	
	@Column(name = "TELEFONE")
	private String telefone;
	
	@Column(name = "DATE_CREATION")
	private Date dateCreation;
	
	@Column(name = "LOGRADOURO")
	private String logradouro;
	
	@Column(name = "NUMERO")
	private Integer numero;
	
	@Column(name = "COMPLEMENTO")
	private String complemento;
	
	@Column(name = "BAIRRO")
	private String bairro;
	
	@Column(name = "CIDADE")
	private String cidade;
	
	@Column(name = "UF", columnDefinition = "CHAR(2)")
	private String uf;

	@Column(name = "CEP")
	private String cep;

	// @ManyToOne
	// @JoinColumn(name = "COMPANY_ID")
	// private User users;

	@OneToMany(mappedBy = "company")
	@JsonBackReference
	private List<User> users;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<History> historys;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<Wallet> wallet;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<Person> person;
}
