package com.sparta.kd.coursedelivery.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

  Optional<JwtToken> findByToken(String token);
}