/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.concesionario.frontend.converter;

import com.concesionario.backend.entities.Usuario;
import com.concesionario.backend.model.UsuarioFacadeLocal;
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
@FacesConverter(value = "usuarioConverter")
@SessionScoped
public class UsuarioConverter implements Converter{
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;

    public UsuarioConverter() {
    }
            
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Usuario> usuarios = usuarioFacadeLocal.findAll();
        for (Usuario objeto : usuarios){
            if(objeto.getUsuarioID()== Integer.parseInt(value)){
             return  objeto;  
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null){
            return ((Usuario) value).getUsuarioID().toString();
        }else{
            return null;
        }
    }
}
