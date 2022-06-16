package ua.borovyk.wartruthbot.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.borovyk.wartruthbot.constant.RoleType;
import ua.borovyk.wartruthbot.entity.Account;
import ua.borovyk.wartruthbot.entity.Role;
import ua.borovyk.wartruthbot.repository.AccountRepository;
import ua.borovyk.wartruthbot.repository.RoleRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;
    RoleRepository roleRepository;
    BCryptPasswordEncoder passwordEncoder;

    public void createNewModerator(Account account) {
        Objects.requireNonNull(account);
        var moderatorRole = roleRepository.findByName(RoleType.MODERATOR)
                .orElseThrow();
        account.setRoles(Set.of(moderatorRole));
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        accountRepository.save(account);
    }

    public Optional<Account> getAccountByUsername(String username) {
        Objects.requireNonNull(username);
        return accountRepository.findByUsername(username);
    }

}
