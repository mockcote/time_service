package mockcote.timeservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer problemId;

    @Column(nullable = false, length = 20)
    private String status; // SUCCESS, FAIL

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Integer limitTime; // 분 단위 제한시간

    @Column(nullable = false)
    private Integer duration; // 초 단위 소요시간

    @Column(nullable = false, length = 20)
    private String language;

}
