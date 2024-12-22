package mockcote.timeservice.service;

import mockcote.timeservice.dto.SubmissionRequest;

public interface TimeService {

  	// 풀이여부체크
    String checkSubmissionStatus(String userId, Integer problemId) throws Exception;
  
    /**
     * 풀이 로그 저장
     * @param request
     * @return
     */
    String saveLog(SubmissionRequest request);
}
