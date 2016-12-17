/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.concesionario.frontend.converter;

import com.concesionario.backend.entities.TipoUsuario;
import com.concesionario.backend.model.TipoUsuarioFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Santi
 */
@FacesConverter(value = "tipoUsuarioConverter")
@ViewScoped
public class TipoUsuarioConverter implements Converter{

    @EJB
    private TipoUsuarioFacadeLocal tipoFacadeLocal;

    public TipoUsuarioConverter() {
    }
            
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<TipoUsuario> tipos = tipoFacadeLocal.findAll();
        for (TipoUsuario objeto : tipos){
            if(objeto.getTipoID() == Integer.parseInt(value)){
             return  objeto;  
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null){
            return ((TipoUsuario) value).getTipoID().toString();
        }else{
            return null;
        }
    }
    
}
