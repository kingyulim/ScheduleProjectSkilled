package com.scheduleprojectskilled.member;

import com.scheduleprojectskilled.common.exception.CustomException;
import com.scheduleprojectskilled.common.exception.ExceptionMessageEnum;
import com.scheduleprojectskilled.member.dto.request.JoinMemberRequestDto;
import com.scheduleprojectskilled.member.dto.request.LoginMemberRequestDto;
import com.scheduleprojectskilled.member.dto.request.MemberDeleteRequestDto;
import com.scheduleprojectskilled.member.dto.request.MemberUpdateRequestDto;
import com.scheduleprojectskilled.member.dto.response.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입 요청 검증
     * @param request 로그인 입력값 파라미터
     * @return JoinMemberResponseDto json 반환
     */
    @PostMapping("/join")
    public ResponseEntity<JoinMemberResponseDto> createMember(
            @Valid @RequestBody JoinMemberRequestDto request,
            HttpSession session
    ) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        /**
         * 로그인이 돼어 있으면 예외처리
         */
        if (thisSession != null) {
            throw new CustomException(ExceptionMessageEnum.LOGIN_CHECK);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberService.createMember(request));
    }

    /**
     * 로그인 요청 검증
     * @param request 로그인 입력값 파라미터
     * @param session 세션 파라미터
     * @return LoginMemberResponseDto json 반환
     */
    @PostMapping("/login")
    public ResponseEntity<LoginMemberResponseDto> login(
            @Valid @RequestBody LoginMemberRequestDto request,
            HttpSession session
    ) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        /**
         * thisSession 세션 없으면 예외처리
         */
        if (thisSession != null) {
            throw new CustomException(ExceptionMessageEnum.LOGIN_CHECK);
        }

        LoginMemberResponseDto loginMemberResponseDto = memberService.loginMember(request);

        SessionResponse sessionResponse = new SessionResponse (
                loginMemberResponseDto.getId(),
                loginMemberResponseDto.getMemberName()
        );

        /**
         * 세션 생성
         */
        session.setAttribute("thisSession", sessionResponse);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loginMemberResponseDto);
    }

    /**
     * 회원 단건 조회 요청 검증
     * @param id 회원 고유 번호 파라미터
     * @param session 세션 파라미터
     * @return MemberListResponse json 반환
     */
    @GetMapping("/memberList/{id}")
    public ResponseEntity<MemberListResponse> findOneMember(
            @PathVariable("id") Long id,
            HttpSession session
    ) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        /**
         * thisSession 세션 없으면 예외처리
         */
        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.NO_LOGIN);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.findOneMember(id));
    }

    /**
     * 회원 다건 조회 요청 검증
     * @param session 세션 파라미터
     * @return map 형식의 키값 memberList 의 List<MemberListResponse> 반환
     */
    @GetMapping("/memberList")
    public ResponseEntity<Map<String, List<MemberListResponse>>> findAllMember(HttpSession session) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        /**
         * thisSession 세션 없으면 예외처리
         */
        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.NO_LOGIN);
        }

        List<MemberListResponse> memberList = memberService.findAllMember();

        Map<String, List<MemberListResponse>> memberMap = Map.of("memberList", memberList);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberMap);
    }

    /**
     * 업데이트 요청 검증
     * @param id 회원 고유 번호 파라미터
     * @param request 업데이트 입력값 파라미터
     * @param session 세션
     * @return MemberUpdateResponseDto json 반환
     */
    @PutMapping("memberUpdate/{id}")
    public ResponseEntity<MemberUpdateResponseDto> updateMember(
            @PathVariable("id") Long id,
            @Valid @RequestBody MemberUpdateRequestDto request,
            HttpSession session
    ){
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        /**
         * 로그인 확인 예외처리
         */
        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.NO_LOGIN);
        }

        /**
         * 파라미터 키값 일치 예외처리
         */
        if (!thisSession.getId().equals(id)) {
            throw new CustomException(ExceptionMessageEnum.NO_MEMBER_KEY_MATCHING);
        }

        MemberUpdateResponseDto updateResponse = memberService.updateMember(id, request);

        /**
         * 회원정보가 변경 됐으니 세션 제거
         */
        session.invalidate();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updateResponse);
    }

    /**
     * 회원 삭제 요청 검증
     * @param id 회원 고유 번호 파라미터
     * @param request 삭제 회원 입력값 파라미터
     * @param session 세션 파라미터
     * @return MemberDeleteResponseDto json 반환
     */
    @DeleteMapping("memberDelete/{id}")
    public ResponseEntity<MemberDeleteResponseDto> deleteMember(
            @PathVariable("id") Long id,
            @Valid @RequestBody MemberDeleteRequestDto request,
            HttpSession session
    ){
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        /**
         * 로그인 확인 예외처리
         */
        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.NO_LOGIN);
        }

        /**
         * 파라미터 키값 일치 예외처리
         */
        if (!thisSession.getId().equals(id)) {
            throw new CustomException(ExceptionMessageEnum.NO_MEMBER_KEY_MATCHING);
        }

        MemberDeleteResponseDto deleteResponse = memberService.deleteMember(thisSession.getId(), request);

        /**
         * 세션 삭제
         */
        session.invalidate();

        return ResponseEntity
               .status(HttpStatus.OK)
               .body(deleteResponse);
    }

    /**
     * 로그아웃 요청 검증
     * @param session 세션 파라미터
     * @return 로그인 했던 회원 이름 반환
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        /**
         * 로그인 확인 예외처리
         */
        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.LOGIN_CHECK);
        }

        String sessionName = thisSession.getMemberName();

        /**
         * 세션 제거
         */
        session.invalidate();

        return ResponseEntity.status(HttpStatus.OK).body(sessionName + "님 로그아웃 되었습니다.");
        //return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 세션 조회 검증
     * @param session 세션 파라미터
     * @return SessionResponse json 반환
     */
    @PostMapping("/sessionMember")
    public ResponseEntity<SessionResponse> sessionCheck(HttpSession session){
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.NO_SESSION);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(thisSession);
    }
}
