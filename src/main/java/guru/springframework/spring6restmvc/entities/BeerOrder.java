package guru.springframework.spring6restmvc.entities;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BeerOrder {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
	private UUID id;
	
	@Version
	private Long version;
	
	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp createdDate;
	
	@UpdateTimestamp
	private Timestamp lastModifiedDate;
	
	public boolean isNew() {
		return this.id == null;
	}
	
	private String customerRef;
	
	@ManyToOne
	private Customer customer;
}
