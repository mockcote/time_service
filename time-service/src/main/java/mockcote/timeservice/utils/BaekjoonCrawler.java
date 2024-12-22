package mockcote.timeservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BaekjoonCrawler {
	
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

    //private static final String BASE_URL = "https://www.acmicpc.net/status";
    
    public String checkSubmissionStatus(String handle, Integer problemId) throws Exception {
        // 크롤링 대상 URL
        String url = "https://www.acmicpc.net/status?problem_id=" + problemId +
                     "&user_id=" + handle + "&language_id=-1&result_id=-1";

        // Jsoup으로 HTML 가져오기
        Document document = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .get();

        // HTML 테이블에서 결과 찾기
        Element table = document.getElementById("status-table");
        if (table == null) {
            throw new IllegalStateException("Status table not found on the page.");
        }

        // 테이블 행 데이터 파싱
        Elements rows = table.select("tbody tr");
        if (rows.isEmpty()) {
            return "NO_RECORD"; // 행이 없는 경우
        }
        for (Element row : rows) {
            String result = row.select("td").get(3).text(); // 결과 열 (4번째 열)

            // "맞았습니다!!" 또는 "틀렸습니다!!" 확인
            if (result.equals("맞았습니다!!")) {
                return "SUCCESS";
            } else if (result.equals("틀렸습니다")) {
                return "FAIL";
            }
        }

        // 결과가 없을 경우 예외 처리
        return "PENDING"; // 결과가 "맞았습니다!!" 또는 "틀렸습니다!!"가 아닐 경우
    }

//    public String checkSubmissionStatus(String userId, Integer problemId) {
//        try {
//            // 백준 채점 현황 페이지 URL 구성
//            String url = BASE_URL + "?user_id=" + userId + "&problem_id=" + problemId;
//
//            // Jsoup을 사용하여 페이지 가져오기
//            Document document = Jsoup.connect(url).get();
//            log.info("document {}",document);
//            // 채점 결과를 표시하는 첫 번째 결과 행 추출
//            Element resultRow = document.selectFirst("tr#status-table tbody tr");
//
//            if (resultRow == null) {
//                return "NO_RECORD"; // 제출 기록이 없는 경우
//            }
//
//            // 결과 컬럼 (채점 결과) 추출
//            Element resultElement = resultRow.selectFirst(".result");
//
//            if (resultElement == null) {
//                return "NO_RECORD";
//            }
//
//            String resultText = resultElement.text();
//
//            // 결과에 따른 반환
//            if (resultText.contains("맞았습니다!!")) {
//                return "SUCCESS";
//            } else if (resultText.contains("틀렸습니다")) {
//                return "FAIL";
//            } else {
//                return "PENDING"; // 채점 중인 경우
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("크롤링 중 오류 발생: " + e.getMessage());
//        }
//    }
}
