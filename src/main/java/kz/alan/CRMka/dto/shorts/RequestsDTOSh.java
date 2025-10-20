package kz.alan.CRMka.dto.shorts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestsDTOSh {
    private Long id;
    private String userName;
    boolean status;
}
