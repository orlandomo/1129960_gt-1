package com.gestec.modelo.controladores;

import com.gestec.modelo.entidades.Barrio;
import com.gestec.modelo.entidades.Citas;
import com.gestec.modelo.entidades.Direccion;
import com.gestec.modelo.entidades.Eventoagenda;
import com.gestec.modelo.entidades.Horadisponibilidad;
import com.gestec.modelo.entidades.Localidad;
import com.gestec.modelo.entidades.Mensaje;
import com.gestec.modelo.entidades.Notificacion;
import com.gestec.modelo.entidades.NotificacionCita;
import com.gestec.modelo.entidades.NotificacionUsuario;
import com.gestec.modelo.entidades.Relcalificacionusuarios;
import com.gestec.modelo.entidades.Relsolicitudtipo;
import com.gestec.modelo.entidades.Servicio;
import com.gestec.modelo.entidades.Solicitud;
import com.gestec.modelo.entidades.Tiposervicio;
import com.gestec.modelo.entidades.Usuarios;
import com.gestec.modelo.persistencia.CitasFacadeLocal;
import com.gestec.modelo.persistencia.DireccionFacadeLocal;
import com.gestec.modelo.persistencia.EventoagendaFacadeLocal;
import com.gestec.modelo.persistencia.HoradisponibilidadFacadeLocal;
import com.gestec.modelo.persistencia.LocalidadFacadeLocal;
import com.gestec.modelo.persistencia.MensajeFacadeLocal;
import com.gestec.modelo.persistencia.NotificacionCitaFacadeLocal;
import com.gestec.modelo.persistencia.NotificacionFacadeLocal;
import com.gestec.modelo.persistencia.NotificacionUsuarioFacadeLocal;
import com.gestec.modelo.persistencia.RelsolicitudtipoFacadeLocal;
import com.gestec.modelo.persistencia.ServicioFacadeLocal;
import com.gestec.modelo.persistencia.SolicitudFacadeLocal;
import com.gestec.modelo.persistencia.UsuariosFacadeLocal;
import java.io.ByteArrayInputStream;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author michael
 */
@Named(value = "citasRequest")
@ManagedBean(name = "citasRequestt")
@SessionScoped
public class CitasRequest implements Serializable {

    @Inject
    private SesionController sesion;

    @EJB
    private UsuariosFacadeLocal ufl;
    @EJB
    private MensajeFacadeLocal mfl;
    @EJB
    private CitasFacadeLocal cfl;
    @EJB
    private SolicitudFacadeLocal sfl;
    @EJB
    private HoradisponibilidadFacadeLocal hdfl;
    @EJB
    private DireccionFacadeLocal dfl;
    @EJB
    private EventoagendaFacadeLocal efl;
    @EJB
    private ServicioFacadeLocal sefl;
    @EJB
    private RelsolicitudtipoFacadeLocal tsfl;
    @EJB
    private NotificacionCitaFacadeLocal ncfl;
    @EJB
    private NotificacionUsuarioFacadeLocal nufl;
    @EJB
    private NotificacionFacadeLocal nfl;
    @EJB
    private LocalidadFacadeLocal lfl;

    private Citas cita;
    private Citas nuevaCita;
    private Integer tipoServicio;
    private Integer tipoEquipo;
    private Integer cantidadEquipos;
    private Integer numeroLocalidad;
    private Integer numeroBarrio;
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
    private Relsolicitudtipo tipoSolicitud;
    private NotificacionCita notificacionCita;
    private Notificacion notificacion;
    private List<Barrio> barrios;
    private Integer orden;
    private Integer orientacion;
    private List<Usuarios> tecnicosCita;
    private String nombreTecnico;
    private Usuarios tecnicoCita;
    private Citas citaEvento;
    private Eventoagenda eventoCita;
    private Date horaInicio;
    private Date horaFin;
    private Date fechaCita;
    private String descripcionEvento;
    private NotificacionUsuario notificacionUsuario;
    private Mensaje mensaje;

    public CitasRequest() {
        this.numeroBarrio = 0;
        this.numeroLocalidad = 0;
        this.tecnicos = 1;
        this.fechaSolicitud = new Date();
    }

    @PostConstruct
    public void init() {
        this.mensaje = new Mensaje();
        this.tecnicosCita = ufl.listarTecnicos();
        this.citas = cfl.listarCitas();
        this.servicio = new Servicio();
        this.nuevaSolicitud = new Solicitud();
        this.horaDisponibilidad = new Horadisponibilidad();
        this.horaDisponibilidad.setSolicitudIdsolicitud(new Solicitud());
        this.direccion = new Direccion();
        this.direccion.setIdBarrio(new Barrio());
        this.direccion.setUsuariosidUsuario(new Usuarios());
        this.evento = new Eventoagenda();
        this.evento.setUsuariosidUsuario(new Usuarios());
        this.nuevaCita = new Citas();
        this.nuevaCita.setSolicitudIdsolicitud(new Solicitud());
        this.nuevaCita.setServicionoTiquet(new Servicio());
        this.tipoSolicitud = new Relsolicitudtipo();
        this.tipoSolicitud.setSolicitudIdsolicitud(new Solicitud());
        this.tipoSolicitud.setTipoServicioidtipoServicio(new Tiposervicio());
        this.notificacionCita = new NotificacionCita();
        this.notificacionCita.setIdCita(new Citas());
        this.notificacionCita.setIdNotificacion(new Notificacion());
        this.notificacion = nfl.find(5);
        this.barrios = new ArrayList<>();
        this.notificacionUsuario = new NotificacionUsuario();
        this.notificacionUsuario.setIdNotificacion(new Notificacion());
        this.notificacionUsuario.setIdUsuario(new Usuarios());
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

    public Integer getNumeroLocalidad() {
        return numeroLocalidad;
    }

    public void setNumeroLocalidad(Integer noLocalidad) {
        this.numeroLocalidad = noLocalidad;
    }

    public Integer getNumeroBarrio() {
        return numeroBarrio;
    }

    public void setNumeroBarrio(Integer numeroBarrio) {
        this.numeroBarrio = numeroBarrio;
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

    public List<Barrio> getBarrios() {
        return barrios;
    }

    public void setBarrios(List<Barrio> barrios) {
        this.barrios = barrios;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Integer orientacion) {
        this.orientacion = orientacion;
    }

    public List<Usuarios> getTecnicosCita() {
        return tecnicosCita;
    }

    public void setTecnicosCita(List<Usuarios> tecnicosCita) {
        this.tecnicosCita = tecnicosCita;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }

    public void setTecnicoCita(Usuarios tecnicoCita) {
        this.tecnicoCita = tecnicoCita;
    }

    public Usuarios getTecnicoCita() {
        return tecnicoCita;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getNTecnico() {
        if (this.tecnicoCita == null) {
            return "";
        }
        return this.tecnicoCita.getNombreUsuario() + " " + this.tecnicoCita.getApellido();
    }

    public List<Double> getCalificaciones(Usuarios tecnico) {
        List<Double> ptos = new ArrayList<>();
        List<Relcalificacionusuarios> calif = tecnico.getRelcalificacionusuariosList();
        for (Relcalificacionusuarios calificacion : calif) {
            ptos.add(calificacion.getCalificacionIdcalificacion().getCalificacion());
        }
        return ptos;
    }

    public List<Direccion> getDireccion(Usuarios tecnico) {
        return tecnico.getDireccionList();
    }

    public StreamedContent getImagenTecnico() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String parametro = context.getExternalContext().getRequestParameterMap().get("imgTecnico");
            byte[] imagen = ufl.find(Integer.valueOf(parametro)).getFotoPerfil();
            if (sesion.getUsuario().getFotoPerfil() == null) {
                return new DefaultStreamedContent(new ByteArrayInputStream(ufl.find(1).getFotoPerfil()));
            }
            return new DefaultStreamedContent(new ByteArrayInputStream(imagen));
        }
    }

    public Integer validarFiltro() {
        if (this.tecnicos == 1) {
            return 1;
        }
        if (this.tecnicos == 2) {
            return 2;
        }
        if (this.tecnicos == 3) {
            return 3;
        }
        return null;
    }

    public void registrarCita() {
        this.nuevaSolicitud.setDireccionidDireccion(sesion.getUsuario().getDireccionList().get(0));
        this.nuevaSolicitud.setSolicitudFecha(fechaSolicitud);
        sfl.create(nuevaSolicitud);
        List<Solicitud> s = sfl.findAll();
        int t = s.size() - 1;
        this.solicitud = s.get(t);
        this.horaDisponibilidad.setSolicitudIdsolicitud(solicitud);
        this.horaDisponibilidad.setHoraInicio(horaInicio);
        this.horaDisponibilidad.setHoraFin(horaFin);
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
        this.nuevaCita.setFechaCita(fechaCita);
        this.nuevaCita.setFiltro(validarFiltro());
        this.nuevaCita.setSolicitudIdsolicitud(solicitud);
        this.nuevaCita.setServicionoTiquet(servicio);
        this.evento.setFechaFin(this.horaDisponibilidad.getHoraFin());
        this.evento.setFechaInicio(this.horaDisponibilidad.getHoraInicio());
        this.evento.setDescripcionEvento(descripcionEvento);
        this.evento.setNombreEvento("Servicio " + sesion.getUsuario().getNombreUsuario());
        this.evento.setTipoEvento("Servicio");
        this.evento.setUsuariosidUsuario(sesion.getUsuario());
        efl.create(evento);
        List<Eventoagenda> ea = efl.findAll();
        int tama = ea.size() - 1;
        this.eventoCita = ea.get(tama);
        this.nuevaCita.setEventoAgenda(eventoCita);
        cfl.create(nuevaCita);
        this.tipoSolicitud.getTipoServicioidtipoServicio().setIdtipoServicio(this.tipoServicio);
        this.tipoSolicitud.setSolicitudIdsolicitud(this.solicitud);
        tsfl.create(tipoSolicitud);
        List<Citas> c = cfl.findAll();
        int tam = c.size() - 1;
        this.citaEvento = c.get(tam);
    }

    public void ingresarSolicitud() {
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage msj = new FacesMessage();

        if (this.horaInicio.compareTo(this.horaFin) > 0) {
            msj = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "La fecha inicial no puede ser mayor a la fecha final", "");
        } else if (validarFiltro() == 2) {
            redireccionar("/faces/gestec/cita/busqueda_tecnico.xhtml?faces-redirect=true");
        } else {
            try {
                registrarCita();
            } catch (Exception e) {
                msj = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error al registrar la cita", "");
            }
        }
        fc.addMessage(null, msj);

    }

    public void agendarCita() {
        registrarCita();
        this.notificacionCita.setIdCita(citaEvento);
        this.notificacionCita.setIdNotificacion(this.notificacion);
        this.notificacionCita.setEstadoNotificacion("Enviado");
        this.notificacionCita.setFechaNotificacion(new Date());
        ncfl.create(notificacionCita);
        this.mensaje.setEstadoMensaje("Enviado");
        this.mensaje.setFechaMensaje(new Date());
        this.mensaje.setMensaje(descripcionEvento);
        this.mensaje.setSolicitudIdsolicitud(this.solicitud);
        this.mensaje.setUsuariosidUsuario(tecnicoCita);
        mfl.create(mensaje);
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

    public void verDetalleNotificacion(Citas cita, NotificacionCita not) {
        setCita(cita);
        not.setEstadoNotificacion("Visto");
        ncfl.edit(not);
        redireccionar("/faces/gestec/cita/detalle_cita.xhtml?faces-redirect=true");
    }

    public String contacto() {
        this.contacto = cita.getSolicitudIdsolicitud().getMensajeList().get(0).getUsuariosidUsuario().getNombreUsuario()
                + " " + cita.getSolicitudIdsolicitud().getMensajeList().get(0).getUsuariosidUsuario().getApellido();
        return contacto;
    }

    public Usuarios obtenerContacto() {
        return cita.getSolicitudIdsolicitud().getMensajeList().get(0).getUsuariosidUsuario();
    }

    public void cancelarCita() {
        citaModificada.setEstadoCita("Cancelada");
        cfl.edit(citaModificada);
        this.notificacionUsuario.getIdNotificacion().setIdNotificacion(6);
        Usuarios usuario = new Usuarios();
        if (sesion.getUsuario().getTipoUsuario().equals("Tecnico")) {
            usuario = citaModificada.getSolicitudIdsolicitud().getDireccionidDireccion().getUsuariosidUsuario();
        } else if (sesion.getUsuario().getTipoUsuario().equals("Cliente")) {
            usuario = citaModificada.getSolicitudIdsolicitud().getMensajeList().get(0).getUsuariosidUsuario();
        }
        this.notificacionUsuario.setIdUsuario(usuario);
        this.notificacionUsuario.setEstadoNotificacion("Enviado");
        this.notificacionUsuario.setFechaNotificacion(new Date());
        nufl.create(notificacionUsuario);
        this.citas = cfl.listarCitas();
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

    public void llenarBarrios(AjaxBehaviorEvent event) {
        if (getNumeroLocalidad() < 21 && getNumeroLocalidad() > 0) {
            Localidad barriosLocalidad = lfl.llenarBarriosLocalidad(getNumeroLocalidad());
            this.barrios = barriosLocalidad.getBarrioList();

        } else {
            this.barrios = new ArrayList<>();
        }
        filtrarTecnicos();
    }

    public void filtrarLocalidad() {
        Localidad localidad = lfl.llenarBarriosLocalidad(numeroLocalidad);
        List<Barrio> barriosTecnicos = localidad.getBarrioList();
        for (Barrio barriosTecnico : barriosTecnicos) {
            List<Direccion> direccionesTecnicos = dfl.buscarPorBarrio(barriosTecnico.getIdBarrio());
            if (direccionesTecnicos.size() > 0) {
                for (Direccion direc : direccionesTecnicos) {
                    if (direc.getUsuariosidUsuario().getTipoUsuario().equals("Tecnico")) {
                        tecnicosCita.add(direc.getUsuariosidUsuario());
                    }
                }

            }
        }
    }

    public void filtrarBarrio() {

        List<Direccion> direccionesTecnicos = dfl.buscarPorBarrio(this.numeroBarrio);
        if (direccionesTecnicos.size() > 0) {
            for (Direccion direc : direccionesTecnicos) {
                if (direc.getUsuariosidUsuario().getTipoUsuario().equals("Tecnico")) {
                    tecnicosCita.add(direc.getUsuariosidUsuario());
                }
            }
        }
    }

    /*public void filtrarNombre() {
        filtrarLocalidad();
        filtrarBarrio();
        List<Usuarios> tecnicos2 = new ArrayList<>();
        for (Usuarios tec : this.tecnicosCita) {
            String d = this.nombreTecnico;
            String f = tec.getNombreUsuario() + " " + tec.getApellido();
            int t1 = d.length();
            int tamaño = f.length();
            int coincidencias = 0;
            int hasta = (tamaño - t1) + 1;

            if (hasta > 0) {
                String h = d.substring(0, t1);
                for (int i = 0; i < hasta; i++) {
                    String k = f.substring(i, t1);
                    if (h.equals(k)) {
                        tecnicos2.add(tec);
                        i = hasta;
                    }
                    t1++;
                }
            }
        }
        this.tecnicosCita = tecnicos2;
    }*/
    public void filtrarTecnicos() {

        this.tecnicosCita = new ArrayList<>();

        if (this.numeroBarrio == 0) {
            if (this.numeroLocalidad == 0) {
                this.tecnicosCita = ufl.listarTecnicos();
            } else {
                filtrarLocalidad();
            }

        } else {
            filtrarBarrio();
        }

    }
}
