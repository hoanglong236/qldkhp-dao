package com.qldkhpdao.dao;

import com.qldkhpdao.common.QLDKHPDaoException;
import com.qldkhpdao.common.DaoUtils;
import com.qldkhpdao.dto.CourseDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CourseDao for database operations.
 *
 * @author B1809367
 */
public class CourseDaoImpl implements CourseDao {

    @Override
    public void insert(CourseDto courseDto) throws QLDKHPDaoException {
        String query = "INSERT INTO LOPHP (MAHP, NHOMHP, HOCKI, NIENKHOA, MACB, MAPH, NGAY_HOC, TIET_BAT_DAU, SO_TIET, SI_SO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, courseDto.getSubjectId());
            ps.setString(2, courseDto.getGroupId());
            ps.setString(3, courseDto.getSemester());
            ps.setString(4, courseDto.getSchoolYear());
            ps.setString(5, courseDto.getLectureId());
            ps.setString(6, courseDto.getRoomId());
            ps.setString(7, courseDto.getStudyDay());
            ps.setInt(8, courseDto.getStartPeriod());
            ps.setInt(9, courseDto.getNumberPeriods());
            ps.setInt(10, courseDto.getMaxEnrollment());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                DaoUtils.throwDaoException(String.format(
                        "Failed to insert course: %s, %s, %s, %s",
                        courseDto.getSubjectId(),
                        courseDto.getGroupId(),
                        courseDto.getSemester(),
                        courseDto.getSchoolYear()), null);
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException(String.format(
                    "Error inserting course: %s, %s, %s, %s",
                    courseDto.getSubjectId(),
                    courseDto.getGroupId(),
                    courseDto.getSemester(),
                    courseDto.getSchoolYear()), e);
        }
    }

    @Override
    public void update(CourseDto courseDto) throws QLDKHPDaoException {
        String query = "UPDATE LOPHP SET MACB = ?, MAPH = ?, NGAY_HOC = ?, TIET_BAT_DAU = ?, SO_TIET = ?, SI_SO = ? WHERE MAHP = ? AND NHOMHP = ? AND HOCKI = ? AND NIENKHOA = ?";

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, courseDto.getLectureId());
            ps.setString(2, courseDto.getRoomId());
            ps.setString(3, courseDto.getStudyDay());
            ps.setInt(4, courseDto.getStartPeriod());
            ps.setInt(5, courseDto.getNumberPeriods());
            ps.setInt(6, courseDto.getMaxEnrollment());
            ps.setString(7, courseDto.getSubjectId());
            ps.setString(8, courseDto.getGroupId());
            ps.setString(9, courseDto.getSemester());
            ps.setString(10, courseDto.getSchoolYear());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                DaoUtils.throwDaoException(String.format(
                        "Failed to update course: %s, %s, %s, %s",
                        courseDto.getSubjectId(),
                        courseDto.getGroupId(),
                        courseDto.getSemester(),
                        courseDto.getSchoolYear()), null);
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException(String.format(
                    "Error updating course: %s, %s, %s, %s",
                    courseDto.getSubjectId(),
                    courseDto.getGroupId(),
                    courseDto.getSemester(),
                    courseDto.getSchoolYear()), e);
        }
    }

    @Override
    public void delete(String subjectId, String groupId,
            String semester, String schoolYear) throws QLDKHPDaoException {

        String query = "DELETE FROM LOPHP WHERE MAHP = ? AND NHOMHP = ? AND HOCKI = ? AND NIENKHOA = ?";

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, subjectId);
            ps.setString(2, groupId);
            ps.setString(3, semester);
            ps.setString(4, schoolYear);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                DaoUtils.throwDaoException(String.format(
                        "Failed to delete course: %s, %s, %s, %s",
                        subjectId, groupId, semester, schoolYear), null);
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException(String.format(
                    "Error deleting course: %s, %s, %s, %s",
                    subjectId, groupId, semester, schoolYear), e);
        }
    }

    @Override
    public int count() throws QLDKHPDaoException {
        String query = "SELECT COUNT(*) AS SO_LUONG_LOPHP FROM LOPHP";
        int count = 0;

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                count = rs.getInt("SO_LUONG_LOPHP");
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException("Error counting courses", e);
        }
        return count;
    }

    @Override
    public List<CourseDto> findList() throws QLDKHPDaoException {
        String query = "SELECT T1.MAHP, T1.NHOMHP, T1.HOCKI, T1.NIENKHOA, T1.MACB, T1.MAPH, T1.NGAY_HOC, T1.TIET_BAT_DAU, T1.SO_TIET, T1.SI_SO, COUNT(T2.MASV) AS SO_LUONG_SV_DK FROM LOPHP T1 LEFT JOIN SINHVIEN_LOPHP T2 ON T1.MAHP = T2.MAHP AND T1.NHOMHP = T2.NHOMHP AND T1.HOCKI = T2.HOCKI AND T1.NIENKHOA = T2.NIENKHOA GROUP BY T1.MAHP, T1.NHOMHP, T1.NIENKHOA, T1.HOCKI ORDER BY T1.NIENKHOA, T1.HOCKI, T1.MAHP, T1.NHOMHP";
        List<CourseDto> courses = new ArrayList<>();

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                CourseDto dto = new CourseDto();
                dto.setSubjectId(rs.getString("MAHP"));
                dto.setGroupId(rs.getString("NHOMHP"));
                dto.setSemester(rs.getString("HOCKI"));
                dto.setSchoolYear(rs.getString("NIENKHOA"));
                dto.setLectureId(rs.getString("MACB"));
                dto.setRoomId(rs.getString("MAPH"));
                dto.setStudyDay(rs.getString("NGAY_HOC"));
                dto.setStartPeriod(rs.getInt("TIET_BAT_DAU"));
                dto.setNumberPeriods(rs.getInt("SO_TIET"));
                dto.setMaxEnrollment(rs.getInt("SI_SO"));
                dto.setCurrentEnrollment(rs.getInt("SO_LUONG_SV_DK"));

                courses.add(dto);
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException("Error fetching all courses", e);
        }
        return courses;
    }

    @Override
    public List<CourseDto> findListBySemester(String semester, String schoolYear)
            throws QLDKHPDaoException {

        String query = "SELECT T1.MAHP, T1.NHOMHP, T1.HOCKI, T1.NIENKHOA, T1.MACB, T1.MAPH, T1.NGAY_HOC, T1.TIET_BAT_DAU, T1.SO_TIET, T1.SI_SO, COUNT(T2.MASV) AS SO_LUONG_SV_DK FROM LOPHP T1 LEFT JOIN SINHVIEN_LOPHP T2 ON T1.MAHP = T2.MAHP AND T1.NHOMHP = T2.NHOMHP AND T1.HOCKI = T2.HOCKI AND T1.NIENKHOA = T2.NIENKHOA WHERE T1.HOCKI = ? AND T1.NIENKHOA = ? GROUP BY T1.MAHP, T1.NHOMHP, T1.NIENKHOA, T1.HOCKI ORDER BY T1.MAHP, T1.NHOMHP";
        List<CourseDto> courses = new ArrayList<>();

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, semester);
            ps.setString(2, schoolYear);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CourseDto dto = new CourseDto();
                    dto.setSubjectId(rs.getString("MAHP"));
                    dto.setGroupId(rs.getString("NHOMHP"));
                    dto.setSemester(rs.getString("HOCKI"));
                    dto.setSchoolYear(rs.getString("NIENKHOA"));
                    dto.setLectureId(rs.getString("MACB"));
                    dto.setRoomId(rs.getString("MAPH"));
                    dto.setStudyDay(rs.getString("NGAY_HOC"));
                    dto.setStartPeriod(rs.getInt("TIET_BAT_DAU"));
                    dto.setNumberPeriods(rs.getInt("SO_TIET"));
                    dto.setMaxEnrollment(rs.getInt("SI_SO"));
                    dto.setCurrentEnrollment(rs.getInt("SO_LUONG_SV_DK"));

                    courses.add(dto);
                }
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException(String.format(
                    "Error fetching courses by semester: %s, %s",
                    semester, schoolYear), e);
        }
        return courses;
    }

    @Override
    public List<CourseDto> findListByLectureSemester(String lectureId,
            String semester, String schoolYear) throws QLDKHPDaoException {

        String query = "SELECT T1.MAHP, T1.NHOMHP, T1.HOCKI, T1.NIENKHOA, T1.MACB, T1.MAPH, T1.NGAY_HOC, T1.TIET_BAT_DAU, T1.SO_TIET, T1.SI_SO, COUNT(T2.MASV) AS SO_LUONG_SV_DK FROM LOPHP T1 LEFT JOIN SINHVIEN_LOPHP T2 ON T1.MAHP = T2.MAHP AND T1.NHOMHP = T2.NHOMHP AND T1.HOCKI = T2.HOCKI AND T1.NIENKHOA = T2.NIENKHOA WHERE T1.MACB = ? AND T1.HOCKI = ? AND T1.NIENKHOA = ? GROUP BY T1.MAHP, T1.NHOMHP, T1.NIENKHOA, T1.HOCKI ORDER BY T1.MAHP, T1.NHOMHP";
        List<CourseDto> courses = new ArrayList<>();

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, lectureId);
            ps.setString(2, semester);
            ps.setString(3, schoolYear);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CourseDto dto = new CourseDto();
                    dto.setSubjectId(rs.getString("MAHP"));
                    dto.setGroupId(rs.getString("NHOMHP"));
                    dto.setSemester(rs.getString("HOCKI"));
                    dto.setSchoolYear(rs.getString("NIENKHOA"));
                    dto.setLectureId(rs.getString("MACB"));
                    dto.setRoomId(rs.getString("MAPH"));
                    dto.setStudyDay(rs.getString("NGAY_HOC"));
                    dto.setStartPeriod(rs.getInt("TIET_BAT_DAU"));
                    dto.setNumberPeriods(rs.getInt("SO_TIET"));
                    dto.setMaxEnrollment(rs.getInt("SI_SO"));
                    dto.setCurrentEnrollment(rs.getInt("SO_LUONG_SV_DK"));

                    courses.add(dto);
                }
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException(String.format(
                    "Error fetching courses by lecture and semeseter: %s, %s, %s",
                    lectureId, semester, schoolYear), e);
        }
        return courses;
    }

    @Override
    public CourseDto findByKey(String subjectId, String groupId,
            String semester, String schoolYear) throws QLDKHPDaoException {

        String query = "SELECT T1.MAHP, T1.NHOMHP, T1.HOCKI, T1.NIENKHOA, T1.MACB, T1.MAPH, T1.NGAY_HOC, T1.TIET_BAT_DAU, T1.SO_TIET, T1.SI_SO, COUNT(T2.MASV) AS SO_LUONG_SV_DK FROM LOPHP T1 LEFT JOIN SINHVIEN_LOPHP T2 ON T1.MAHP = T2.MAHP AND T1.NHOMHP = T2.NHOMHP AND T1.HOCKI = T2.HOCKI AND T1.NIENKHOA = T2.NIENKHOA WHERE T1.MAHP = ? AND T1.NHOMHP = ? AND T1.HOCKI = ? AND T1.NIENKHOA = ? GROUP BY T1.MAHP, T1.NHOMHP, T1.NIENKHOA, T1.HOCKI";
        CourseDto dto = null;

        try (
            Connection conn = DaoUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, subjectId);
            ps.setString(2, groupId);
            ps.setString(3, semester);
            ps.setString(4, schoolYear);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new CourseDto();
                    dto.setSubjectId(rs.getString("MAHP"));
                    dto.setGroupId(rs.getString("NHOMHP"));
                    dto.setSemester(rs.getString("HOCKI"));
                    dto.setSchoolYear(rs.getString("NIENKHOA"));
                    dto.setLectureId(rs.getString("MACB"));
                    dto.setRoomId(rs.getString("MAPH"));
                    dto.setStudyDay(rs.getString("NGAY_HOC"));
                    dto.setStartPeriod(rs.getInt("TIET_BAT_DAU"));
                    dto.setNumberPeriods(rs.getInt("SO_TIET"));
                    dto.setMaxEnrollment(rs.getInt("SI_SO"));
                    dto.setCurrentEnrollment(rs.getInt("SO_LUONG_SV_DK"));
                }
            }
        } catch (SQLException e) {
            DaoUtils.throwDaoException(String.format(
                    "Error fetching course by key: %s, %s, %s, %s",
                    subjectId, groupId, semester, schoolYear), e);
        }
        return dto;
    }
}
