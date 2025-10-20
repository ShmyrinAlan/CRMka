package kz.alan.CRMka.mappers;

import kz.alan.CRMka.dto.CoursesDTO;
import kz.alan.CRMka.entities.Courses;

public class CourseMapper {
    public static Courses toCourses(CoursesDTO dto) {
        return new Courses(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrice());
    }

    public static CoursesDTO toCoursesDTO(Courses en) {
        return new CoursesDTO(en.getId(), en.getName(), en.getDescription(), en.getPrice());
    }
}
