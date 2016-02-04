package Controlador;

import Entidades.ActualUsuario;
import Entidades.DetallesEmpresas;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTemplateInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class ControlTemplate implements Serializable {

    @EJB
    AdministrarTemplateInterface administrarTemplate;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private ActualUsuario actualUsuario;
    private String nombreUsuario;
    private StreamedContent logoEmpresa;
    private StreamedContent fotoUsuario;
    private FileInputStream fis;
    private final String acercaDe;
    private final String webSite;
    private final String linkSoporte;
    private DetallesEmpresas detalleEmpresa;

    public ControlTemplate() {
        acercaDe="acercade";
        webSite="www.nomina.com.co";
        linkSoporte="Teamviewer";
    }

    @PostConstruct
    public void inicializarAdministrador() {
        System.out.println("Inicializando Template.");
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTemplate.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
            actualUsuario = administrarTemplate.consultarActualUsuario();
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlTemplate: " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void informacionUsuario() {
        if (actualUsuario != null) {
            String rutaFoto = administrarTemplate.rutaFotoUsuario();
            if (rutaFoto != null) {
                String bckRuta = rutaFoto;
                try {
                    rutaFoto = rutaFoto + actualUsuario.getAlias() + ".jpg";
                    fis = new FileInputStream(new File(rutaFoto));
                    fotoUsuario = new DefaultStreamedContent(fis, "image/jpg");
                } catch (FileNotFoundException e) {
                    try {
                        System.out.println("El usuario no tiene una foto asignada: " + rutaFoto);
                        rutaFoto = bckRuta + "sinFoto.jpg";
                        fis = new FileInputStream(new File(rutaFoto));
                        fotoUsuario = new DefaultStreamedContent(fis, "image/jpg");
                    } catch (FileNotFoundException ex) {
                        System.out.println("No se encontro el siguiente archivo, verificar. \n" + rutaFoto);
                    }
                }
            }
        }
    }

    public void cerrarSession() throws IOException {
        FacesContext x = FacesContext.getCurrentInstance();
        HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
        administrarTemplate.cerrarSession(ses.getId());
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath() + "/iniciored.xhtml");
    }

    public void validarSession() throws IOException {
        FacesContext x = FacesContext.getCurrentInstance();
        HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
        boolean resultado = administrarTemplate.obtenerConexion(ses.getId());
        if (resultado == false) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            //ec.invalidateSession();
            ec.redirect(ec.getRequestContextPath() + "/iniciored.xhtml");
        }
    }

    public String getNombreUsuario() {
        if (nombreUsuario == null && actualUsuario != null) {
            nombreUsuario = actualUsuario.getPersona().getNombreCompletoOrden2();
        }
        return nombreUsuario;
    }

    public StreamedContent getFotoUsuario() {
        informacionUsuario();
        return fotoUsuario;
    }

    public StreamedContent getLogoEmpresa() {
        obtenerEmpresaLogo();
        return logoEmpresa;
    }

    public void obtenerEmpresaLogo() {
        String rutaFoto = administrarTemplate.logoEmpresa();
        if (rutaFoto != null) {
            try {
                fis = new FileInputStream(new File(rutaFoto));
                logoEmpresa = new DefaultStreamedContent(fis, "image/png");
            } catch (FileNotFoundException e) {
                logoEmpresa = null;
                System.out.println("Logo de la empresa no encontrado para el template. \n" + e);
            }
        }
    }
    public String obtenerAcercaDe(){
        return acercaDe;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getLinkSoporte() {
        return linkSoporte;
    }

    public DetallesEmpresas getDetalleEmpresa() {
        System.out.println("ControlTemplate.getDetalleEmpresa");
        administrarTemplate.consultarDetalleEmpresaUsuario();
        return detalleEmpresa;
    }

}
