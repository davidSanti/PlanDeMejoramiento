/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.concesionario.frontend.controller;

import com.concesionario.backend.entities.Usuario;
import com.concesionario.backend.entities.Vehiculo;
import com.concesionario.backend.entities.Venta;
import com.concesionario.backend.model.UsuarioFacadeLocal;
import com.concesionario.backend.model.VehiculoFacadeLocal;
import com.concesionario.backend.model.VentaFacadeLocal;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Santi
 */
@Named(value = "ventaController")
@ViewScoped
public class VentaController {

    @EJB
    private VentaFacadeLocal ventaFacadeLocal;
    @EJB
    private UsuarioFacadeLocal UsuarioFacadeLocal; 
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    
    private Venta venta;
    private Usuario usuario;
    private Vehiculo vehiculo;
    
    private List<Usuario> usuarios;
    private List<Vehiculo> vehiculos;
    
    public VentaController() {
    }
    
    @PostConstruct
    public void init(){
        venta = new Venta();
        usuario = new Usuario();
        vehiculo = new Vehiculo();
                
    }
    
    public String registrarVenta(){
        String redirect = "registrarVenta";
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            //this.venta.setVentaID(2);
            this.ventaFacadeLocal.create(venta);
            
             context.addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "La venta se ha registrado correctamente"));
             redirect = "ventas?faces-redirect=true";
        }catch(EJBException e){
            @SuppressWarnings("ThrowableResultIgnored")
            Exception cause = e.getCausedByException();
            if (cause instanceof ConstraintViolationException) {
                @SuppressWarnings("ThrowableResultIgnored")
                ConstraintViolationException cve = (ConstraintViolationException) e.getCausedByException();
                for (Iterator<ConstraintViolation<?>> it = cve.getConstraintViolations().iterator(); it.hasNext();) {
                    ConstraintViolation<? extends Object> v = it.next();
                    System.err.println(v);
                    System.err.println("==>>" + v.getMessage());
                }
            }
            context.addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "No se ha podido registrar la venta"));
        }
        return redirect;
    }
    
    public List<Venta> listarVentas(){
        return this.ventaFacadeLocal.findAll();
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<Vehiculo> getVehiculos() {
        vehiculos = this.vehiculoFacadeLocal.litar();
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public List<Usuario> getUsuarios() {
        usuarios = this.UsuarioFacadeLocal.listarClientes();
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    
    
}
