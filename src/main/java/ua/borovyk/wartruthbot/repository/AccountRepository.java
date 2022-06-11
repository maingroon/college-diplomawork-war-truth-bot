package ua.borovyk.wartruthbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.borovyk.wartruthbot.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

}
