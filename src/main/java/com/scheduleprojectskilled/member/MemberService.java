package com.scheduleprojectskilled.member;

import com.scheduleprojectskilled.common.config.PasswordEncoder;
import com.scheduleprojectskilled.common.exception.CustomException;
import com.scheduleprojectskilled.common.exception.ExceptionMessageEnum;
import com.scheduleprojectskilled.member.dto.request.JoinMemberRequestDto;
import com.scheduleprojectskilled.member.dto.request.LoginMemberRequestDto;
import com.scheduleprojectskilled.member.dto.request.MemberDeleteRequestDto;
import com.scheduleprojectskilled.member.dto.request.MemberUpdateRequestDto;
import com.scheduleprojectskilled.member.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 비지니스 로직 처리
     * @param request 회원 가입 정보 파라미터
     * @return JoinMemberResponseDto 데이터 반환
     */
    @Transactional
    public JoinMemberResponseDto createMember(JoinMemberRequestDto request) {
        /**
         * 중복 되는 이메일 예외 처리 조건
         */
        if (memberRepository.existsByMemberEmail(request.getMemberEmail())) {
            throw new CustomException(ExceptionMessageEnum.DUPLICATE_EMAIL);
        }

        String passwordEncoded = passwordEncoder.encode(request.getMemberPassword());

        MemberJoinEntity inputJoinMember = new MemberJoinEntity(
                request.getMemberName(),
                request.getMemberEmail(),
                passwordEncoded
        );

        MemberJoinEntity savedMember = memberRepository.save(inputJoinMember);

        return new JoinMemberResponseDto(
                savedMember.getId(),
                savedMember.getMemberName()
        );
    }

    /**
     * 회원 단건 조회 비지니스 로직 처리
     * @param id 회원 고유 번호 파라미터
     * @return MemberListResponse 데이터 반환
     */
    @Transactional
    public MemberListResponse findOneMember(Long id) {
        MemberJoinEntity member = memberRepository
                .findById(id)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NO_MEMBER_ID)
                );

        return new MemberListResponse(
                member.getId(),
                member.getMemberName(),
                member.getMemberEmail(),
                member.getMemberCondition(),
                member.getCreateDatetime(),
                member.getUpdateDatetime()
        );
    }

    /**
     * 회원 다건 조회 비지니스 로직 처리
     * @return MemberListResponse 데이터 반환
     */
    @Transactional
    public List<MemberListResponse> findAllMember() {
        List<MemberJoinEntity> memberList = memberRepository.findByMemberCondition("member");

        return memberList
                .stream()
                .map(l -> new MemberListResponse (
                    l.getId(),
                    l.getMemberName(),
                    l.getMemberEmail(),
                    l.getMemberCondition(),
                    l.getCreateDatetime(),
                    l.getUpdateDatetime()
                ))
                .toList();
    }

    /**
     * 로그인 비지니스 로직 처리
     * @param request 로그인 입력값 파라미터
     * @return LoginMemberResponseDto 데이터 반환
     */
    @Transactional
    public LoginMemberResponseDto loginMember(LoginMemberRequestDto request) {
        MemberJoinEntity member = memberRepository
                .findByMemberEmail(request.getMemberEmail())
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.EMPTY_EMAIL)
                );
        /**
         * 최종 회원 정보 조회
         */
        boolean passwordMatch = passwordEncoder.matches(request.getMemberPassword(), member.getMemberPassword());

        if (!passwordMatch) {
            throw new CustomException(ExceptionMessageEnum.MEMBER_CHECK);
        }

        return new LoginMemberResponseDto(
                member.getId(),
                member.getMemberName(),
                member.getMemberName() + "님 로그인 되셨습니다."
        );
    }

    /**
     * 회원 정보 업데이트 비지니스 로직
     * @param id 회원 고유 번호 파라미터
     * @param request 업데이트 입력 값 파라미터
     * @return MemberUpdateResponseDto 데이터 반환
     */
    @Transactional
    public MemberUpdateResponseDto updateMember(Long id, MemberUpdateRequestDto request) {
        MemberJoinEntity member = memberRepository
                .findById(id)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NO_MEMBER_ID)
                );

        /**
         * 중복 이메일 검사
         */
        if (member.getMemberEmail().equals(request.getMemberEmail())) {
            throw new CustomException(ExceptionMessageEnum.DUPLICATE_EMAIL);
        }

        member.memberUpdate(
            request.getMemberEmail(),
            request.getMemberPassword()
        );

        return new MemberUpdateResponseDto(
                member.getId(),
                member.getMemberName(),
                member.getMemberName() + "님의 정보가 업데이트 되었습니다.\n다시 로그인 해주세요."
        );
    }

    /**
     * 회원정보 삭제 비지니스 로직 처리
     * @param id 회원 고유 번호 파라미터
     * @param request 회원 정보 삭제 입력값 파라미터
     * @return MemberDeleteResponseDto 데이터 반환
     */
    @Transactional
    public MemberDeleteResponseDto deleteMember(Long id, MemberDeleteRequestDto request) {
        MemberJoinEntity member = memberRepository
                .findById(id)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NO_MEMBER_ID)
                );

        if (!member.getMemberEmail().equals(request.getMemberEmail()) || !member.getMemberPassword().equals(request.getMemberPassword())) {
            throw new CustomException(ExceptionMessageEnum.MEMBER_CHECK);
        }

        /**
         * 바로 삭제하는게 아닌 오늘 날짜 기준 한달후에 삭제하는 데이터 추가
         */
        member.memberDeleteCondition(
                "no_member",
                LocalDateTime.now().plusMonths(1)
        );

        /**
         * 데이터 바로 삭제 로직
         */
        //memberRepository.deleteById(memberId);

        return new MemberDeleteResponseDto(
                member.getId(),
                member.getMemberName(),
                member.getMemberName() + "님의 정보가 1달후에 삭제 됩니다."
        );
    }
}
