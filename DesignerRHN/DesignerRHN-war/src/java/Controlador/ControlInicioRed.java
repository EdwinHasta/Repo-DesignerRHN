package Controlador;

import Banner.BannerInicioRed;
import Entidades.Conexiones;
import Entidades.Recordatorios;
import InterfaceAdministrar.AdministrarInicioRedInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlInicioRed implements Serializable {

    @EJB
    AdministrarInicioRedInterface administrarInicioRed;
    private String usuario, contraseña, baseDatos;
    private boolean estadoInicio, cambioClave, modulosDesigner;
    private String txtBoton;
    private List<String> actualizaciones;
    private List<BannerInicioRed> banner;
    private boolean inicioSession;
    //FECHA ACTUAL
    private Date fechaActual;
    private SimpleDateFormat formatoFechaActual, formatoAño;
    private String mostrarFecha, añoActual;
    //RECORDATORIOS
    private Recordatorios recordatorio;
    private List<String> listaRecordatorios;
    private List<Recordatorios> listaConsultas;
    private boolean acceso;
    //NOMBRE EMPRESA
    private String nombreEmpresa;
    //CAMBIO CLAVE
    private String NClave, Rclave;
    private String msgSesion;

    public ControlInicioRed() {
        cambioClave = true;
        estadoInicio = false;
        modulosDesigner = true;
        txtBoton = "Conectar";
        listaConsultas = new ArrayList<Recordatorios>();
        actualizaciones = new ArrayList<String>();
        actualizaciones.add("form:btnLogin");
        actualizaciones.add("form:btnCambioClave");
        actualizaciones.add("form:btnPersonal");
        actualizaciones.add("form:btnNomina");
        actualizaciones.add("form:btnIntegracion");
        actualizaciones.add("form:btnGerencial");
        actualizaciones.add("form:btnDesigner");
        actualizaciones.add("form:nombreEmpresa");
        actualizaciones.add("form:usuario");
        actualizaciones.add("form:contrasenha");
        actualizaciones.add("form:baseDatos");
        actualizaciones.add("form:bannerConsultas");
        banner = new ArrayList<BannerInicioRed>();
        banner.add(new BannerInicioRed("9091310.png", "http://www.nomina.com.co/"));
        banner.add(new BannerInicioRed("primefaces.png", ""));
        banner.add(new BannerInicioRed("Java.jpg", ""));
        banner.add(new BannerInicioRed("eclipseLink.jpeg", ""));
        banner.add(new BannerInicioRed("glassfish_logo.png", ""));
        banner.add(new BannerInicioRed("java.png", ""));
        banner.add(new BannerInicioRed("Jsf-logo.png", ""));
        //FECHA ACTUAL
        formatoFechaActual = new SimpleDateFormat("EEEEE dd 'de' MMMMM 'de' yyyy");
        formatoAño = new SimpleDateFormat("yyyy");
        //INICIO SESSION DEFAULT
        inicioSession = true;
        acceso = false;
        msgSesion = "Iniciando sesión, por favor espere...";
    }
    
    public void ingresar() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            FacesContext contexto = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) contexto.getExternalContext().getSession(false);
            if (estadoInicio == false) {
                if (!baseDatos.equals("") && !usuario.equals("") && !contraseña.equals("")) {
                    if (administrarInicioRed.conexionInicial(baseDatos)) {
                        if (administrarInicioRed.validarUsuario(usuario)) {
                            if (administrarInicioRed.conexionUsuario(baseDatos, usuario, contraseña)) {
                                if (administrarInicioRed.validarConexionUsuario(ses.getId())) {
                                    cambioClave = false;
                                    estadoInicio = true;
                                    modulosDesigner = false;
                                    txtBoton = "Desconectar";
                                    acceso = true;
                                    getNombreEmpresa();
                                    listaRecordatorios = administrarInicioRed.recordatoriosInicio();
                                    if (listaRecordatorios != null) {
                                        for (int i = 0; i < listaRecordatorios.size(); i++) {
                                            contexto.addMessage(null, new FacesMessage("", listaRecordatorios.get(i)));
                                        }
                                        context.update("form:growl");
                                    }
                                    listaConsultas = administrarInicioRed.consultasInicio();
                                    if (listaConsultas != null || !listaConsultas.isEmpty()) {
                                        banner.clear();
                                        for (int j = 0; j < listaConsultas.size(); j++) {
                                            if (listaConsultas.get(j).getNombreimagen() != null) {
                                                banner.add(new BannerInicioRed(listaConsultas.get(j).getNombreimagen(), ""));
                                            } else {
                                                banner.add(new BannerInicioRed("SinImagen.JPG", ""));
                                            }
                                        }
                                    } else {
                                        llenarBannerDefaul();
                                    }
                                    HttpServletRequest request = (HttpServletRequest) (contexto.getExternalContext().getRequest());
                                    String Ip, nombreEquipo;
                                    java.net.InetAddress localMachine;
                                    if (request.getRemoteAddr().startsWith("127.0.0.1")) {
                                        localMachine = java.net.InetAddress.getLocalHost();
                                        Ip = localMachine.getHostAddress();
                                    } else {
                                        Ip = request.getRemoteAddr();
                                    }
                                    localMachine = java.net.InetAddress.getByName(Ip);
                                    nombreEquipo = localMachine.getHostName();
                                    Conexiones conexion = new Conexiones();
                                    conexion.setDireccionip(Ip);
                                    conexion.setEstacion(nombreEquipo);
                                    conexion.setSecuencia(BigInteger.valueOf(1));
                                    conexion.setUltimaentrada(new Date());
                                    conexion.setUsuarioso(System.getProperty("os.name") + " / " + System.getProperty("user.name"));
                                    conexion.setUsuariobd(administrarInicioRed.usuarioBD());
                                    administrarInicioRed.guardarDatosConexion(conexion);
                                    context.update(actualizaciones);
                                } else {
                                    inicioSession = true;
                                    sessionEntradaDefault();
                                    contexto.addMessage(null, new FacesMessage("", "La contraseña puede ser incorrecta, ha expirado ó esta bloqueada."));
                                    context.update("form:informacionAcceso");
                                }
                            } else {
                                inicioSession = true;
                                sessionEntradaDefault();
                                contexto.addMessage(null, new FacesMessage("", "No se pudo crear el EntityManager, comuniquese con soporte.."));
                                context.update("form:informacionAcceso");
                            }
                        } else {
                            inicioSession = true;
                            sessionEntradaDefault();
                            contexto.addMessage(null, new FacesMessage("", "El usuario no existe o esta inactivo"));
                            context.update("form:informacionAcceso");
                        }
                    } else {
                        inicioSession = true;
                        sessionEntradaDefault();
                        contexto.addMessage(null, new FacesMessage("", "Base de datos incorrecta."));
                        context.update("form:informacionAcceso");
                    }
                } else {
                    contexto.addMessage(null, new FacesMessage("", "Existen campos vacios."));
                    context.update("form:informacionAcceso");
                }
            } else {
                administrarInicioRed.cerrarSession(ses.getId());
                cambioClave = true;
                modulosDesigner = true;
                txtBoton = "Conectar";
                estadoInicio = false;
                getNombreEmpresa();
                llenarBannerDefaul();
                context.update(actualizaciones);
                context.update("form:growl");
                inicioSession = true;
                acceso = false;
                sessionEntradaDefault();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void validarDialogoSesion() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (estadoInicio == false) {
            msgSesion = "Iniciando sesión, por favor espere...";
            context.update("formularioDialogos:estadoSesion");
            context.execute("estadoSesion.show()");
        } else {
            msgSesion = "Cerrando sesión, por favor espere...";
            context.update("formularioDialogos:estadoSesion");
            context.execute("estadoSesion.show()");
        }
    }

    public void llenarBannerDefaul() {
        banner.clear();
        banner.add(new BannerInicioRed("9091310.png", "http://www.nomina.com.co/"));
        banner.add(new BannerInicioRed("primefaces.png", ""));
        banner.add(new BannerInicioRed("Java.jpg", ""));
        banner.add(new BannerInicioRed("eclipseLink.jpeg", ""));
        banner.add(new BannerInicioRed("glassfish_logo.png", ""));
        banner.add(new BannerInicioRed("java.png", ""));
        banner.add(new BannerInicioRed("Jsf-logo.png", ""));
    }

    public void sessionEntradaDefault() {
        if (inicioSession == true) {
            acceso = administrarInicioRed.conexionDefault();
            inicioSession = false;
        }
    }

    public void nuevoRecordatorio() {
        getRecordatorio();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:recordatorio");
    }

    public void inicioComponentes() throws IOException {
        sessionEntradaDefault();
        getNombreEmpresa();
        getRecordatorio();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:nombreEmpresa");
        context.update("form:recordatorio");
    }

    public void cambiarClave() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Nclave: " + NClave + " Rclave: " + Rclave);
        if (!NClave.equals("") && !Rclave.equals("")) {
            if (NClave.equals(Rclave)) {
                int transaccion = administrarInicioRed.cambioClave(usuario, NClave);
                if (transaccion == 0) {
                    NClave = null;
                    Rclave = null;
                    context.execute("cambiarClave.hide();");
                    context.execute("exitoCambioClave.show();");
                } else if (transaccion == 28007) {
                    context.execute("errorCambioClaveReusar.show();");
                } else if (transaccion == -1) {
                    context.execute("cambiarClave.hide();");
                    System.out.println("El entity manager Factory no se ha creado, revisar.");
                }
            } else {
                context.execute("errorCambioClave.show();");
            }
        } else {
            context.execute("errorCambioClaveCamposVacios.show();");
        }
    }

    public void cancelarCambioClave() {
        NClave = null;
        Rclave = null;
    }
    //GETTER AND SETTER

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario.toUpperCase();
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña.toLowerCase();
    }

    public String getBaseDatos() {
        return baseDatos;
    }

    public void setBaseDatos(String baseDatos) {
        this.baseDatos = baseDatos.toUpperCase();
    }

    public boolean isCambioClave() {
        return cambioClave;
    }

    public String getTxtBoton() {
        return txtBoton;
    }

    public boolean isModulosDesigner() {
        return modulosDesigner;
    }

    public List<BannerInicioRed> getBanner() {
        return banner;
    }

    public String getMostrarFecha() {
        fechaActual = new Date();
        mostrarFecha = formatoFechaActual.format(fechaActual).toUpperCase();
        añoActual = formatoAño.format(fechaActual);
        return mostrarFecha;
    }

    public String getAñoActual() {
        return añoActual;
    }

    public Recordatorios getRecordatorio() {
        if (acceso == true) {
            recordatorio = administrarInicioRed.recordatorioAleatorio();
        }
        return recordatorio;
    }

    public void metodo() throws IOException {
        /* FacesContext context = FacesContext.getCurrentInstance();
         Map<String, String> map = context.getExternalContext().getRequestParameterMap();
         String name = map.get("n"); // name attribute of node
         String type = map.get("t"); // type attribute of node
         System.out.println(name + " " + type);*/
        //System.out.println("Sexy Floww!!!");
        /*BufferedImage img = ImageIO.read(new File("C:\\plantilla.JPG"));
         Graphics2D g = img.createGraphics();
         g.setFont(new Font("Book Antiqua", Font.ITALIC, 38));
         g.setColor(Color.WHITE);
         g.drawString("Empleados que pronto se", 20, 50);
         g.drawString("les vencera el contrato", 20, 90);
         g.drawString("fijo.", 20, 130);
         g.dispose();
         ImageIO.write(img, "jpg", new File("C:\\plantillaContrato.JPG"));*/

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("", "Recuerde pagar la nomina el 30 y no el 5 porque o sino lo linchan."));
        context.addMessage(null, new FacesMessage("", "Recuerde pagar la nomina el 30 y no el 5 porque o sino lo linchan."));
        context.addMessage(null, new FacesMessage("", "Recuerde pagar la nomina el 30 y no el 5 porque o sino lo linchan."));
        context.addMessage(null, new FacesMessage("", "Recuerde pagar la nomina el 30 y no el 5 porque o sino lo linchan."));
        context.addMessage(null, new FacesMessage("", "Recuerde pagar la nomina el 30 y no el 5 porque o sino lo linchan."));
        RequestContext.getCurrentInstance().update("form:growl");
    }

    public String getNombreEmpresa() {
        if (acceso == true) {
            nombreEmpresa = administrarInicioRed.nombreEmpresaPrincipal();
        }
        return nombreEmpresa;
    }

    public boolean isEstadoInicio() {
        return estadoInicio;
    }

    public List<Recordatorios> getListaConsultas() {
        return listaConsultas;
    }

    public void setListaConsultas(List<Recordatorios> listaConsultas) {
        this.listaConsultas = listaConsultas;
    }

    public String getNClave() {
        return NClave;
    }

    public void setNClave(String NClave) {
        this.NClave = NClave.toUpperCase();
    }

    public String getRclave() {
        return Rclave;
    }

    public void setRclave(String Rclave) {
        this.Rclave = Rclave.toUpperCase();
    }

    public String getMsgSesion() {
        return msgSesion;
    }
}
