package mockcote.timeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionRequest {
    private String handle;
    private Integer problemId;
    private String startTime;
    private Integer limitTime;
    private String language;
    private String status;
}
