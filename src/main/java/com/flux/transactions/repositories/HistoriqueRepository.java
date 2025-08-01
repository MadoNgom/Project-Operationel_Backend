package com.flux.transactions.repositories;

import com.flux.transactions.entities.Historique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriqueRepository extends JpaRepository<Historique, Long> {
}
