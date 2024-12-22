package mockcote.timeservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private HttpStatus errorCode; // 에러 코드
    private String errorMessage; // 사용자 친화적인 메시지
}
