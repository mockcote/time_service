package mockcote.timeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionRequest {
    private String handle;
    private Integer problemId;
    private LocalDateTime startTime;
    private Integer limitTime;
    private String language;
    private String status;
}
