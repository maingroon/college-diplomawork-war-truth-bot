package ua.borovyk.wartruthbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.borovyk.wartruthbot.constant.RoleType;
import ua.borovyk.wartruthbot.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleType roleType);

}
