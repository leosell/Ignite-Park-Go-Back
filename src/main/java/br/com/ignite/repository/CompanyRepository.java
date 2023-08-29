package br.com.ignite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ignite.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
