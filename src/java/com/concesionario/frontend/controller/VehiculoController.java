/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.concesionario.frontend.controller;

import com.concesionario.backend.entities.Concesionario;
import com.concesionario.backend.entities.Vehiculo;
import com.concesionario.backend.model.ConcesionarioFacadeLocal;
import com.concesionario.backend.model.VehiculoFacadeLocal;
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
@Named(value = "vehiculoController")
@ViewScoped
public class VehiculoController {
   
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal; 
    @EJB
    private ConcesionarioFacadeLocal concesionarioFacadeLocal;
      
    private Vehiculo vehiculo;
    private Concesionario concesionario;
    private List<Concesionario> concesionarios;
     
    public VehiculoController() {
    }
    
    @PostConstruct
    public void init(){
        vehiculo = new Vehiculo();
        concesionario = new Concesionario();
    }
    
    public String registrarVehiculo(){
        String redirect = "registrarVehiculo";
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            //this.vehiculo.setConcesionarioList((List<Concesionario>) new Concesionario(123));
            this.vehiculo.setVehiculoID(2);
            this.vehiculoFacadeLocal.create(this.vehiculo);
             context.addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "El vehículo se ha registrado correctamente"));
             redirect = "vehiculos?faces-redirect=true";
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
                    null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "No se ha podido registrar el vehículo"));
        }
        return redirect;
    }

    public List<Vehiculo> listarVehiculos(){
        return this.vehiculoFacadeLocal.litar();
    }
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Concesionario getConcesionario() {
        return concesionario;
    }

    public void setConcesionario(Concesionario concesionario) {
        this.concesionario = concesionario;
    }

    public List<Concesionario> getConcesionarios() {
        concesionarios = concesionarioFacadeLocal.findAll();
        return concesionarios;
    }

    public void setConcesionarios(List<Concesionario> concesionarios) {
        this.concesionarios = concesionarios;
    }

   
}
