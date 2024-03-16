package com.springjwtmodule.jwt.repository;

import com.springjwtmodule.jwt.domain.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    Boolean existsByRefresh(String refresh);

    void deleteByRefresh(String refresh);
}
