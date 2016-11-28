package by.bsu.rikz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import by.bsu.rikz.entity.User;

@NoRepositoryBean
public interface UserBaseRepository<U extends User> extends JpaRepository<U, Long> {

}