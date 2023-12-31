package br.com.ignite.entity;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.ignite.enums.TypeUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ENABLED")
	private Boolean enabled;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "EMAIL", unique = true)
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "DATE_CREATION")
	private Date dateCreation;
	
	@Column(name = "LAST_LOGIN")
	private Date lastLogin;
	
	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	private TypeUser type;

	// @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
	// private List<Company> companys;
	
	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	private Company company;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// return List.of(new SimpleGrantedAuthority(type.name()));
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
