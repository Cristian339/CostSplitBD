package org.example.costsplitbd.repositories;

import org.example.costsplitbd.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo,Long> {
}
