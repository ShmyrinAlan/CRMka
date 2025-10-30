package kz.alan.CRMka.mappers;

import kz.alan.CRMka.dto.OperatorsDTO;
import kz.alan.CRMka.dto.shorts.OperatorsDTOSh;
import kz.alan.CRMka.dto.shorts.RequestsDTOSh;
import kz.alan.CRMka.entities.Operators;
import kz.alan.CRMka.entities.Requests;
import kz.alan.CRMka.repositories.OperatorsRepository;
import kz.alan.CRMka.repositories.RequestRepository;

public class OperatorMapper {
    public static OperatorsDTOSh toShort(Operators en) {
        return new OperatorsDTOSh(en.getId(), en.getName(), en.getSurname());
    }
    public static Operators toOperators(OperatorsDTO dto, RequestRepository repo) {
        return new Operators(dto.getId(), dto.getName(), dto.getSurname(), dto.getDepartment(), dto.getRequests().stream().map(req ->{
            return repo.findById(req.getId()).get();
        }).toList());
    }
    public static OperatorsDTO toOperatorsDTO(Operators en) {
        return new OperatorsDTO(en.getId(), en.getName(), en.getSurname(), en.getDepartment(), en.getRequests().stream().map(RequestMapper::toShort).toList());
    }
}
