package br.com.ignite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ignite.entity.Company;
import br.com.ignite.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	public List<Company> findAll() {
		return companyRepository.findAll();
	}
	
	public Company saveAndFlush(Company company) {
		return companyRepository.saveAndFlush(company);
	}
}
