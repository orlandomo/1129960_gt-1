/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*

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
    private String mensajeError;
    private List<Localidad> localidades;
    private List<Barrio> barrios;
    private Usuarios perfil;
    private Usuarios usuario;
    private Contactos contacto;

    @PostConstruct
    public void init(){
        this.mensajeError = "";
        this.contacto = new Contactos();
        this.localidades = lfl.findAll();
        this.barrios = bfl.findAll();
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

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public void iniciarSesion() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        if (this.nombreUsuario != null && !this.nombreUsuario.equals("")
                && this.contrasena != null && !this.contrasena.equals("")) {
            this.usuario = ufl.iniciarSesion(nombreUsuario, contrasena);
            if (usuario != null) {
                ec.getSessionMap().put("user", usuario);
                switch (usuario.getTipoUsuario()) {
                    case "Administrador":
                        redireccionar("/faces/gestec/usuario/admin.xhtml?faces-redirect=true");
                        break;
                    case "Cliente":
                        redireccionar("/faces/gestec/usuario/cliente.xhtml?faces-redirect=true");
                        break;
                    case "Tecnico":
                         redireccionar("/faces/gestec/usuario/tecnico.xhtml?faces-redirect=true");
                        break;
                    default:
                        break;
                }
            } else {
                this.mensajeError = "Datos incorrectos";
            }
        } 
        else if(this.nombreUsuario == null || this.nombreUsuario.equals("")){
            this.mensajeError = "Digite su nombre de usuario";
        }
        else if(this.contrasena != null || !this.contrasena.equals("")){
            this.mensajeError = "Digite su contraseña";
        }
 
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
        this.nombreUsuario = "";
        this.contrasena = "";
        setUsuario(null);
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





<h:inputText class="form-control" id="usuario" pt:placeholder="Nombre usuario" value="#{sesionGestec.nombreUsuario}"/>
                                    </div>
                                    <div class="form-group">
                                        <h:inputSecret class="form-control" id="contrasena" pt:placeholder="Contraseña" value="#{sesionGestec.contrasena}"/>
                                    </div>
                                    <div style="padding: 5px;text-align: center;color: #ddd;" class="col-md-12">
                                        <h:outputText id="mensajeError" value="#{sesionGestec.mensajeError}"/>
                                    </div>
                                    <div class="check checkbox">
                                        <label>
                                            <input type="checkbox"/> No cerrar sesión
                                        </label>
                                    </div>            
                                </div>
                                <div class="pie-modal">
                                    <div class="col-xs-12 col-md-6 center"><a data-toggle="modal" data-target="#myModal4"><h5>Olvidé mi contraseña</h5></a></div>
                                    <div class="col-xs-12  col-md-6 center"><a data-toggle="modal" data-target="#myModal3"><h5>¿Su usuario esta bloqueado?</h5></a></div>

                                    <div class="center">
                                        <h:commandButton id="botonValidar" class="btn btn-primary" 
                                                         value="Ingresar" actionListener="#{sesionGestec.iniciarSesion()}">
                                            <f:ajax execute="usuario contrasena" render="mensajeError"/>
                                        </h:commandButton>




}*/
