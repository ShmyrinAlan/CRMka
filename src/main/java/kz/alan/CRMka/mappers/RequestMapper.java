package kz.alan.CRMka.mappers;

import kz.alan.CRMka.dto.RequestsDTO;
import kz.alan.CRMka.dto.shorts.OperatorsDTOSh;
import kz.alan.CRMka.dto.shorts.RequestsDTOSh;
import kz.alan.CRMka.entities.Courses;
import kz.alan.CRMka.entities.Operators;
import kz.alan.CRMka.entities.Requests;
import kz.alan.CRMka.repositories.CoursesRepository;
import kz.alan.CRMka.repositories.OperatorsRepository;


import java.util.List;
import java.util.Optional;


public class RequestMapper {
    OperatorsRepository repo;
    public static RequestsDTOSh toShort(Requests en) {
        return new RequestsDTOSh(en.getId(), en.getUserName(), en.isStatus());
    }
    public static Requests toRequests(RequestsDTO dto, OperatorsRepository repo, CoursesRepository cor) {
        Optional<Courses> corsu =  cor.findById(dto.getCourse().getId());
        return new Requests(dto.getId(), dto.getUserName(), dto.getDescription(), dto.getPhone(), dto.getDate(), dto.isStatus(), corsu.orElseGet(() -> cor.findById(404l).get()), dto.getOperators().stream().map(id -> {
            Optional<Operators> op = repo.findById(id.getId());
            return op.orElseGet(() -> repo.findById(404l).get());
        }).toList());
    }
    public static RequestsDTO toRequestsDTO(Requests en) {
        return new RequestsDTO(en.getId(), en.getUserName(), en.getDescription(), en.getPhone(), en.getDate(), en.isStatus(), CourseMapper.toCoursesDTO(en.getCourses()), en.getOperators().stream().map(OperatorMapper::toShort).toList());
    }

}
