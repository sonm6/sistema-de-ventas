package com.uttt.spring.boot.laboratorio.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Productos {

	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer id;
	 @NotNull(message = "Debes especificar el nombre")
	    @Size(min = 1, max = 50, message = "El nombre debe medir entre 1 y 50")
		private String nombre;
		@NotNull(message = "Debes especificar el codigo")
	    @Size(min = 1, max = 50, message = "El codigo debe medir entre 1 y 50")
		private String codigo;
		@NotNull(message = "Debes especificar el precio")
		@Min(value = 0, message = "El precio mínimo es 0")
		private Float precio;
		@NotNull(message = "Debes especificar la existencia")
		@Min(value = 0, message = "La existencia mínima es 0")
		private Float existencia;
		@NotNull(message = "Debes especificar la descripcion")
	    @Size(min = 1, max = 1000, message = "La descripcion debe medir entre 25 y 1000")
		private String descripcion;
		@NotNull(message = "Debes especificar la marca")
	    @Size(min = 1, max = 50, message = "La marca debe medir entre 1 y 50")
		private String marca;
		@NotNull(message = "Debes especificar el peso")
		@Min(value = 0, message = "El peso mínimo es 0")
		private Float peso;
	 	
	 	
	 	public Productos(String nombre, String codigo, Float precio, Float existencia, Integer id) {
	        this.nombre = nombre;
	        this.codigo = codigo;
	        this.precio = precio;
	        this.existencia = existencia;
	        this.id = id;
	    }

	    public Productos(String nombre, String codigo, Float precio, Float existencia) {
	        this.nombre = nombre;
	        this.codigo = codigo;
	        this.precio = precio;
	        this.existencia = existencia;
	    }

	    public Productos(@NotNull(message = "Debes especificar el codigo") @Size(min = 1, max = 50, message = "El codigo debe medir entre 1 y 50") String codigo) {
	        this.codigo = codigo;
	    }

	    public Productos() {
	    }

	    public boolean sinExistencia() {
	        return this.existencia <= 0;
	    }

	    public String getCodigo() {
	        return codigo;
	    }

	    public void setCodigo(String codigo) {
	        this.codigo = codigo;
	    }

	    public Float getPrecio() {
	        return precio;
	    }

	    public void setPrecio(Float precio) {
	        this.precio = precio;
	    }

	    public Float getExistencia() {
	        return existencia;
	    }

	    public void setExistencia(Float existencia) {
	        this.existencia = existencia;
	    }

	    public void restarExistencia(Float existencia) {
	        this.existencia -= existencia;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public String getMarca() {
			return marca;
		}

		public void setMarca(String marca) {
			this.marca = marca;
		}

		public Float getPeso() {
			return peso;
		}

		public void setPeso(Float peso) {
			this.peso = peso;
		}
	}
