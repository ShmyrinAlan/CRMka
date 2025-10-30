package kz.alan.CRMka.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Requests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String userName;

    @Column(length = 200)
    private String description;

    @Column(length = 10)
    private String phone;

    @Column
    private LocalDate date;

    @Column
    boolean status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_id")
    private Courses courses;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "requests_operators",
        joinColumns = @JoinColumn(name = "request_id"),
        inverseJoinColumns = @JoinColumn(name = "operators_id")
    )
    private List<Operators> operators;
}
