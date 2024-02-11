package guru.springframework.spring6restmvc.entities;

import java.sql.Timestamp; 
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
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
	
	public BeerOrder(UUID id, Long version, Set<BeerOrderLine> beerOrderLines, BeerOrderShipment beerOrderShipment,
			Timestamp createdDate, Timestamp lastModifiedDate, String customerRef, Customer customer) {
		this.id = id;
		this.version = version;
		this.beerOrderLines = beerOrderLines;
//		this.beerOrderShipment = beerOrderShipment;
		this.setBeerOrderShipment(beerOrderShipment);
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.customerRef = customerRef;
//		this.customer = customer;
		this.setCustomer(customer);
	}
    
	@Version
	private Long version;
	
	@OneToMany(mappedBy = "beerOrder")
	private Set<BeerOrderLine> beerOrderLines;
	
    @OneToOne(cascade = CascadeType.PERSIST)
    private BeerOrderShipment beerOrderShipment;
	
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
	
    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.getBeerOrders().add(this);
    }
    
    public void setBeerOrderShipment(BeerOrderShipment beerOrderShipment) {
        this.beerOrderShipment = beerOrderShipment;
        beerOrderShipment.setBeerOrder(this);
    }

}
