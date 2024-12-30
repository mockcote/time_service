package mockcote.timeservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeStartRequest {
    @NotBlank
    private String handle;
}
