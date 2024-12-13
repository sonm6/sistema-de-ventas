package com.uttt.spring.boot.laboratorio.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uttt.spring.boot.laboratorio.app.modelDao.IVentasDao;


@Controller
@RequestMapping(path = "/detallesventas")
public class PVendidosController {

	  @Autowired
	    IVentasDao ventasdao;

	    @GetMapping(value = "/")
	    public String mostrarVentas(Model model) {
	     	model.addAttribute("titulo","Detalles de las Ventas en Productos");
	        model.addAttribute("detallesventas", ventasdao.findAll());
	        return "ventasfin";
	    }
}
