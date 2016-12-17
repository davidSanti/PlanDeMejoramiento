/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.concesionario.frontend.converter;

import com.concesionario.backend.entities.Concesionario;
import com.concesionario.backend.entities.TipoUsuario;
import com.concesionario.backend.model.ConcesionarioFacadeLocal;
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
@FacesConverter(value = "concesionarioConverter")
@SessionScoped
public class ConcesionarioConverter implements Converter{
    
    @EJB
    private ConcesionarioFacadeLocal concesionarioFacadeLocal;

    public ConcesionarioConverter() {
    }
            
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Concesionario> concesionarios = concesionarioFacadeLocal.findAll();
        for (Concesionario objeto : concesionarios){
            if(objeto.getNit() == Integer.parseInt(value)){
             return  objeto;  
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null){
            return ((Concesionario) value).getNit().toString();
        }else{
            return null;
        }
    }
    
}
