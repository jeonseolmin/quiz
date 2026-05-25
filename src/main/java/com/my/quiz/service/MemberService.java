package com.my.quiz.service;

import com.my.quiz.dto.MemberDto;
import com.my.quiz.dto.MemberStatus;
import com.my.quiz.dto.RoleType;
import com.my.quiz.entity.Member;
import com.my.quiz.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    //회원가입
    public void signup(MemberDto dto){
        Member member = new Member();
        member.setLoginId(dto.getLoginId());
        member.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );
        member.setRole(dto.getRole());
        member.setStatus(dto.getStatus());
        member.setAnswerTrue(0);
        member.setAnswerFalse(0);
        memberRepository.save(member);
    }

    //로그인
    public MemberDto login(MemberDto dto){
        MemberDto loginDto = findByLoginId(dto.getLoginId());
        //널 또는 찾아오거나
        //회원 없으면 그냥 return null;
        if(loginDto == null) return null;
        //비밀번호를 암호해제 비교
        boolean matches = passwordEncoder.matches(
                // 홈에서 받아온 비밀번호
                dto.getPassword(),
                loginDto.getPassword()
        );
        return matches ? loginDto : null;
    }

    // 내 비밀번호 수정
    public void updatePassword(MemberDto dto){
        Member member = memberRepository.findByLoginId(dto.getLoginId()).orElse(null);
        member.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );
        memberRepository.save(member);
    }

    public List<MemberDto> findAll(){
        return memberRepository
                .findByRoleNot(RoleType.ADMIN)
                .stream()
                .map(MemberDto::toEntity)
                .toList();
    }

    public MemberDto findById(Long no){
        Member member = memberRepository.findById(no).orElse(null);
        return member == null ? null : MemberDto.toEntity(member);
    }

    public MemberDto findByLoginId(String loginId){
        Member member = memberRepository.findByLoginId(loginId).orElse(null);
        return member == null ? null : MemberDto.toEntity(member);
    }


    @Transactional
    public void changeState(String loginId,
                            MemberStatus status){

        Member member =
                memberRepository.findByLoginId(loginId)
                        .orElseThrow();

        member.setStatus(status);
    }
}
