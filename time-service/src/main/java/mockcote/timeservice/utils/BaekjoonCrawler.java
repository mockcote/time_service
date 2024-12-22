package mockcote.timeservice.utils;

import lombok.extern.slf4j.Slf4j;
import mockcote.timeservice.exception.CustomException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class BaekjoonCrawler {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

    public String checkSubmissionStatus(String handle, Integer problemId) {
        // 크롤링 대상 URL
        String url = "https://www.acmicpc.net/status?problem_id=" + problemId +
                        "&user_id=" + handle + "&language_id=-1&result_id=-1";

        // Jsoup으로 HTML 가져오기
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .get();
        } catch (IOException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "크롤링 에러");
        }

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

            // 풀이 여부 확인
            if (result.equals("맞았습니다!!") || result.equals("100점")) {
                return "SUCCESS";
            } else {
                return "FAIL";
            }
        }

        // 결과가 없을 경우 예외 처리
        return "PENDING"; // 결과가 "맞았습니다!!" 또는 "틀렸습니다!!"가 아닐 경우
    }

}
