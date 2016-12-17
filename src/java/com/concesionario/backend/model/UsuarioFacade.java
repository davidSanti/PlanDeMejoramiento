/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.concesionario.backend.model;

import com.concesionario.backend.entities.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Santi
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "Concesionario1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public Usuario iniciarSesion(Usuario usuario) {
        Usuario usuarioValidado = null;
        try {
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.cedula =?1 AND u.clave =?2 AND u.estado = true AND u.tipoID.tipoID IN(1,3)");
            query.setParameter(1, usuario.getCedula());
            query.setParameter(2, usuario.getClave());

            List<Usuario> usuarios = query.getResultList();
            if (!usuarios.isEmpty()) {
                usuarioValidado = usuarios.get(0);
            }

        } catch (Exception e) {
            throw e;
        }
        return usuarioValidado;
    }

    @Override
    public List<Usuario> listar() {
        TypedQuery<Usuario> query = getEntityManager().createNamedQuery("Usuario.findAll", Usuario.class);
        return query.getResultList();
    }

    @Override
    public boolean verificarTipo(Usuario usuario) {
        boolean bandera = false;
        if (usuario.getTipoID().getTipoID() == 3) {
            bandera = true;
        }
        return bandera;
    }

    @Override
    public List<Usuario> listarClientes() {
        try {
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.estado = true AND u.tipoID.tipoID = 2");
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }

}
