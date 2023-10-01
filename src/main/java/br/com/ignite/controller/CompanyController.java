package br.com.ignite.controller;

import java.util.*;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ignite.dto.CompanyDTO;
import br.com.ignite.entity.Company;
import br.com.ignite.service.CompanyService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PostMapping
	@Transactional
	public ResponseEntity<?> post(@RequestBody @Valid CompanyDTO company) {
		if (company.getCnpj() == null || company.getCnpj().trim().isEmpty()) {
			return new ResponseEntity<>("CNPJ obrigatorio", HttpStatus.BAD_REQUEST);
		} else if (company.getCpfResponsavel() == null || company.getCpfResponsavel().trim().isEmpty()) {
			return new ResponseEntity<>("CPF do responsavel obrigatorio", HttpStatus.BAD_REQUEST);
		}

		Company comp = new Company();
		comp.setCnpj(company.getCnpj());
		comp.setCpfResponsavel(company.getCpfResponsavel());
		comp.setNomeFantasia(company.getNomeFantasia());
		comp.setNomeResponsavel(company.getNomeResponsavel());
		comp.setQtdCarros(company.getQtdCarros());
		comp.setLogradouro(company.getLogradouro());
		comp.setCidade(company.getCidade());
		comp.setComplemento(company.getComplemento());
		comp.setDateCreation(new Date());
		comp.setDiaFuncionamento(company.getDiaFuncionamento());
		comp.setEnabled(true);
		comp.setHorarioFuncionamento(company.getHorarioFuncionamento());
		comp.setNumero(company.getNumero());
		comp.setTelefone(company.getTelefone());
		comp.setUf(company.getUf());
		comp.setBairro(company.getBairro());
		comp.setCep(company.getCep());

		Company companySaved = companyService.saveAndFlush(comp);
		CompanyDTO companyDTOsaved = map(companySaved);
		return new ResponseEntity<CompanyDTO>(companyDTOsaved, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		List<Company> company = companyService.findAll();
		List<CompanyDTO> companyDTO = map(company);
		return new ResponseEntity<>(company, HttpStatus.OK);
	}

	public CompanyDTO map(Company company) {
		if (company == null) return null;

		CompanyDTO comp = new CompanyDTO();
		comp.setCnpj(company.getCnpj());
		comp.setCpfResponsavel(company.getCpfResponsavel());
		comp.setNomeFantasia(company.getNomeFantasia());
		comp.setNomeResponsavel(company.getNomeResponsavel());
		comp.setQtdCarros(company.getQtdCarros());
		comp.setLogradouro(company.getLogradouro());
		comp.setCidade(company.getCidade());
		comp.setComplemento(company.getComplemento());
		comp.setDateCreation(new Date());
		comp.setDiaFuncionamento(company.getDiaFuncionamento());
		comp.setEnabled(true);
		comp.setHorarioFuncionamento(company.getHorarioFuncionamento());
		comp.setNumero(company.getNumero());
		comp.setTelefone(company.getTelefone());
		comp.setUf(company.getUf());
		comp.setBairro(company.getBairro());
		comp.setCep(company.getCep());
		return comp;
	}

	public List<CompanyDTO> map(List<Company> company) {
		if (company == null) {
			return Collections.emptyList();
		}
		List<CompanyDTO> companyDTO = new ArrayList<>();
		for (Company comp : company) {
			companyDTO.add(map(comp));
		}
		return companyDTO;
	}
}
