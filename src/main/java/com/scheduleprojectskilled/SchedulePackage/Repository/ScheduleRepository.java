package com.scheduleprojectskilled.SchedulePackage.Repository;

import com.scheduleprojectskilled.SchedulePackage.Entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
}
