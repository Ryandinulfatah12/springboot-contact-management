package dinulfatahryan.belajar.springboot.restfull.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAddressRequest {
    @NotBlank
    @JsonIgnore
    private String addressId;

    @NotBlank
    @JsonIgnore
    private String contactId;

    @Size(max = 200)
    private String street;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    @NotBlank
    private String province;

    @Size(max = 100)
    @NotBlank
    private String country;

    @Size(max = 10)
    private String postalCode;
}
