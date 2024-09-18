package com.example.demo.repositories;

import com.example.demo.model.EmailHandler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EmailHandlerRepository extends JpaRepository<EmailHandler, Integer> {

    boolean existsByEmailUser(String emailUser);
    Optional<EmailHandler> findByEmailUserAndEmailCode(String emailUser, int emailCode);
    @Transactional
    void deleteByEmailUser(String emailUser);
}
