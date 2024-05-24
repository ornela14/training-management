package al.sdacademy.trainingmanagement.service;

import al.sdacademy.trainingmanagement.dto.courseUserDtos.CourseUserDto;
import al.sdacademy.trainingmanagement.dto.courseUserDtos.CourseUserListDto;
import al.sdacademy.trainingmanagement.repository.criteria.CourseUserCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseUserService {

    Page<CourseUserListDto> getCourseUserList(CourseUserCriteria criteria);

    List<CourseUserListDto> getCourseUserListByCourseId(Long courseId);

    CourseUserDto editCourseUser(CourseUserDto courseUserDto);

    CourseUserDto assignUserToCourse(CourseUserDto dto);

    void removeUserFromCourse(Long userId, Long courseId);

    CourseUserDto getCourseUserEntity(Long courseId, Long userId);

}
