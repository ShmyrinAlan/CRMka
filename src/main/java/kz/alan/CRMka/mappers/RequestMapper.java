package kz.alan.CRMka.mappers;

import kz.alan.CRMka.dto.RequestsDTO;
import kz.alan.CRMka.dto.shorts.OperatorsDTOSh;
import kz.alan.CRMka.dto.shorts.RequestsDTOSh;
import kz.alan.CRMka.entities.Requests;
import kz.alan.CRMka.repositories.OperatorsRepository;


import java.util.List;


public class RequestMapper {
    OperatorsRepository repo;
    public static RequestsDTOSh toShort(Requests en) {
        return new RequestsDTOSh(en.getId(), en.getUserName(), en.isStatus());
    }
    public static Requests toRequests(RequestsDTO dto, OperatorsRepository repo) {
        return new Requests(dto.getId(), dto.getUserName(), dto.getDescription(), dto.getPhone(), dto.getDate(), dto.isStatus(), CourseMapper.toCourses(dto.getCourse()), repo.findAllById(dto.getOperators() != null ? dto.getOperators().stream().map(OperatorsDTOSh::getId).toList(): List.of(404L)));
    }
    public static RequestsDTO toRequestsDTO(Requests en) {
        return new RequestsDTO(en.getId(), en.getUserName(), en.getDescription(), en.getPhone(), en.getDate(), en.isStatus(), CourseMapper.toCoursesDTO(en.getCourses()), en.getOperators().stream().map(OperatorMapper::toShort).toList());
    }

}
