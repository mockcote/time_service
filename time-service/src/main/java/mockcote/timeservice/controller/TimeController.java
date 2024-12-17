package mockcote.timeservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mockcote.timeservice.dto.SubmissionRequest;
import mockcote.timeservice.service.TimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/time")
public class TimeController {

    private final TimeService timeService;

    @GetMapping("/result")
    public ResponseEntity<String> checkProblemResult(
            @RequestParam String userId,
            @RequestParam Integer problemId) {

        String result = timeService.checkProblemResult(userId, problemId);
        return ResponseEntity.ok("채점 결과: " + result);
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitResult(@RequestBody SubmissionRequest request) {

        // 요청 데이터 출력 (Slf4j 로그 사용)
    log.info("userId: {}", request.getUserId());
    log.info("problemId: {}", request.getProblemId());
    log.info("startTime: {}", request.getStartTime());
    log.info("limitTime: {}", request.getLimitTime());
    log.info("language: {}", request.getLanguage());
    log.info("submissionResult: {}", request.getSubmissionResult());

        // 서비스 로직 호출
        String result = timeService.processSubmission(
                request.getUserId(),
                request.getProblemId(),
                java.time.LocalDateTime.parse(request.getStartTime()),
                request.getLimitTime(),
                request.getLanguage(),
                request.getSubmissionResult()
        );
    log.info("결과: {}", result); // 결과 로그 출력
        return ResponseEntity.ok("결과: " + result);
    }
}
