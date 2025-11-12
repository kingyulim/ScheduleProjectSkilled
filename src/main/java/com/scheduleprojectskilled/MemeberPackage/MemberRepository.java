package com.scheduleprojectskilled.MemeberPackage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberJoinEntity, Long> {
    /**
     * 이메일 중복 검사 query
     * @param memberEmail 입력된 이메일
     * @return 중복이 있다면 에러 메세지 반환
     */
    boolean existsByMemberEmail(String memberEmail);

    /**
     * 이메일 존재 확인 query
     * @param memberEmail 입력된 이메일
     * @return 입력된 값이 비어있거나 데이터에 존재 하지 않는다면 에러 메세지 반환
     */
    Optional<MemberJoinEntity> findByMemberEmail(String memberEmail);
}
