package com.gestec.modelo.controladores;

import com.gestec.modelo.entidades.Barrio;
import com.gestec.modelo.entidades.Citas;
import com.gestec.modelo.entidades.Direccion;
import com.gestec.modelo.entidades.Eventoagenda;
import com.gestec.modelo.entidades.Horadisponibilidad;
import com.gestec.modelo.entidades.Localidad;
import com.gestec.modelo.entidades.Mensaje;
import com.gestec.modelo.entidades.Servicio;
import com.gestec.modelo.entidades.Solicitud;
import com.gestec.modelo.entidades.Usuarios;
import com.gestec.modelo.persistencia.CitasFacadeLocal;
import com.gestec.modelo.persistencia.EventoagendaFacadeLocal;
import com.gestec.modelo.persistencia.HoradisponibilidadFacadeLocal;
import com.gestec.modelo.persistencia.ServicioFacadeLocal;
import com.gestec.modelo.persistencia.SolicitudFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author michael
 */
@Named(value="citasRequest")
@ManagedBean(name =  "citasRequestt")
@SessionScoped
public class CitasRequest implements Serializable {

    @ManagedProperty(value = "#{sesionGestec}")
    private SesionController sesion;

    @EJB
    private CitasFacadeLocal cfl;
    @EJB
    private SolicitudFacadeLocal sfl;
    @EJB
    private HoradisponibilidadFacadeLocal hdfl;
    @EJB
    private EventoagendaFacadeLocal efl;
    @EJB
    private ServicioFacadeLocal sefl;

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
    private Date fechaSolicitud;
    private Solicitud nuevaSolicitud;
    private Solicitud solicitud;
    private Horadisponibilidad horaDisponibilidad;
    private Eventoagenda evento;

    public CitasRequest() {
        this.tecnicos = 1;
        this.fechaSolicitud = new Date();
    }

    @PostConstruct
    public void init() {
        this.citas = cfl.findAll();
        this.servicio = new Servicio();
        this.nuevaSolicitud = new Solicitud();
        this.horaDisponibilidad = new Horadisponibilidad();
        this.horaDisponibilidad.setSolicitudIdsolicitud(new Solicitud());
        this.direccion = new Direccion();
        this.direccion.setIdBarrio(new Barrio());
        this.direccion.setUsuariosidUsuario(new Usuarios());
        this.evento = new Eventoagenda();
        this.evento.setUsuariosidUsuario(new Usuarios());
        this.evento.setCitas(new Citas());
        this.nuevaCita = new Citas();
        this.nuevaCita.setSolicitudIdsolicitud(new Solicitud());
        this.nuevaCita.setServicionoTiquet(new Servicio());
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

    public Integer getNoLocalidad() {
        return noLocalidad;
    }

    public void setNoLocalidad(Integer noLocalidad) {
        this.noLocalidad = noLocalidad;
    }

    public List<Localidad> getLocalidades() {
        return sesion.getLocalidades();
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Solicitud getNuevaSolicitud() {
        return nuevaSolicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.nuevaSolicitud = solicitud;
    }

    public Horadisponibilidad getHoraDisponibilidad() {
        return horaDisponibilidad;
    }

    public void setHoraDisponibilidad(Horadisponibilidad horaDisponibilidad) {
        this.horaDisponibilidad = horaDisponibilidad;
    }

    public Eventoagenda getEvento() {
        return evento;
    }

    public void setEvento(Eventoagenda evento) {
        this.evento = evento;
    }
    
    public Integer validarFiltro(){
        if (this.tecnicos==1) {
            return 1;
        }
        if (this.tecnicos==2) {
            return 2;
        }
        if (this.tecnicos==3) {
            return 3;
        }
        return null;
    }

    public void ingresarCita() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        FacesMessage msj;

        if (this.horaDisponibilidad.getHoraInicio().compareTo(this.horaDisponibilidad.getHoraFin()) > 0) {
            msj = new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                  "La fecha inicial no puede ser mayor a la fecha final", "");
            fc.addMessage(null, msj);
        } 
        else {

            this.nuevaSolicitud.setDireccionidDireccion(sesion.getUsuario().getDireccionList().get(0));
            this.nuevaSolicitud.setSolicitudFecha(fechaSolicitud);
            sfl.create(nuevaSolicitud);
            List<Solicitud> s = sfl.findAll();
            int t = s.size() - 1;
            this.solicitud = s.get(t);
            this.horaDisponibilidad.setSolicitudIdsolicitud(solicitud);
            hdfl.create(horaDisponibilidad);
            this.servicio.setDescripcionServicio("Descripcion");
            this.servicio.setCostoServicio("Costo");
            this.servicio.setGarantia(new Date());
            this.servicio.setFechaServicio(new Date());
            this.servicio.setEstadoServicio("Solicitado");
            sefl.create(servicio);
            List<Servicio> se = sefl.findAll();
            int ta = se.size() - 1;
            this.servicio = se.get(ta);
            this.nuevaCita.setDuracionCita("Duracion");
            this.nuevaCita.setEstadoCita("Solicitado");
            this.nuevaCita.setFiltro(validarFiltro());
            this.nuevaCita.setSolicitudIdsolicitud(solicitud);
            this.nuevaCita.setServicionoTiquet(servicio);
            this.evento.setFechaFin(this.horaDisponibilidad.getHoraFin());
            this.evento.setFechaInicio(this.horaDisponibilidad.getHoraInicio());
            this.evento.setNombreEvento("Servicio " + sesion.getUsuario().getNombreUsuario());
            this.evento.setTipoEvento("Servicio");
            this.evento.setUsuariosidUsuario(sesion.getUsuario());
            List<Citas> c = cfl.findAll();
            int tam = c.size() - 1;
            Citas citaEvento = c.get(tam);
            this.evento.setCitas(citaEvento);
            efl.create(evento);
            List<Eventoagenda> ea = efl.findAll();
            int tama = ea.size() - 1;
            Eventoagenda eventoCita = ea.get(tama);
            this.nuevaCita.setEventoAgenda(eventoCita);
            cfl.create(nuevaCita);
        }

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

    public String contacto() {
        this.contacto = cita.getSolicitudIdsolicitud().getMensajeList().get(0).getUsuariosidUsuario().getNombreUsuario()
                + " " + cita.getSolicitudIdsolicitud().getMensajeList().get(0).getUsuariosidUsuario().getApellido();
        return contacto;
    }

    public Usuarios obtenerContacto() {
        return cita.getSolicitudIdsolicitud().getMensajeList().get(0).getUsuariosidUsuario();
    }

    public String cancelarCita(Citas cita) {
        cita.setEstadoCita("Cancelada");
        cfl.edit(cita);
        return "";
    }

    public String editar(Citas cita) {
        setCitaModificada(cita);
        return "formulario_citas.xhtml?faces-redirect=true";
    }

    public String actualizarCita() {
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

    public void validarCita() {
        if (getCitas() == null) {
            redireccionar("/faces/gestec/error/error404.xhtml");
        }
    }

    public String crearCita() {
        this.cfl.create(getNuevaCita());
        return "";
    }

}
