package kz.alan.CRMka.dto;

import kz.alan.CRMka.dto.shorts.OperatorsDTOSh;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestsDTO {
    private Long id;
    private String userName;
    private String description;
    private String phone;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    boolean status;
    private CoursesDTO course;
    private List<OperatorsDTOSh> operators;
}
