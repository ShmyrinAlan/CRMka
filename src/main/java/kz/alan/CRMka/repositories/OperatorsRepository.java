package kz.alan.CRMka.repositories;

import kz.alan.CRMka.entities.Operators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OperatorsRepository extends JpaRepository<Operators, Long> {
}
