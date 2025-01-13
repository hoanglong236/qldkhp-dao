package com.qldkhpdao.dao;

import com.qldkhpdao.common.QLDKHPDaoException;
import com.qldkhpdao.dto.AdminDto;

/**
 * DAO interface for admin-related database operations.
 *
 * @author B1809367
 */
public interface AdminDao {

    void insert(AdminDto adminDto) throws QLDKHPDaoException;

    AdminDto findByUsername(String username) throws QLDKHPDaoException;
}
