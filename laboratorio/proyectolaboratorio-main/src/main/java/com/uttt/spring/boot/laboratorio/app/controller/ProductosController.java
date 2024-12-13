package com.uttt.spring.boot.laboratorio.app.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uttt.spring.boot.laboratorio.app.model.Productos;
import com.uttt.spring.boot.laboratorio.app.modelDao.IProductosDao;



@Controller
public class ProductosController {

	@Autowired
	IProductosDao productoDao;
	
	@RequestMapping(value ="/", method = RequestMethod.GET)
	public String accountPrincipal(Model model) {
		Productos product = new Productos();
		model.addAttribute("product",product);
		model.addAttribute("titulo","Almacén de Productos");
		listarProducto(model);
		return "almacenProducto";
	}
	
	@RequestMapping(value="/registroProductos" , method = RequestMethod.GET)
	public String registroProductos(Map<String,Object> model) {
	Productos registerProd = new Productos();
		model.put("registroProductos",registerProd);
		model.put("titulo", "Formulario de Registro de Productos");
		return "registroProductos";
	}
	
	@RequestMapping(value ="/registroProductos", method = RequestMethod.POST)
	public String guardarProducto(@Valid @ModelAttribute("registroProductos") Productos registerArt, BindingResult result, Model model)
	{
		if(result.hasErrors()) {
			model.addAttribute("titulo","Formulario de Registro");
			return "registroProductos";
		}
		productoDao.save(registerArt);
		return "redirect:almacenProducto";
	}
	
	@RequestMapping(value="/almacenProducto", method = RequestMethod.GET)
	public String listarProducto(Model model) {
		model.addAttribute("titulo","Almacén de Productos");
		model.addAttribute("productoss", productoDao.findAll());
		return "almacenProducto";	
	}
	
	@RequestMapping(value = "/editarproducto/{id}")
	public String editarProducto (@PathVariable(value="id") Integer id, Model model) {
		Optional<Productos> registrarProd = null;
		if(id >0) {
			registrarProd =productoDao.findById(id);
			
		}
		else {
			return "redirect:/almacenProducto";
		}
		model.addAttribute("registroProductos", registrarProd);
		model.addAttribute("titulo", "Edicion de Articulos");
		
		return "registroProductos";
	}
	
	
	@RequestMapping(value = "/eliminarproducto/{id}")
	public String eliminar (@ModelAttribute Productos producto, RedirectAttributes redirectAttrs) {
		 redirectAttrs
         .addFlashAttribute("mensaje", "Eliminado correctamente")
         .addFlashAttribute("clase", "warning");
		 productoDao.deleteById(producto.getId());
		return "almacenProducto";
	}
}