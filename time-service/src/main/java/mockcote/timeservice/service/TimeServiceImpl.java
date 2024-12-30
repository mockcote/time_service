package mockcote.timeservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mockcote.timeservice.dto.SubmissionRequest;
import mockcote.timeservice.exception.CustomException;
import mockcote.timeservice.model.Logs;
import mockcote.timeservice.repository.LogsRepository;
import mockcote.timeservice.utils.BaekjoonCrawler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService {

    private final LogsRepository logsRepository;
    private final BaekjoonCrawler baekjoonCrawler;
    private final WebClient webClient;
    private final Map<String, LocalDateTime> sessions = new HashMap<>();

    @Value("${statistics-service.url}") // 통계 서비스 URL
    private String statisticsServiceUrl;

    @Override
    public LocalDateTime timeStart(String handle, LocalDateTime now) {
        // 이미 세션이 있다면 시작시간 return
        if(sessions.containsKey(handle)) return sessions.get(handle);

        // 세션이 처음이라면 현재시간 return
        sessions.put(handle, now);
        return now;
    }

    @Override
    public void timeEnd(String handle) {
        sessions.remove(handle);
    }

    @Override
    public String checkSubmissionStatus(String handle, Integer problemId) {
        return baekjoonCrawler.checkSubmissionStatus(handle, problemId);
    }

    @Override
    @Transactional
    public String saveLog(SubmissionRequest request) {
        long duration = Duration.between(request.getStartTime(), LocalDateTime.now()).getSeconds(); // 소요 시간 계산

        // 로그 데이터 저장
        Logs data = new Logs();
        data.setHandle(request.getHandle());
        data.setProblemId(request.getProblemId());
        data.setStatus(request.getStatus());
        data.setStartTime(request.getStartTime());
        data.setLimitTime(request.getLimitTime());
        data.setDuration((int) duration);
        data.setLanguage(request.getLanguage());

//      duration 소요시간(초) 출력
        log.info("duration: {}", duration);

//      db 저장
        logsRepository.save(data);

        // 통계 서비스로 post 요청 보내기
        HttpStatusCode response = null;         // 블로킹 방식으로 동기 처리
        try {
            response = webClient.post()
                    .uri(statisticsServiceUrl+"/stats/history") // 통계 서비스의 API 경로
                    .bodyValue(data)   // 전송할 데이터
                    .exchangeToMono((res) -> Mono.just(res.statusCode()))// 응답을 Mono<String>으로 변환
                    .block();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "통계 서비스 연결 에러");
        }

        try {
            response = webClient.post()
                    .uri(statisticsServiceUrl+"/stats/rank/problem") // 통계 서비스의 API 경로
                    .bodyValue(data)   // 전송할 데이터
                    .exchangeToMono((res) -> Mono.just(res.statusCode()))// 응답을 Mono<String>으로 변환
                    .block();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "통계 서비스 연결 에러");
        }

        timeEnd(request.getHandle());
        return response.toString(); // response 반환
    }

}
