package com.sparta.kd.coursedelivery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JwtTokenService {

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String createToken(String subject) {
        String token = jwtUtil.generateToken(subject);

        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setSubject(subject);
        jwtToken.setExpiration(LocalDateTime.now().plusHours(10));

        jwtTokenRepository.save(jwtToken);

        return token;
    }

    public boolean validateToken(String token) {
        return jwtTokenRepository.findByToken(token)
                .map(jwt -> jwtUtil.validateToken(token, jwt.getSubject()))
                .orElse(false);
    }
}

