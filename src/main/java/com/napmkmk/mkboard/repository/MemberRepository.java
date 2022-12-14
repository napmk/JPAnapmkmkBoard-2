package com.napmkmk.mkboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.napmkmk.mkboard.entity.SiteMember;

public interface MemberRepository extends JpaRepository<SiteMember, Integer> {

}
