package mockcote.timeservice.service;

import lombok.RequiredArgsConstructor;
import mockcote.timeservice.model.Logs;
import mockcote.timeservice.repository.LogsRepository;
import mockcote.timeservice.utils.BaekjoonCrawler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

public interface TimeService {

	// 풀이여부체크
    String checkSubmissionStatus(String userId, Integer problemId) throws Exception;

}
