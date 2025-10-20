package kz.alan.CRMka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursesDTO {
    private Long id;

    private String name;
    private String description;
    private Integer price;
}
