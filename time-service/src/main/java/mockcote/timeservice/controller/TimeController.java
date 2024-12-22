package mockcote.timeservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mockcote.timeservice.dto.SubmissionRequest;
import mockcote.timeservice.service.TimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/submissions")
public class TimeController {
  
    private final TimeService timeService;

    @GetMapping("/result")
    public ResponseEntity<String> checkProblemResult(@RequestParam(name = "userId") String userId,
        @RequestParam(name = "problemId") Integer problemId) {

      try {
        String result = timeService.checkSubmissionStatus(userId, problemId);
        return ResponseEntity.ok(result);
      } catch (IllegalStateException e) {
        log.error("IllegalStateException 발생: {}", e.getMessage());
        return ResponseEntity.badRequest().body("오류: " + e.getMessage());
      } catch (Exception e) {
        log.error("예기치 못한 오류 발생: {}", e.getMessage());
        return ResponseEntity.status(500).body("서버 오류 발생");
      }
    }
  
    // 로그 저장
    @PostMapping("/save")
    public ResponseEntity<String> submitResult(@RequestBody SubmissionRequest request) {

        // 서비스 로직 호출
        String result = timeService.saveLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
