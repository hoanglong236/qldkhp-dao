package com.qldkhpdao.dao;

import com.qldkhpdao.common.QLDKHPDaoException;
import com.qldkhpdao.dto.EnrollmentDto;
import java.util.List;

/**
 * DAO interface for enrollment-related database operations.
 *
 * @author B1809367
 */
public interface EnrollmentDao {

    void insert(EnrollmentDto dto) throws QLDKHPDaoException;

    void delete(String subjectId, String groupId, String semester,
            String schoolYear, String studentId) throws QLDKHPDaoException;

    List<EnrollmentDto> findList() throws QLDKHPDaoException;

    List<EnrollmentDto> findListByCourse(String subjectId, String groupId,
            String semester, String schoolYear) throws QLDKHPDaoException;

    List<EnrollmentDto> findListByStudentSemester(String studentId,
            String semester, String schoolYear) throws QLDKHPDaoException;

    EnrollmentDto findByStudentSubjectSemester(String studentId,
            String subjectId, String semester, String schoolYear)
            throws QLDKHPDaoException;
}
