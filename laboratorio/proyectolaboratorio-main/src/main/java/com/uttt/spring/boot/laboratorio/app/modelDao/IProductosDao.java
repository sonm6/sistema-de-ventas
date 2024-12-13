package com.uttt.spring.boot.laboratorio.app.modelDao;

import org.springframework.data.repository.CrudRepository;


import com.uttt.spring.boot.laboratorio.app.model.Productos;

public interface IProductosDao extends CrudRepository<Productos, Integer> {

	Productos findFirstByCodigo(String codigo);
}
