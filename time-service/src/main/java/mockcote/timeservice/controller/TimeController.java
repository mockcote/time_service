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

//    @GetMapping("/result")
//    public ResponseEntity<String> checkProblemResult(
//            @RequestParam String userId,
//            @RequestParam Integer problemId) {
//
//        String result = timeService.checkProblemResult(userId, problemId);
//
//        return ResponseEntity.ok("채점 결과: " + result);
//    }

    // 로그 저장
    @PostMapping("/save")
    public ResponseEntity<String> submitResult(@RequestBody SubmissionRequest request) {

        // 서비스 로직 호출
        String result = timeService.saveLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
