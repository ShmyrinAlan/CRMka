package kz.alan.CRMka.repositories;

import kz.alan.CRMka.entities.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface RequestRepository extends JpaRepository<Requests, Long> {
    List<Requests> findAllByDateAfter(LocalDate localDate);

    List<Requests> findAllByStatus(boolean status);
}
