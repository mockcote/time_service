package mockcote.timeservice.service;

import lombok.RequiredArgsConstructor;
import mockcote.timeservice.model.Logs;
import mockcote.timeservice.repository.LogsRepository;
import mockcote.timeservice.utils.BaekjoonCrawler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final LogsRepository logsRepository;
    private final BaekjoonCrawler baekjoonCrawler;

    public String checkProblemResult(String userId, Integer problemId) {
        return baekjoonCrawler.checkSubmissionStatus(userId, problemId);
    }

    public String processSubmission(Integer userId, Integer problemId, LocalDateTime startTime, Integer limitTime, String language, String submissionResult) {
        long duration = Duration.between(startTime, LocalDateTime.now()).getSeconds(); // 소요 시간 계산
        String status;

        if (submissionResult.equals("SUCCESS")) {
            status = "SUCCESS";
        }
        else {
            status = "FAIL";
        }

        // 로그 데이터 저장
        Logs log = new Logs();
        log.setUserId(userId);
        log.setProblemId(problemId);
        log.setStatus(status);
        log.setStartTime(startTime);
        log.setLimitTime(limitTime);
        log.setDuration((int) duration);
        log.setLanguage(language);

        logsRepository.save(log);

        return status; // 상태 반환
    }
}
