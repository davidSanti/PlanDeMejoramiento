/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.concesionario.frontend.controller;

import com.concesionario.backend.entities.TipoUsuario;
import com.concesionario.backend.entities.Usuario;
import com.concesionario.backend.model.TipoUsuarioFacadeLocal;
import com.concesionario.backend.model.UsuarioFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Santi
 */
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    @EJB
    private UsuarioFacadeLocal UsuarioFacadeLocal;
    @EJB
    private TipoUsuarioFacadeLocal tipoUsuarioFacadeLocal;

    private Usuario usuario;
    private TipoUsuario tipoUsuario;
    private List<TipoUsuario> tipos;

    public UsuarioController() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        tipoUsuario = new TipoUsuario();

    }

    public String autenticarse() {
        Usuario usuarioValidado = null;
        String redireccion = null;
        try {

            usuarioValidado = this.UsuarioFacadeLocal.iniciarSesion(this.usuario);
            if (usuarioValidado != null) {
                HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                sesion.setAttribute("Usuario", usuarioValidado);
                redireccion = "sistema?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Datos Incorrectos"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Datos Incorrectos"));
        }
        return redireccion;
    }

    public List<Usuario> listarUsuarios() {
        return this.UsuarioFacadeLocal.listar();
    }

    public String registrarUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        String redirect = "registrarUsuario";
        try {
            usuario.setEstado(true);
            usuario.setUsuarioID(2);
            this.UsuarioFacadeLocal.create(this.usuario);
            context.addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "El usuario se ha registrado correctamente"));
            redirect = "usuarios?faces-redirect=true";
        } catch (EJBException e) {
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
            // Assert.fail("ejb exception");
            context.addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "El usuario no se ha podido registrar"));
        }
        return redirect;
    }

    public void cerrarSesion() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
    }

    public boolean verificarTipo() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Usuario u = (Usuario) sesion.getAttribute("Usuario");

        if (this.UsuarioFacadeLocal.verificarTipo(u)) {
            return true;
        } else {
            return false;
        }

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<TipoUsuario> getTipos() {
        tipos = this.tipoUsuarioFacadeLocal.findAll();
        return tipos;
    }

    public void setTipos(List<TipoUsuario> tipos) {
        this.tipos = tipos;
    }

}
