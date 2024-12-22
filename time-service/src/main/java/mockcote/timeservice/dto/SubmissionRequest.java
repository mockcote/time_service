package mockcote.timeservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionRequest {
    @NotBlank
    private String handle;

    @NotNull
    private Integer problemId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private Integer limitTime;

    @NotNull
    private String language;

    @NotNull
    private String status;
}
