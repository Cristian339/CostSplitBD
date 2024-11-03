package org.example.costsplitbd.repositories;

import org.example.costsplitbd.models.ParticipacionGasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipacionGastoRepository extends JpaRepository<ParticipacionGasto, Long> {
}
