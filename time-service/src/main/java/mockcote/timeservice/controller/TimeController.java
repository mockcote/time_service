package mockcote.timeservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mockcote.timeservice.dto.SubmissionRequest;
import mockcote.timeservice.dto.TimeStartRequest;
import mockcote.timeservice.service.TimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/submissions")
public class TimeController {

    private final TimeService timeService;

    // 문제 풀이 시작
    @PostMapping("/start")
    public ResponseEntity<?> timeStart(@RequestBody @Valid TimeStartRequest request) {
        LocalDateTime startTime = timeService.timeStart(request.getHandle(), LocalDateTime.now());
        return ResponseEntity.ok(startTime.toString());
    }

    // 문제 풀이 종료
    @PostMapping("/end")
    public ResponseEntity<?> timeEnd(@RequestBody @Valid TimeStartRequest request) {
        timeService.timeEnd(request.getHandle());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 풀이 여부 확인
    @GetMapping("/result")
    public ResponseEntity<String> checkProblemResult(
            @RequestParam(name = "handle") @NotBlank String handle,
            @RequestParam(name = "problemId") @NotNull Integer problemId) {

        String result = timeService.checkSubmissionStatus(handle, problemId);
        return ResponseEntity.ok(result);
    }

    // 로그 저장
    @PostMapping("/save")
    public ResponseEntity<?> submitResult(@RequestBody @Valid SubmissionRequest request) {
        String result = timeService.saveLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
