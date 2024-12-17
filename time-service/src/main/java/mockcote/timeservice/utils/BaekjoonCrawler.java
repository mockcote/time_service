package mockcote.timeservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BaekjoonCrawler {

    private static final String BASE_URL = "https://www.acmicpc.net/status";

    public String checkSubmissionStatus(String userId, Integer problemId) {
        try {
            // 백준 채점 현황 페이지 URL 구성
            String url = BASE_URL + "?user_id=" + userId + "&problem_id=" + problemId;

            // Jsoup을 사용하여 페이지 가져오기
            Document document = Jsoup.connect(url).get();
            log.info("document {}",document);
            // 채점 결과를 표시하는 첫 번째 결과 행 추출
            Element resultRow = document.selectFirst("tr#status-table tbody tr");

            if (resultRow == null) {
                return "NO_RECORD"; // 제출 기록이 없는 경우
            }

            // 결과 컬럼 (채점 결과) 추출
            Element resultElement = resultRow.selectFirst(".result");

            if (resultElement == null) {
                return "NO_RECORD";
            }

            String resultText = resultElement.text();

            // 결과에 따른 반환
            if (resultText.contains("맞았습니다!!")) {
                return "SUCCESS";
            } else if (resultText.contains("틀렸습니다")) {
                return "FAIL";
            } else {
                return "PENDING"; // 채점 중인 경우
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("크롤링 중 오류 발생: " + e.getMessage());
        }
    }
}
