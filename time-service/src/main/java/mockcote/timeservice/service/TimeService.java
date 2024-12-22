package mockcote.timeservice.service;

import mockcote.timeservice.dto.SubmissionRequest;

public interface TimeService {

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
