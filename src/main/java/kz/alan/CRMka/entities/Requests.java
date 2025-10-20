package kz.alan.CRMka.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id")
    private Courses courses;

    @ManyToMany
    @JoinTable(
        name = "requests_operators",
        joinColumns = @JoinColumn(name = "request_id")
    )
    private List<Operators> operators;
}
