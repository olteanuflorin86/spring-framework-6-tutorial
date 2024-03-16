package guru.springframework.spring6restmvc.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private Integer id;
    
    @NotBlank
    private String customerName;
    
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    
}
