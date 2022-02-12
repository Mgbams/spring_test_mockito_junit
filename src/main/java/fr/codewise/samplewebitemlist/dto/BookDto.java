package fr.codewise.samplewebitemlist.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class BookDto {

	@NotEmpty
	private String name;
	
	@NotEmpty
	private String summary;
	
	@NotEmpty
	private int rating;
}
