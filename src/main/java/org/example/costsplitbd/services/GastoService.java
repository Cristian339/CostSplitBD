package org.example.costsplitbd.services;

import org.example.costsplitbd.models.Gasto;
import org.example.costsplitbd.repositories.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastoService {
    @Autowired
    private GastoRepository gastoRepository;


    public List<Gasto> getAllGasto() {
        return gastoRepository.findAll();
    }

    public Gasto getGastoById(Long id) {
        return gastoRepository.findById(id).orElse(null);
    }

    public void deleteGastoById(Long id) {
        gastoRepository.deleteById(id);
    }
}
