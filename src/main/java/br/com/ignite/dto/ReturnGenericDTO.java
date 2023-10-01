package br.com.ignite.dto;

import org.springframework.http.HttpStatusCode;

import br.com.ignite.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnGenericDTO {

	private String message;
	private Status status;
	private HttpStatusCode code;
	private String data;
}
