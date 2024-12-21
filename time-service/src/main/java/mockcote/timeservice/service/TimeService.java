package mockcote.timeservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mockcote.timeservice.model.Logs;
import mockcote.timeservice.repository.LogsRepository;
import mockcote.timeservice.utils.BaekjoonCrawler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeService {

    private final LogsRepository logsRepository;
    private final BaekjoonCrawler baekjoonCrawler;

    private final WebClient webClient;
    @Value("${statistics-service.url}") // 통계 서비스 URL
    private String statisticsServiceUrl;

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

        // 통계 서비스로 post 요청 보내기
//        String response = webClient.post()
//                .uri(statisticsServiceUrl) // 통계 서비스의 API 경로
//                .bodyValue(data)   // 전송할 데이터
//                .retrieve()       // 응답 처리 시작
//                .bodyToMono(String.class) // 응답을 Mono<String>으로 변환
//                .block();         // 블로킹 방식으로 동기 처리
        return status; // response 반환
    }
}
