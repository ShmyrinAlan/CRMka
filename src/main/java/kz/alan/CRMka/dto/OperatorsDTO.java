package kz.alan.CRMka.dto;

import kz.alan.CRMka.dto.shorts.RequestsDTOSh;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperatorsDTO {
    private Long id;

    private String name;
    private String surname;
    private String department;
    private List<RequestsDTOSh> requests;
}
