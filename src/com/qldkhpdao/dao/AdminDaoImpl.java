package com.qldkhpdao.dao;

import com.qldkhpdao.common.DaoUtils;
import com.qldkhpdao.common.QLDKHPDaoException;
import com.qldkhpdao.dto.AdminDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of AdminDao for database operations.
 *
 * @author B1809367
 */
public class AdminDaoImpl implements AdminDao {

    @Override
    public void insert(AdminDto adminDto) throws QLDKHPDaoException {
        String query = "INSERT INTO QUANTRIVIEN (MAQTV, MAT_KHAU) VALUES (?, ?)";

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, adminDto.getUsername());
            ps.setString(2, adminDto.getPassword());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                DaoUtils.throwDaoException("Failed to insert admin: "
                        + adminDto.getUsername(), null);
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException(
                    "Error inserting admin: " + adminDto.getUsername(), e);
        }
    }

    @Override
    public AdminDto findByUsername(String username) throws QLDKHPDaoException {
        String query = "SELECT MAQTV, MAT_KHAU FROM QUANTRIVIEN WHERE MAQTV = ?";
        AdminDto dto = null;

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new AdminDto();
                    dto.setUsername(rs.getString("MAQTV"));
                    dto.setPassword(rs.getString("MAT_KHAU"));
                }
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException(
                    "Error fetching admin by username: " + username, e);
        }
        return dto;
    }
}
