package dinulfatahryan.belajar.springboot.restfull.api.model;

import jakarta.validation.constraints.Email;
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
public class CreateContactRequest {
    @Size(max = 100)
    @NotBlank
    private String firstName;

    @Size(max = 100)
    private String LastName;

    @Size(max = 100)
    @Email
    private String email;

    @Size(max = 100)

    private String phone;
}
