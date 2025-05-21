package com.example.comenzirestaurant.Service;

import com.example.comenzirestaurant.Domain.Table;
import com.example.comenzirestaurant.Repository.TableRepository;

public class TableService extends AbstractService{
    private TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public Iterable<Table> getAll(){
        return tableRepository.getAll();
    }
}
