package mockcote.timeservice.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mockcote.timeservice.dto.SubmissionRequest;

import java.time.LocalDateTime;

public interface TimeService {

    /**
     * 풀이 시작
     * @param handle
     * @param problemId
     * @param now
     * @return
     */
    LocalDateTime timeStart(@NotBlank String handle, @NotNull Integer problemId, LocalDateTime now);

    /**
     * 풀이 종료
     * @param handle
     */
    void timeEnd(@NotBlank String handle);

    /**
     * 풀이 여부 체크
     * @param handle
     * @param problemId
     * @return
     */
    String checkSubmissionStatus(String handle, Integer problemId);

    /**
     * 풀이 로그 저장
     * @param request
     * @return
     */
    String saveLog(SubmissionRequest request);
}
