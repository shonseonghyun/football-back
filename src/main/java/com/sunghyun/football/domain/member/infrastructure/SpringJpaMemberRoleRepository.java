package com.sunghyun.football.domain.member.infrastructure;

import com.sunghyun.football.domain.member.infrastructure.entity.MemberRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringJpaMemberRoleRepository extends JpaRepository<MemberRoleEntity,Long> {
//    List<MemberRoleEntity> findByMemberNo(Long memberNo);
}
