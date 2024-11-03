package org.example.costsplitbd.services;

import org.example.costsplitbd.models.Saldo;
import org.example.costsplitbd.repositories.SaldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaldoService {
    @Autowired
    private SaldoRepository saldoRepository;


    public List<Saldo> getAllSaldo() {
        return saldoRepository.findAll();
    }

    public Saldo getSaldoById(Long id) {
        return saldoRepository.findById(id).orElse(null);
    }

    public void deleteSaldoById(Long id) {
        saldoRepository.deleteById(id);
    }

}
