package com.qldkhpdao.dao;

import com.qldkhpdao.common.QLDKHPDaoException;
import com.qldkhpdao.common.DaoUtils;
import com.qldkhpdao.dto.EnrollmentDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of EnrollmentDao for database operations.
 *
 * @author B1809367
 */
public class EnrollmentDaoImpl implements EnrollmentDao {

    @Override
    public void insert(EnrollmentDto dto) throws QLDKHPDaoException {
        String query = "INSERT INTO SINHVIEN_LOPHP (MAHP, NHOMHP, HOCKI, NIENKHOA, MASV) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, dto.getSubjectId());
            ps.setString(2, dto.getGroupId());
            ps.setString(3, dto.getSemester());
            ps.setString(4, dto.getSchoolYear());
            ps.setString(5, dto.getStudentId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                DaoUtils.throwDaoException(String.format(
                        "Failed to insert enrollment record: %s, %s, %s, %s, %s",
                        dto.getSubjectId(),
                        dto.getGroupId(),
                        dto.getSemester(),
                        dto.getSchoolYear(),
                        dto.getStudentId()), null);
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException(String.format(
                    "Error inserting enrollment record: %s, %s, %s, %s, %s",
                    dto.getSubjectId(),
                    dto.getGroupId(),
                    dto.getSemester(),
                    dto.getSchoolYear(),
                    dto.getStudentId()), e);
        }
    }

    @Override
    public void delete(String subjectId, String groupId, String semester,
            String schoolYear, String studentId) throws QLDKHPDaoException {

        String query = "DELETE FROM SINHVIEN_LOPHP WHERE MAHP = ? AND NHOMHP = ? AND HOCKI = ? AND NIENKHOA = ? AND MASV = ?";

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, subjectId);
            ps.setString(2, groupId);
            ps.setString(3, semester);
            ps.setString(4, schoolYear);
            ps.setString(5, studentId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                DaoUtils.throwDaoException(String.format(
                        "Failed to delete enrollment record: %s, %s, %s, %s, %s",
                        subjectId, groupId, semester, schoolYear, studentId), null);
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException(String.format(
                    "Error deleting enrollment record: %s, %s, %s, %s, %s",
                    subjectId, groupId, semester, schoolYear, studentId), e);
        }
    }

    @Override
    public List<EnrollmentDto> findList() throws QLDKHPDaoException {
        String query = "SELECT T1.MAHP, T1.NHOMHP, T1.HOCKI, T1.NIENKHOA, T1.MASV, T1.CREATED_AT, T2.TEN_SV FROM SINHVIEN_LOPHP T1 INNER JOIN SINHVIEN T2 ON T2.MASV = T1.MASV";
        List<EnrollmentDto> enrollments = new ArrayList<>();

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                EnrollmentDto dto = new EnrollmentDto();
                dto.setSubjectId(rs.getString("MAHP"));
                dto.setGroupId(rs.getString("NHOMHP"));
                dto.setSemester(rs.getString("HOCKI"));
                dto.setSchoolYear(rs.getString("NIENKHOA"));
                dto.setStudentId(rs.getString("MASV"));
                dto.setCreatedAt(rs.getString("CREATED_AT"));
                dto.setStudentName(rs.getString("TEN_SV"));

                enrollments.add(dto);
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException(
                    "Error fetching all enrollment records", e);
        }
        return enrollments;
    }

    @Override
    public List<EnrollmentDto> findListByCourse(String subjectId,
            String groupId, String semester, String schoolYear)
            throws QLDKHPDaoException {

        String query = "SELECT T1.MAHP, T1.NHOMHP, T1.HOCKI, T1.NIENKHOA, T1.MASV, T1.CREATED_AT, T2.TEN_SV FROM SINHVIEN_LOPHP T1 INNER JOIN SINHVIEN T2 ON T2.MASV = T1.MASV WHERE T1.MAHP = ? AND T1.NHOMHP = ? AND T1.HOCKI = ? AND T1.NIENKHOA = ?";
        List<EnrollmentDto> enrollments = new ArrayList<>();

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, subjectId);
            ps.setString(2, groupId);
            ps.setString(3, semester);
            ps.setString(4, schoolYear);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EnrollmentDto dto = new EnrollmentDto();
                    dto.setSubjectId(rs.getString("MAHP"));
                    dto.setGroupId(rs.getString("NHOMHP"));
                    dto.setSemester(rs.getString("HOCKI"));
                    dto.setSchoolYear(rs.getString("NIENKHOA"));
                    dto.setStudentId(rs.getString("MASV"));
                    dto.setCreatedAt(rs.getString("CREATED_AT"));
                    dto.setStudentName(rs.getString("TEN_SV"));

                    enrollments.add(dto);
                }
            }

        } catch (SQLException e) {
            DaoUtils.throwDaoException(
                    "Error fetching enrollment records by course", e);
        }
        return enrollments;
    }

    @Override
    public List<EnrollmentDto> findListByStudentSemester(String studentId,
            String semester, String schoolYear) throws QLDKHPDaoException {

        String query = "SELECT T1.MAHP, T1.NHOMHP, T1.HOCKI, T1.NIENKHOA, T1.MASV, T1.CREATED_AT, T2.TEN_SV, T3.MAPH, T3.NGAY_HOC, T3.TIET_BAT_DAU, T3.SO_TIET FROM SINHVIEN_LOPHP T1 INNER JOIN SINHVIEN T2 ON T2.MASV = T1.MASV INNER JOIN LOPHP T3 ON T3.MAHP = T1.MAHP AND T3.NHOMHP = T1.NHOMHP AND T3.HOCKI = T1.HOCKI AND T3.NIENKHOA = T1.NIENKHOA WHERE T1.MASV = ? AND T1.HOCKI = ? AND T1.NIENKHOA = ?";
        List<EnrollmentDto> enrollments = new ArrayList<>();

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, studentId);
            ps.setString(2, semester);
            ps.setString(3, schoolYear);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EnrollmentDto dto = new EnrollmentDto();
                    dto.setSubjectId(rs.getString("MAHP"));
                    dto.setGroupId(rs.getString("NHOMHP"));
                    dto.setSemester(rs.getString("HOCKI"));
                    dto.setSchoolYear(rs.getString("NIENKHOA"));
                    dto.setStudentId(rs.getString("MASV"));
                    dto.setCreatedAt(rs.getString("CREATED_AT"));
                    dto.setStudentName(rs.getString("TEN_SV"));
                    dto.setRoomId(rs.getString("MAPH"));
                    dto.setStudyDay(rs.getString("NGAY_HOC"));
                    dto.setStartPeriod(rs.getInt("TIET_BAT_DAU"));
                    dto.setNumberPeriods(rs.getInt("SO_TIET"));

                    enrollments.add(dto);
                }
            }

        } catch (SQLException e) {
            DaoUtils.throwDaoException(String.format(
                    "Error fetching enrollment records by student and semester: %s, %s, %s",
                    studentId, semester, schoolYear), e);
        }
        return enrollments;
    }

    @Override
    public EnrollmentDto findByStudentSubjectSemester(String studentId,
            String subjectId, String semester, String schoolYear)
            throws QLDKHPDaoException {

        String query = "SELECT MAHP, NHOMHP, HOCKI, NIENKHOA, MASV, CREATED_AT FROM SINHVIEN_LOPHP WHERE MASV = ? AND MAHP = ? AND HOCKI = ? AND NIENKHOA = ?";
        EnrollmentDto dto = null;

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, studentId);
            ps.setString(2, subjectId);
            ps.setString(3, semester);
            ps.setString(4, schoolYear);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new EnrollmentDto();
                    dto.setSubjectId(rs.getString("MAHP"));
                    dto.setGroupId(rs.getString("NHOMHP"));
                    dto.setSemester(rs.getString("HOCKI"));
                    dto.setSchoolYear(rs.getString("NIENKHOA"));
                    dto.setStudentId(rs.getString("MASV"));
                    dto.setCreatedAt(rs.getString("CREATED_AT"));
                }
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException(String.format(
                    "Error fetching enrollment record by student, subject, and semester: %s, %s, %s, %s",
                    studentId, subjectId, semester, schoolYear), e);
        }
        return dto;
    }
}
