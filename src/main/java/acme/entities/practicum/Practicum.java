
package acme.entities.practicum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.courses.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			abstractPracticum;

	@NotBlank
	@Length(max = 101)
	protected String			someGoals;

	@NotNull
	protected Boolean			draftMode;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Company			company;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Course			course;

}
