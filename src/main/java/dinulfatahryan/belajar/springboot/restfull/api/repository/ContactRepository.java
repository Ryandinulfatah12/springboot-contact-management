package dinulfatahryan.belajar.springboot.restfull.api.repository;

import dinulfatahryan.belajar.springboot.restfull.api.entity.Contact;
import dinulfatahryan.belajar.springboot.restfull.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, String>, JpaSpecificationExecutor<Contact> {
    Optional<Contact> findFirstByUserAndId(User user, String id);
}
