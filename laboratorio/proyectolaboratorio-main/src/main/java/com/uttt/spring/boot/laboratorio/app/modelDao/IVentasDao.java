package com.uttt.spring.boot.laboratorio.app.modelDao;

import org.springframework.data.repository.CrudRepository;

import com.uttt.spring.boot.laboratorio.app.model.Ventas;

public interface IVentasDao extends CrudRepository<Ventas, Integer> {

}
