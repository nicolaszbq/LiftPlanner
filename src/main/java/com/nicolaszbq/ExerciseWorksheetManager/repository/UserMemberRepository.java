package com.nicolaszbq.ExerciseWorksheetManager.repository;

import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMemberRepository extends JpaRepository<UserMember, String> {

}
