package com.scheduleprojectskilled.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    /**
     * 내가 쓴 글 고유 번호 와 고유 번호에 해당되는 이름으로 찾는 query methode
     * @param memberId 회원 고유 번호
     * @param wriName 회원 고유 번호와 같은 이름
     * @return query return
     */
    List<ScheduleEntity> findByMemberIdAndWriName(Long memberId, String wriName);

}
