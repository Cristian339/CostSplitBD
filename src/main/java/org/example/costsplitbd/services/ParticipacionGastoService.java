package org.example.costsplitbd.services;

import org.example.costsplitbd.models.ParticipacionGasto;
import org.example.costsplitbd.models.ParticipacionGasto;
import org.example.costsplitbd.repositories.ParticipacionGastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipacionGastoService {
    @Autowired
    private ParticipacionGastoRepository participacionGastoRepository;

    public List<ParticipacionGasto> getAllParticipacionGasto() {
        return participacionGastoRepository.findAll();
    }

    public ParticipacionGasto getParticipacionGastoById(Long id) {
        return participacionGastoRepository.findById(id).orElse(null);
    }

    public void deleteParticipacionGastoById(Long id) {
        participacionGastoRepository.deleteById(id);
    }
}
