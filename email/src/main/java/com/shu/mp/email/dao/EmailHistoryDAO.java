package com.shu.mp.email.dao;

import com.shu.mp.email.po.EmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author JGod
 * @create 2021-11-16-16:04
 */
@Repository
public interface EmailHistoryDAO extends JpaRepository<EmailHistory, Long> {
}
