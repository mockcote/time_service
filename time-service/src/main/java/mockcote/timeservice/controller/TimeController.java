package mockcote.timeservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
            @RequestParam(name = "handle") @NotBlank String handle,
            @RequestParam(name = "problemId") @NotNull Integer problemId) {

        String result = timeService.checkSubmissionStatus(handle, problemId);
        return ResponseEntity.ok(result);
    }

    // 로그 저장
    @PostMapping("/save")
    public ResponseEntity<String> submitResult(@RequestBody @Valid SubmissionRequest request) {

        // 서비스 로직 호출
        String result = timeService.saveLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
