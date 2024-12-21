package mockcote.timeservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mockcote.timeservice.dto.SubmissionRequest;
import mockcote.timeservice.service.TimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/submissions")
public class TimeController {

    private final TimeService timeService;

    @GetMapping("/result")
    public ResponseEntity<String> checkProblemResult(
            @RequestParam String userId,
            @RequestParam Integer problemId) {

        String result = timeService.checkProblemResult(userId, problemId);
        
        return ResponseEntity.ok("채점 결과: " + result);
    }

    // 로그 저장
    @PostMapping("/save")
    public ResponseEntity<String> submitResult(@RequestBody SubmissionRequest request) {

        // 요청 데이터 출력
        log.info("handle: {}", request.getHandle());
        log.info("problemId: {}", request.getProblemId());
        log.info("startTime: {}", request.getStartTime());
        log.info("limitTime: {}", request.getLimitTime());
        log.info("language: {}", request.getLanguage());
        log.info("status: {}", request.getStatus());

        // 서비스 로직 호출
        String result = timeService.processSubmission(
                request.getHandle(),
                request.getProblemId(),
                java.time.LocalDateTime.parse(request.getStartTime()),
                request.getLimitTime(),
                request.getLanguage(),
                request.getStatus()
        );

        log.info("결과: {}", result); // 결과 로그 출력
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
