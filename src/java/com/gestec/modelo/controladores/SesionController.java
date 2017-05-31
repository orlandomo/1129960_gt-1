/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestec.modelo.controladores;

import com.gestec.modelo.entidades.Barrio;
import com.gestec.modelo.entidades.Contactos;
import com.gestec.modelo.entidades.Localidad;
import com.gestec.modelo.entidades.Relcalificacionusuarios;
import com.gestec.modelo.entidades.Usuarios;
import com.gestec.modelo.persistencia.BarrioFacadeLocal;
import com.gestec.modelo.persistencia.ContactosFacadeLocal;
import com.gestec.modelo.persistencia.LocalidadFacadeLocal;
import com.gestec.modelo.persistencia.UsuariosFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

@Named(value = "sesionGestec")
@ManagedBean(name = "sesion")
@SessionScoped
public class SesionController implements Serializable {

    @EJB
    private UsuariosFacadeLocal ufl;
    @EJB
    private LocalidadFacadeLocal lfl;
    @EJB
    private BarrioFacadeLocal bfl;
    @EJB
    private ContactosFacadeLocal cfl;

    private String nombreUsuario;
    private String contrasena;
    private List<Localidad> localidades;
    private List<String> barriosLocalidades;
    private Integer numeroLocalidad;
    private List<Barrio> barrios;
    private List<String> nombreBarrios;
    private Usuarios perfil;
    private Usuarios usuario;
    private Contactos contacto;
    
    private Locale idiomaSeleccionado;
    private List<Locale> idiomasSoportados;

    @PostConstruct
    public void init(){
        this.contacto = new Contactos();
        this.localidades = lfl.findAll();
        this.barrios = bfl.findAll();
        
        FacesContext fc= FacesContext.getCurrentInstance();
        idiomaSeleccionado=new Locale("es");
        
        idiomasSoportados= new ArrayList<>();
        Iterator<Locale> it=fc.getApplication().getSupportedLocales();
        while (it.hasNext()){
            idiomasSoportados.add(it.next());
        }
        
    }
    
    

    public Locale getIdiomaSeleccionado() {
        return idiomaSeleccionado;
    }

    public void setIdiomaSeleccionado(Locale idiomaSeleccionado) {
        this.idiomaSeleccionado = idiomaSeleccionado;
    }

    public List<Locale> getIdiomasSoportados() {
        return idiomasSoportados;
    }

    public void setIdiomasSoportados(List<Locale> idiomasSoportados) {
        this.idiomasSoportados = idiomasSoportados;
    }
     
     
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Usuarios getPerfil() {
        return perfil;
    }

    public void setPerfil(Usuarios perfil) {
        this.perfil = perfil;
    }

    public Contactos getContacto() {
        return contacto;
    }

    public void setContacto(Contactos contacto) {
        this.contacto = contacto;
    }

    public List<Localidad> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<Localidad> localidades) {
        this.localidades = localidades;
    }  

    public List<Barrio> getBarrios() {
        return barrios;
    }

    public void setBarrios(List<Barrio> barrios) {
        this.barrios = barrios;
    }

    public Integer getNumeroLocalidad() {
        return numeroLocalidad;
    }

    public void setNumeroLocalidad(Integer numeroLocalidad) {
        this.numeroLocalidad = numeroLocalidad;
    }

    public List<String> getBarriosLocalidades() {
        return barriosLocalidades;
    }

    public void setBarriosLocalidades(List<String> barriosLocalidades) {
        this.barriosLocalidades = barriosLocalidades;
    }

    public List<String> getNombreBarrios() {
        return nombreBarrios;
    }

    public void setNombreBarrios(List<String> nombreBarrios) {
        this.nombreBarrios = nombreBarrios;
    }

    public String iniciarSesion() {

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        FacesMessage msj = new FacesMessage();
        String url = "";

        if (this.nombreUsuario != null && !this.nombreUsuario.equals("")
                && this.contrasena != null && !this.contrasena.equals("")) {
            this.usuario = ufl.iniciarSesion(nombreUsuario, contrasena);
            if (usuario != null) {
                ec.getSessionMap().put("user", usuario);
                switch (usuario.getTipoUsuario()) {
                    case "Administrador":
                        url = "/faces/gestec/usuario/admin.xhtml?faces-redirect=true";
                        break;
                    case "Cliente":
                        url = "/faces/gestec/usuario/cliente.xhtml?faces-redirect=true";
                        break;
                    case "Tecnico":
                        url = "/faces/gestec/usuario/tecnico.xhtml?faces-redirect=true";
                        break;
                    default:
                        break;
                }
            } else {
                msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datos incorrectos", "Confirme que sus datos sean correctos");
            }
        } else {
            msj = new FacesMessage(FacesMessage.SEVERITY_WARN, "Todos los campos son obligatorios", "Debe ingresar todos los campos");

        }
        fc.addMessage(null, msj);
        return url;
    }

    public Usuarios getUsuario() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        Object u = ec.getSessionMap().get("user");
        if (u instanceof Usuarios) {
            this.usuario = (Usuarios) ec.getSessionMap().get("user");
            return usuario;
        }
        return null;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public void verPerfil(Usuarios perfil) {
        setPerfil(perfil);
        redireccionar("/faces/gestec/usuario/perfil_externo.xhtml?faces-redirect=true");
    } 

    public String formatearFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "CO"));
        String fechaF = formato.format(fecha);
        if (fechaF.equals("")) {
            return "Sin fecha";
        } else {
            return fechaF;
        }
    }
    
    public String formatearFechaCumpleaños() {
        SimpleDateFormat formato = new SimpleDateFormat("dd 'de' MMMM", new Locale("es", "CO"));
        String fechaF = formato.format(getUsuario().getFechaNacimiento());
        if (fechaF.equals("")) {
            return "Sin fecha";
        } else {
            return fechaF;
        }
    }

    public String formatearFechaCumpleañosPerfil() {
        SimpleDateFormat formato = new SimpleDateFormat("dd 'de' MMMM", new Locale("es", "CO"));
        String fechaF = formato.format(getPerfil().getFechaNacimiento());
        if (fechaF.equals("")) {
            return "Sin fecha";
        } else {
            return fechaF;
        }
    }

    public String formatearFechaNacimiento() {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "CO"));
        String fechaF = formato.format(getUsuario().getFechaNacimiento());
        if (fechaF.equals("")) {
            return "Sin fecha";
        } else {
            return fechaF;
        }
    }

    public List<Double> getCalificaciones() {
        validaSesion();
        List<Double> puntos = new ArrayList<>();
        List<Relcalificacionusuarios> calificaciones = getUsuario().getRelcalificacionusuariosList();
        for (Relcalificacionusuarios calificacion : calificaciones) {
            puntos.add(calificacion.getCalificacionIdcalificacion().getCalificacion());
        }
        return puntos;
    }

    public List<Double> getCalificacionesPerfil() {
        validaSesion();
        List<Double> puntos = new ArrayList<>();
        List<Relcalificacionusuarios> calificaciones = getPerfil().getRelcalificacionusuariosList();
        for (Relcalificacionusuarios calificacion : calificaciones) {
            puntos.add(calificacion.getCalificacionIdcalificacion().getCalificacion());
        }
        return puntos;
    }

    public Boolean validarSesion() {
        return getUsuario() != null;
    }

    public void validaSesion() {
        if (getUsuario() == null) {
            redireccionar("/faces/index.xhtml?faces-redirect=true");
        }
    }

    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        setUsuario(null);
        this.nombreUsuario = "";
        this.contrasena = "";
        setNombreUsuario(null);
        setContrasena(null);
        redireccionar("/faces/index.xhtml?faces-redirect=true");
    }

    private void redireccionar(String url) {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            ec.redirect(ec.getRequestContextPath() + url);
        } catch (IOException ex) {
            Logger.getLogger(SesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cambiarIdioma(Locale nuevoIdioma){
        this.idiomaSeleccionado=nuevoIdioma;
        FacesContext.getCurrentInstance().getViewRoot().setLocale(this.idiomaSeleccionado);
    }
    
    public String dibujarMenu() {
        validaSesion();
        String rol = getUsuario().getTipoUsuario();
        switch (rol) {
            case "Administrador":
                return "./../../WEB-INF/template/menu_admin.xhtml";
            case "Tecnico":
                return "./../../WEB-INF/template/menu_tecnico.xhtml";
            case "Cliente":
                return "./../../WEB-INF/template/menu_cliente.xhtml";
            default:
                break;
        }
        return null;
    }
    public void irAlInicio() {
        String rol = getUsuario().getTipoUsuario();
        switch (rol) {
            case "Administrador":
                redireccionar("/faces/gestec/usuario/admin.xhtml?faces-redirect=true");
            case "Tecnico":
                redireccionar("/faces/gestec/usuario/tecnico.xhtml?faces-redirect=true");
            case "Cliente":
                redireccionar("/faces/gestec/usuario/cliente.xhtml?faces-redirect=true");
            default:
                break;
        }
    }
    
    public void bloquearCliente(){
        String rol = getUsuario().getTipoUsuario();
        if (rol.equals("Cliente")) {
            redireccionar("/faces/gestec/error/error500.xhtml?faces-redirect=true");
        }
    }
    
    public void bloquearTecnico(){
        String rol = getUsuario().getTipoUsuario();
        if (rol.equals("Tecnico")) {
            redireccionar("/faces/gestec/error/error500.xhtml?faces-redirect=true");
        }
    }
    
    public void bloquearAdmin(){
        String rol = getUsuario().getTipoUsuario();
        if (rol.equals("Administrador")) {
            redireccionar("/faces/gestec/error/error500.xhtml?faces-redirect=true");
        }
    }
    
    public String agregarContacto(Usuarios usuario, Usuarios contacto){   
        getContacto().setIdContacto(usuario);
        getContacto().setIdUsuario(contacto);
        cfl.create(this.contacto);
        return "";
    }
    
    public void editarUsuario(){     
        ufl.edit(usuario);
        redireccionar("faces/index.xhtml?faces-redirect=true");
    }

    public void llenarBarrios(AjaxBehaviorEvent event){ 
        Localidad barriosLocalidad = lfl.llenarBarriosLocalidad(getNumeroLocalidad());
        List<Barrio> barrios = barriosLocalidad.getBarrioList();
        this.nombreBarrios = new ArrayList<>();
        
        for (Barrio barrio : barrios) {
            nombreBarrios.add(barrio.getNombreBarrio());
        }
    }
    
}
