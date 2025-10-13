package kz.alan.CRMka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import kz.alan.CRMka.entities.Requests;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private Long id;
    private String userName;
    private String courseName;
    private String description;
    private String phone;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    boolean status;

    public RequestDTO(@NonNull Requests request) {
        id = request.getId();
        userName = request.getUserName();
        courseName = request.getCourseName();
        description = request.getDescription();
        phone = request.getPhone();
        date = request.getDate();
        status = request.isStatus();
    }
}
