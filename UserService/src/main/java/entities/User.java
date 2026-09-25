package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "micro_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@Column(name = "ID")
	private String userId;
	
	@Column(name = "NAME" , length=25)
	private String name;
	
	@Column(name = "EMAIL" , unique = true)
	private String email;
	
	@Column(name = "ABOUT")
	private String about;
	
}
