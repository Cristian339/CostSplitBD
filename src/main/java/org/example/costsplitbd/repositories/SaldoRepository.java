package org.example.costsplitbd.repositories;

import org.example.costsplitbd.models.Saldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, Long> {
}
