package com.qldkhpdao.dao;

import com.qldkhpdao.common.QLDKHPDaoException;
import com.qldkhpdao.dto.CourseDto;
import java.util.List;

/**
 * DAO interface for course-related database operations.
 *
 * @author B1809367
 */
public interface CourseDao {

    void insert(CourseDto courseDto) throws QLDKHPDaoException;

    void update(CourseDto courseDto) throws QLDKHPDaoException;

    void delete(String subjectId, String groupId,
            String semester, String schoolYear) throws QLDKHPDaoException;

    int count() throws QLDKHPDaoException;

    List<CourseDto> findList() throws QLDKHPDaoException;

    List<CourseDto> findListBySemester(String semester, String schoolYear)
            throws QLDKHPDaoException;

    List<CourseDto> findListByLectureSemester(String lectureId,
            String semester, String schoolYear) throws QLDKHPDaoException;

    CourseDto findByKey(String subjectId, String groupId,
            String semester, String schoolYear) throws QLDKHPDaoException;
}
