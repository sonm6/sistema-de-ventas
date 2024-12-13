package com.uttt.spring.boot.laboratorio.app.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uttt.spring.boot.laboratorio.app.model.ProductoenVenta;
import com.uttt.spring.boot.laboratorio.app.model.Productos;
import com.uttt.spring.boot.laboratorio.app.model.ProductosFull;
import com.uttt.spring.boot.laboratorio.app.model.Ventas;
import com.uttt.spring.boot.laboratorio.app.modelDao.IProductosDao;
import com.uttt.spring.boot.laboratorio.app.modelDao.IProductosFullDao;
import com.uttt.spring.boot.laboratorio.app.modelDao.IVentasDao;

@Controller
@RequestMapping(path = "/ventas")
public class PVentaController {
	@Autowired
	private IProductosDao productodao;
	@Autowired
	private IProductosFullDao productosfulldao;
	@Autowired
	private IVentasDao ventasdao;
	

	private void guardar(ArrayList<ProductoenVenta> carrito,HttpServletRequest request) {
		request.getSession().setAttribute("carrito", carrito);
		
	}
	private void limpiar(HttpServletRequest request) {
	        this.guardar(new ArrayList<>(), request);
	  
	}
	private ArrayList<ProductoenVenta> obtener(HttpServletRequest request){
		  @SuppressWarnings("unchecked")
			ArrayList<ProductoenVenta> carrito = (ArrayList<ProductoenVenta>) request.getSession().getAttribute("carrito");
	        if (carrito == null) {
	            carrito = new ArrayList<>();
	        }
	        return carrito;
	}
	  @GetMapping(value = "/limpiar")
	    public String cancelar(HttpServletRequest request, RedirectAttributes redirectAttrs) {
	        this.limpiar(request);
	        redirectAttrs
	                .addFlashAttribute("mensaje", "Venta cancelada")
	                .addFlashAttribute("clase", "info");
	        return "redirect:/ventas/";
	    }
	  @PostMapping(value = "/quitar/{indice}")
	    public String quitar(@PathVariable int indice, HttpServletRequest request) {
	        ArrayList<ProductoenVenta> carrito = this.obtener(request);
	        if (carrito != null && carrito.size() > 0 && carrito.get(indice) != null) {
	            carrito.remove(indice);
	            this.guardar(carrito, request);
	        }
	        return "redirect:/ventas/";
	    }
	  @GetMapping(value = "/")
	    public String interfaz(Model model, HttpServletRequest request) {
		  	model.addAttribute("titulo","Tienda de Productos");
	        model.addAttribute("producto", new Productos());
	        float total = 0;
	        ArrayList<ProductoenVenta> carrito = this.obtener(request);
	        for (ProductoenVenta p: carrito) total += p.getTotal();
	        model.addAttribute("total", total);
	        return "ventas";
	    }

	  @PostMapping(value = "/agregar")
	    public String agregarA(@ModelAttribute Productos producto, HttpServletRequest request, RedirectAttributes redirectAttrs) {
	        ArrayList<ProductoenVenta> carrito = this.obtener(request);
	       
	        Productos idco = productodao.findFirstByCodigo(producto.getCodigo());
	       
	        if (idco == null) {
	            redirectAttrs
	                    .addFlashAttribute("mensaje", "El producto con el codigo no existe, registre o actualice algun producto")
	                    .addFlashAttribute("clase", "danger");
	            return "redirect:/ventas/";
	        }
	        if (idco.sinExistencia()) {
	            redirectAttrs
	                    .addFlashAttribute("mensaje", "El producto esta agotado, agregue o modifique existencia")
	                    .addFlashAttribute("clase", "warning");
	            return "redirect:/ventas/";
	        }
	        
	        boolean siexiste = false;
	        for (ProductoenVenta pventa : carrito) {
	            if (pventa.getCodigo().equals(idco.getCodigo())) {
	                pventa.aumentarCantidad();
	                siexiste = true;
	                break;
	            }
	        }
	        if (!siexiste) {
	            carrito.add(new ProductoenVenta(idco.getNombre(),idco.getCodigo(),idco.getPrecio(),idco.getExistencia(),idco.getId(), 1f));
	        }
	        this.guardar(carrito, request);
	        return "redirect:/ventas/";
	    }
	  
	  @PostMapping(value = "/terminar")
	    public String terminarV(HttpServletRequest request, RedirectAttributes redirectAttrs) {
	        ArrayList<ProductoenVenta> carrito = this.obtener(request);
	        if (carrito == null || carrito.size() <= 0) {
	        	  redirectAttrs
	                .addFlashAttribute("mensaje", "No hay Productos en lista")
	                .addFlashAttribute("clase", "danger");
	            return "redirect:/ventas/";
	        }
	      
	        for (ProductoenVenta pventa : carrito) {
	            Productos productosvarios = productodao.findById(pventa.getId()).orElse(null);
	            
	            if (pventa.getCantidad() > pventa.getExistencia()) {
	            	
	            	this.limpiar(request);
	            	 redirectAttrs
		                .addFlashAttribute("mensaje", "No hay cantidad suficiente para realizar pedido")
		                .addFlashAttribute("clase", "warning");
		            return "redirect:/ventas/";
	            	
	            }  
	            Ventas ventasguardar = ventasdao.save(new Ventas());
	            ProductosFull productoVendido = new ProductosFull(pventa.getCantidad(), pventa.getPrecio(), pventa.getNombre(), pventa.getCodigo(), ventasguardar);
	            productosvarios.restarExistencia(pventa.getCantidad());
	            productodao.save(productosvarios);
	            productosfulldao.save(productoVendido);
	        }
	        this.limpiar(request);
	        redirectAttrs.addFlashAttribute("mensaje", "Venta realizada correctamente").addFlashAttribute("clase", "success");
	        return "redirect:/detallesventas/";
	    }
	  }