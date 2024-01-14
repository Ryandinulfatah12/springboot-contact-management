package dinulfatahryan.belajar.springboot.restfull.api.repository;

import dinulfatahryan.belajar.springboot.restfull.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findFirstByToken(String token);
}
