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

//    @PostMapping("/submit")
//    public ResponseEntity<String> submitResult(@RequestBody SubmissionRequest request) {
//
//        // 요청 데이터 출력 (Slf4j 로그 사용)
//    log.info("userId: {}", request.getUserId());
//    log.info("problemId: {}", request.getProblemId());
//    log.info("startTime: {}", request.getStartTime());
//    log.info("limitTime: {}", request.getLimitTime());
//    log.info("language: {}", request.getLanguage());
//    log.info("submissionResult: {}", request.getSubmissionResult());
//
//        // 서비스 로직 호출
//        String result = timeService.processSubmission(
//                request.getUserId(),
//                request.getProblemId(),
//                java.time.LocalDateTime.parse(request.getStartTime()),
//                request.getLimitTime(),
//                request.getLanguage(),
//                request.getSubmissionResult()
//        );
//    log.info("결과: {}", result); // 결과 로그 출력
//        return ResponseEntity.ok("결과: " + result);
//    }
}
