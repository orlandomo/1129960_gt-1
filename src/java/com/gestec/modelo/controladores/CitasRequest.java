package com.gestec.modelo.controladores;

import com.gestec.modelo.entidades.Barrio;
import com.gestec.modelo.entidades.Citas;
import com.gestec.modelo.entidades.Direccion;
import com.gestec.modelo.entidades.Localidad;
import com.gestec.modelo.entidades.Mensaje;
import com.gestec.modelo.entidades.Servicio;
import com.gestec.modelo.entidades.Usuarios;
import com.gestec.modelo.persistencia.BarrioFacadeLocal;
import com.gestec.modelo.persistencia.CitasFacadeLocal;
import com.gestec.modelo.persistencia.DireccionFacadeLocal;
import com.gestec.modelo.persistencia.LocalidadFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author michael
 */
@ManagedBean(name = "citasRequest")
@SessionScoped
public class CitasRequest implements Serializable{
    
    @ManagedProperty(value="#{sesionGestec}")
    private SesionController sesion;

    @EJB
    private CitasFacadeLocal cfl;
    @EJB
    private LocalidadFacadeLocal lfl;
    @EJB
    private DireccionFacadeLocal dfl;
    @EJB
    private BarrioFacadeLocal bfl;

    private Citas cita;
    private Citas nuevaCita;
    private Integer tipoServicio;
    private Integer tipoEquipo;
    private Integer cantidadEquipos;
    private Integer noLocalidad;
    private Integer tecnicos;
    private Servicio servicio;
    private Direccion direccion;
    private Citas citaModificada;
    private String contacto;
    private Usuarios perfil;
    private List<Citas> citas;
    private List<Barrio> nombreBarrios;

    public CitasRequest() {
        this.tecnicos = 1;
    }

    @PostConstruct
    public void init() {
        this.citas = cfl.findAll();
        this.servicio = new Servicio();
        this.direccion = new Direccion();
        this.direccion.setIdBarrio(new Barrio());
        this.direccion.setUsuariosidUsuario(new Usuarios());
    }

    public void setSesion(SesionController sesion) {
        this.sesion = sesion;
    } 
    
    public Citas getCita() {
        return cita;
    }

    public void setCita(Citas cita) {
        this.cita = cita;
    }

    public List<Citas> getCitas() {
        return citas;
    }

    public void setCitas(List<Citas> citas) {
        this.citas = citas;
    }
    

    public Usuarios getPerfil() {
        return perfil;
    }

    public void setPerfil(Usuarios perfil) {
        this.perfil = perfil;
    }

    public Citas getCitaModificada() {
        return citaModificada;
    }

    public void setCitaModificada(Citas citaModificada) {
        this.citaModificada = citaModificada;
    }

    public Citas getNuevaCita() {
        return nuevaCita;
    }

    public void setNuevaCita(Citas nuevaCita) {
        this.nuevaCita = nuevaCita;
    }

    public Integer getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(Integer tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Integer getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(Integer tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public Integer getCantidadEquipos() {
        return cantidadEquipos;
    }

    public void setCantidadEquipos(Integer cantidadEquipos) {
        this.cantidadEquipos = cantidadEquipos;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Integer getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(Integer tecnicos) {
        this.tecnicos = tecnicos;
    }
    
    public List<Direccion> getDirecciones(){
        return sesion.getUsuario().getDireccionList();
    }

    public Integer getNoLocalidad() {
        return noLocalidad;
    }

    public void setNoLocalidad(Integer noLocalidad) {
        this.noLocalidad = noLocalidad;
    }
    
    public List<Localidad> getLocalidades(){
        return sesion.getLocalidades();
    }

    public List<Barrio> getNombreBarrios() {
        return nombreBarrios;
    }

    public void setNombreBarrios(List<Barrio> nombreBarrios) {
        this.nombreBarrios = nombreBarrios;
    }
    
    public String ingresarCita(){
       /*this.direccion.setUsuariosidUsuario(sesion.getUsuario());
        dfl.create(direccion);*/
        return "citas";
    }
    
    public String formatearFechaCita(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd", new Locale("es", "CO"));
        String fechaF = formato.format(fecha);
        if (fechaF.equals("")) {
            return "Sin fecha";
        } else {
            return fechaF;
        }
    }

    public List<Mensaje> mensajes(Citas cita) {
        List<Mensaje> mensajes = cita.getSolicitudIdsolicitud().getMensajeList();
        return mensajes;
    }

    public String ultimoMensaje(Citas cita) {
        int tamañoLista = cita.getSolicitudIdsolicitud().getMensajeList().size() - 1;
        return cita.getSolicitudIdsolicitud().getMensajeList().get(tamañoLista).getMensaje();
    }

    public String verDetalle(Citas cita) {
        setCita(cita);
        return "detalle_cita.xhtml?faces-redirect=true";
    }
    
    public String contacto(){
        this.contacto = cita.getSolicitudIdsolicitud().getMensajeList().get(0).getUsuariosidUsuario().getNombreUsuario() 
                + " " + cita.getSolicitudIdsolicitud().getMensajeList().get(0).getUsuariosidUsuario().getApellido();
        return contacto;
    }
    
    public Usuarios obtenerContacto(){
        return cita.getSolicitudIdsolicitud().getMensajeList().get(0).getUsuariosidUsuario();
    }
    
    public String cancelarCita(Citas cita){
        cita.setEstadoCita("Cancelada");
        cfl.edit(cita);
        return "";
    }
    
    public String editar(Citas cita){
        setCitaModificada(cita);
        return "formulario_citas.xhtml?faces-redirect=true";
    }
    
    public String actualizarCita(){
        cfl.edit(citaModificada);
        return "citas.xhtml?faces-redirect=true";
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
    
    public void validarCita(){
        if(getCitas()==null){
            redireccionar("/faces/gestec/error/error404.xhtml");
        }
    }
    
    public String crearCita(){
        this.cfl.create(getNuevaCita());
        return "";
    }
    
    public void llenarBarrios(AjaxBehaviorEvent event) {

        Localidad barriosLocalidad = lfl.llenarBarriosLocalidad(getNoLocalidad());
        this.nombreBarrios = barriosLocalidad.getBarrioList();

    }
    
    
}