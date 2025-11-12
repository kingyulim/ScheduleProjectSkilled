package com.scheduleprojectskilled.MemeberPackage;

import com.scheduleprojectskilled.Config.ExceptionMessageEnum;
import com.scheduleprojectskilled.MemeberPackage.Dto.Request.LoginRequest;
import com.scheduleprojectskilled.MemeberPackage.Dto.Request.MemberJoinRequest;
import com.scheduleprojectskilled.MemeberPackage.Dto.Request.MemberUpdateRequest;
import com.scheduleprojectskilled.MemeberPackage.Dto.Response.FindMemberResponse;
import com.scheduleprojectskilled.MemeberPackage.Dto.Response.LoginResponse;
import com.scheduleprojectskilled.MemeberPackage.Dto.Response.MemberJoinResponse;
import com.scheduleprojectskilled.MemeberPackage.Dto.Response.MemeberUpdateResponse;
import com.scheduleprojectskilled.MemeberPackage.Exception.NoFindMemberId;
import com.scheduleprojectskilled.MemeberPackage.Exception.NoMemberException;
import com.scheduleprojectskilled.MemeberPackage.Exception.SameEmilException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입 비지니스 로직 처리
     * @param request 입력값 파라미터
     * @return Controller에 보여줄 MemberJoinResponse반환
     */
    @Transactional
    public MemberJoinResponse memberJoin(MemberJoinRequest request) {
        MemberJoinEntity memberJoin = new MemberJoinEntity(
                request.getMemberName(),
                request.getMemberEmail(),
                request.getMemberPassword()
        );

        if (memberRepository.existsByMemberEmail(request.getMemberEmail())) {
            throw new SameEmilException("중복되는 이메일이 존재합니다");
        }

        MemberJoinEntity memberJoinSave = memberRepository.save(memberJoin);

        return new MemberJoinResponse(
                memberJoinSave.getId(),
                memberJoin.getMemberName(),
                memberJoin.getMemberEmail(),
                memberJoin.getCreateDatetime(),
                memberJoin.getUpdateDatetime()
        );
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        MemberJoinEntity findMember = memberRepository
                .findByMemberEmail(request.getMemberEmail())
                .orElseThrow(
                    () -> new NoMemberException("등록된 이메일이 아닙니다.")
                );

        if (!findMember.getMemberPassword().equals(request.getMemberPassword())) {
            throw new NoMemberException(ExceptionMessageEnum.NO_MEMBER_MESSAGE.getMessage());
        }

        return new LoginResponse(
                findMember.getId(),
                findMember.getMemberName(),
                findMember.getMemberEmail()
        );
    }

    /**
     * 회원 정보 단건 조회 비지니스 로직 처리
     * @param id 회원 고유 번호
     * @return 고유번호에 맞는 회원 정보 반환
     */
    @Transactional(readOnly = true)
    public FindMemberResponse findMember(Long id) {
        MemberJoinEntity findMember = memberRepository
                .findById(id)
                .orElseThrow(
                        () -> new NoFindMemberId("입력하신 번호에 해당되는 회원이 없습니다.")
                );

        return new FindMemberResponse(
                findMember.getId(),
                findMember.getMemberName(),
                findMember.getMemberEmail(),
                findMember.getCreateDatetime(),
                findMember.getUpdateDatetime()
        );
    }

    /**
     * 회원 정보 다건 조회 비지니스 로직 처리
     * @return 저장된 모든 회원 반환
     */
    @Transactional(readOnly = true)
    public List<FindMemberResponse> findAllMember() {
       List<MemberJoinEntity> members = memberRepository.findAll();

       return members.stream()
               .map(m -> new FindMemberResponse(
                       m.getId(),
                       m.getMemberName(),
                       m.getMemberEmail(),
                       m.getCreateDatetime(),
                       m.getUpdateDatetime()
               ))
               .toList();
    }

    @Transactional
    public MemeberUpdateResponse memberUpdate(Long id, MemberUpdateRequest request) {
        MemberJoinEntity member = memberRepository
                .findById(id)
                .orElseThrow(
                        () -> new NoFindMemberId(ExceptionMessageEnum.NO_MEMBER_MESSAGE.getMessage())
                );

        if (memberRepository.existsByMemberEmail(request.getMemberEmail())) {
            throw new NoMemberException("중복되는 이메일이 있습니다.");
        }

        member.memberUpdate(
                request.getMemberEmail(),
                request.getMemberPassword()
        );

        return new MemeberUpdateResponse(
                member.getId(),
                member.getMemberName(),
                member.getMemberEmail(),
                member.getUpdateDatetime()
        );
    }
}
