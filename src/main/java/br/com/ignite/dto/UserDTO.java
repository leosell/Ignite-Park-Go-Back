package br.com.ignite.dto;

import java.util.Date;

import br.com.ignite.entity.Company;
import br.com.ignite.enums.TypeUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private Boolean enabled;
	private String name;
	private String email;
	private String password;
	private Date dateCreation;
	private TypeUser type;
	private Company company;
}
