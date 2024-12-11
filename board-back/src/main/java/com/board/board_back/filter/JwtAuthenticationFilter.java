package com.board.board_back.filter;

import java.io.IOException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.board.board_back.provider.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String token = parseBearerToken(request); //토큰 꺼내오기

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
    
            String email = jwtProvider.validate(token); //이메일 꺼내오기
    
            if (email == null) {
                filterChain.doFilter(request, response);
                return;
            }
    
            AbstractAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES); //사용자 이름, 비밀번호, 권한을 포함할 수 있는 객체
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // request 세부정보 설정, 웹인증세부정보소스
            
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext(); //비어있는 Context 생성
            securityContext.setAuthentication(authenticationToken); //비어있는 Context에 authenticationToken 추가
    
            SecurityContextHolder.setContext(securityContext); //외부에서 사용할 수 있도록 Holder에 다시 담음 

        } catch(Exception exception) {
            exception.printStackTrace();
        }
        
        filterChain.doFilter(request, response);

    }

    private String parseBearerToken(HttpServletRequest request) {
        
        String authorization = request.getHeader("Authorization");

        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization) return null;

        boolean isBearer = authorization.startsWith("Bearer ");
        if(!isBearer) return null;

        String token = authorization.substring(7);
        return token;
    }
    
}