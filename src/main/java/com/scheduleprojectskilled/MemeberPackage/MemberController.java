package com.scheduleprojectskilled.MemeberPackage;

import com.scheduleprojectskilled.MemeberPackage.Dto.Request.LoginRequest;
import com.scheduleprojectskilled.MemeberPackage.Dto.Request.MemberJoinRequest;
import com.scheduleprojectskilled.MemeberPackage.Dto.Request.MemberUpdateRequest;
import com.scheduleprojectskilled.MemeberPackage.Dto.Response.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원 테이블 검사 생성 로직
     * @param request 회원가입 데이터 입력값 파라미터
     * @return MemberJoinResponse json반환
     */
    @PostMapping("/members")
    public ResponseEntity<MemberJoinResponse> memberJoin(@Valid @RequestBody MemberJoinRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberService.memberJoin(request));
    }

    /**
     * 회원 단건 조회 검사 로직
     * @param id 회원 고유 번호 파라미터
     * @return FindMemberResponse json 반환
     */
    @GetMapping("/members/{id}")
    public ResponseEntity<FindMemberResponse> findOneMember(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.findMember(id));
    }

    /**
     * 회원 다건 조회 검사 로직
     * @return 모든 회원 정보 반환
     */
    @GetMapping("/members")
    public ResponseEntity<List<FindMemberResponse>> findAllMembers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.findAllMember());
    }

    /**
     * 회원 정보 업데이트 검사 로직
     * @param id 회원 고유 번호 파라미터
     * @param request 입력된 값 파라미터
     * @param session 세션 파라미터
     * @return 수정된 유저 이름 반환
     */
    @PutMapping("/members/{id}")
    public ResponseEntity<String> updateMember(@PathVariable("id") Long id, @Valid @RequestBody MemberUpdateRequest request, HttpSession session) {
        SessionResponse sessionUser = (SessionResponse) session.getAttribute("thisLoginMember");
        if (sessionUser == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        MemeberUpdateResponse memberData = memberService.memberUpdate(id, request);

        session.invalidate(); // 회원정보 변경 로그아웃

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberData.getMemberName() + "님의 정보가 수정 되었습니다,\n다시 로그인 해주세요.");
    }

    /**
     * 로그인 검사 로직
     * @param request 로그인 정보 입력값 파라미터
     * @param session 세션 파라미터
     * @return 로그인이 된 회원 정보 반환
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session
        ) {
        SessionResponse sessionMember = (SessionResponse) session.getAttribute("thisLoginMember");
        if (sessionMember != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("이미 로그인 상태입니다.");
        }

        LoginResponse member = memberService.login(request);

        SessionResponse sessionUser = new SessionResponse(
                member.getId(),
                member.getMemberName(),
                member.getMemberEmail()
        );

        session.setAttribute("thisLoginMember", sessionUser);

        LoginResponse response = new LoginResponse(
                member.getId(),
                member.getMemberName(),
                member.getMemberEmail()
        );

        return ResponseEntity.status(HttpStatus.OK).body(member.getMemberName() + "님 로그인 되었습니다.");
    }

    /**
     * 로그아웃 로직
     * @param session 세션 파라미터
     * @return 세션 종료 문구 반환
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        SessionResponse sessionResponse = (SessionResponse) session.getAttribute("thisLoginMember");
        if (sessionResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("세션이 존재하지 않습니다.");
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body("세션이 종료되었습니다.");
    }

    /**
     * 세션 확인 검사 로직
     * @param sessionMember 브라우저에 남겨진 세션 json 파라미터
     * @return 세션 정보 반환
     */
    @PostMapping("/sessionMember")
    public ResponseEntity<String> sesionMember(@SessionAttribute(name = "thisLoginMember") SessionResponse sessionMember) {
        if (sessionMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body("회원 고유 번호: " + sessionMember.getId().toString());
    }
}
