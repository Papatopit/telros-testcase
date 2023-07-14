package com.telros.telrostestcase.repository;

import com.telros.telrostestcase.model.Token;
import com.telros.telrostestcase.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    boolean existsByToken(String token);
    void deleteByUser(User user);
}
