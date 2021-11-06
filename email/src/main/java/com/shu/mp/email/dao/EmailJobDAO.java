package com.shu.mp.email.dao;

import com.shu.mp.email.po.EmailJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JGod
 * @create 2021-11-16-16:04
 */
@Repository
public interface EmailJobDAO extends JpaRepository<EmailJob, Long> {

    List<EmailJob> findAllBySendTimeBefore(Long now);


}
