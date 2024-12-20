package mockcote.timeservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mockcote.timeservice.model.Logs;
import mockcote.timeservice.repository.LogsRepository;
import mockcote.timeservice.utils.BaekjoonCrawler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeService {

    private final LogsRepository logsRepository;
    private final BaekjoonCrawler baekjoonCrawler;

    public String checkProblemResult(String userId, Integer problemId) {
        return baekjoonCrawler.checkSubmissionStatus(userId, problemId);
    }

    @Transactional
    public String processSubmission(String handle , Integer problemId, LocalDateTime startTime, Integer limitTime, String language, String status) {
        long duration = Duration.between(startTime, LocalDateTime.now()).getSeconds(); // 소요 시간 계산

        // 로그 데이터 저장
        Logs data = new Logs();
        data.setHandle(handle);
        data.setProblemId(problemId);
        data.setStatus(status);
        data.setStartTime(startTime);
        data.setLimitTime(limitTime);
        data.setDuration((int) duration);
        data.setLanguage(language);

//      duration 소요시간(초) 출력
        log.info("duration: {}", duration);

//      db 저장
        logsRepository.save(data);

        return status; // 상태 반환
    }
}
