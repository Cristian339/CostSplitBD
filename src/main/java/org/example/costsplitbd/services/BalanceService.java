/*
package org.example.costsplitbd.services;


import org.example.costsplitbd.models.Balance;
import org.example.costsplitbd.repositories.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;


    public List<Balance> getAllBalance() {
        return balanceRepository.findAll();
    }

    public Balance getBalanceById(Long id) {
        return balanceRepository.findById(id).orElse(null);
    }

    public void deleteBalanceById(Long id) {
        balanceRepository.deleteById(id);
    }
}
*/
