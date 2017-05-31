/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestec.modelo.persistencia;

import com.gestec.modelo.entidades.Usuarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author michael
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> implements UsuariosFacadeLocal {

    @PersistenceContext(unitName = "gestecPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }

    @Override
    public Usuarios iniciarSesion(String nombreUsuario, String clave) {
        try {
            TypedQuery<Usuarios> q = getEntityManager().createNamedQuery("Usuarios.login", Usuarios.class);
            q.setParameter("usuario", nombreUsuario);
            q.setParameter("clave", clave);
            Usuarios usuarioLogin = q.getSingleResult();
            return usuarioLogin;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
