package com.springjwtmodule.entity.refresh;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    boolean existsByRefresh(String refresh);

    boolean deleteByRefresh(String refresh);
}
