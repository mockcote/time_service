package mockcote.timeservice.service;

import mockcote.timeservice.dto.SubmissionRequest;

public interface TimeService {

    /**
     * 풀이 로그 저장
     * @param request
     * @return
     */
    String saveLog(SubmissionRequest request);
}
