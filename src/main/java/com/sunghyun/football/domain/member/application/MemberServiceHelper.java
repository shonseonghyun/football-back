package com.sunghyun.football.domain.member.application;

import com.sunghyun.football.domain.member.domain.Member;
import com.sunghyun.football.domain.member.domain.repository.MemberRepository;
import com.sunghyun.football.domain.member.infrastructure.auth.UserDetails.CustomUserDetails;
import com.sunghyun.football.global.exception.ErrorCode;
import com.sunghyun.football.global.exception.exceptions.member.MemberNotFoundException;
import com.sunghyun.football.global.exception.exceptions.member.auth.EmailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceHelper implements UserDetailsService {
    private final MemberRepository memberRepository;

    public Member findExistingMember(Long memberNo){
        Member selectedMember = memberRepository.findByMemberNo(memberNo)
                .orElseThrow(()-> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        return selectedMember;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final String email = username;

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new EmailNotFoundException(ErrorCode.EMAIL_NOT_FOUND.getCode())); //auth handler에서 잡힌다.

        return CustomUserDetails.from(member);
    }
}
