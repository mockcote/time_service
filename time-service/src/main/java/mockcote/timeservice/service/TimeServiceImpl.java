package mockcote.timeservice.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mockcote.timeservice.utils.BaekjoonCrawler;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService{
	
	private final BaekjoonCrawler baekjoonCrawler;
	
	@Override
	public String checkSubmissionStatus(String userId, Integer problemId) throws Exception {
        return baekjoonCrawler.checkSubmissionStatus(userId, problemId);
    }

}
