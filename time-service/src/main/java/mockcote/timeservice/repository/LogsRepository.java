package mockcote.timeservice.repository;

import mockcote.timeservice.model.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {
    // 추가적으로 사용자 ID나 문제 ID별로 조회할 메서드를 선언할 수 있습니다.
    // 예시: List<Logs> findByUserId(Integer userId);
}
