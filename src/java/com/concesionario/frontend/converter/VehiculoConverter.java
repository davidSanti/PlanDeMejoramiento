/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.concesionario.frontend.converter;

import com.concesionario.backend.entities.Usuario;
import com.concesionario.backend.entities.Vehiculo;
import com.concesionario.backend.model.VehiculoFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Santi
 */
@FacesConverter(value = "vehiculoConverter")
@SessionScoped
public class VehiculoConverter implements Converter{
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;

    public VehiculoConverter() {
    }
            
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Vehiculo> vehiculos = vehiculoFacadeLocal.litar();
        for (Vehiculo objeto : vehiculos){
            if(objeto.getVehiculoID()== Integer.parseInt(value)){
             return  objeto;  
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null){
            return ((Vehiculo) value).getVehiculoID().toString();
        }else{
            return null;
        }
    }
}
