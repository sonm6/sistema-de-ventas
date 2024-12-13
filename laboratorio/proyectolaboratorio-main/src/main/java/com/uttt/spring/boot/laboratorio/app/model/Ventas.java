package com.uttt.spring.boot.laboratorio.app.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;





@Entity
public class Ventas {
	  @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer id;
	    private String fechaYHora;

	    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
	    private Set<ProductosFull> productos;

	    public Ventas() {
	        this.fechaYHora = Dates.obtenerFechaHoraActual();
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public Float getTotal() {
	        Float total = 0f;
	        for (ProductosFull productoVendido : this.productos) {
	            total += productoVendido.getTotal();
	        }
	        return total;
	    }

	    public String getFechaYHora() {
	        return fechaYHora;
	    }

	    public void setFechaYHora(String fechaYHora) {
	        this.fechaYHora = fechaYHora;
	    }

	    public Set<ProductosFull> getProductos() {
	        return productos;
	    }

	    public void setProductos(Set<ProductosFull> productos) {
	        this.productos = productos;
	    }
	}