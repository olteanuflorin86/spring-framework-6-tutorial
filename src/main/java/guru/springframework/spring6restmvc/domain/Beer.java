package guru.springframework.spring6restmvc.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
//import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Beer {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//  @Type(type="org.hibernate.type.UUIDCharType")
	@JdbcTypeCode(value = Types.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
	private UUID id;
	
	@Version
	private Long version;
	
	private String beerName;
	
	private BeerStyleEnum beerStyle;
	
	private String upc;
	
	private Integer quantityOnHand;
	
	private BigDecimal price;
	
	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp createdDate;
	
	@UpdateTimestamp
	private Timestamp lastModifiedDate;
	
}
