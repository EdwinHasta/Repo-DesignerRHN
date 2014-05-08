/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.ActualUsuario;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTemplateInterface;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class ControlTemplate implements Serializable {

    @EJB
    AdministrarTemplateInterface administrarTemplate;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private ActualUsuario actualUsuario;
    private String nombreUsuario, fotoUsuario;

    public ControlTemplate() {
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTemplate.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
            /*if (resultado == false) {
             try {
             System.out.println("Paso 0 ");
             ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
             System.out.println("Paso 1 ");
             ec.invalidateSession();
             System.out.println("Paso 2 ");
             ec.redirect(ec.getRequestContextPath() + "/iniciored.xhtml");
             System.out.println("Paso 3 ");
             } catch (IOException e) {
             System.out.println("Error: (AdministrarTemplate.obtenerConexion): " + e);
             }
             }*/
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlTemplate: " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void informacionUsuario() {
        /*try {
            validarSession();
        } catch (IOException e) {
            System.out.println("mierdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!");
        }*/
        actualUsuario = administrarTemplate.consultarActualUsuario();
        if (actualUsuario != null) {
            nombreUsuario = actualUsuario.getPersona().getNombreCompleto();
            fotoUsuario = "Imagenes\\Fotos_Usuarios\\" + actualUsuario.getAlias() + ".jpg";
            System.out.println("fotoUsuario : " + fotoUsuario);
        }
    }

    public void cerrarSession() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath() + "/iniciored.xhtml");
    }

    public void validarSession() throws IOException {
        System.out.println("Hola bebo");
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
        if (nombreUsuario == null) {
            informacionUsuario();
        }
        return nombreUsuario;
    }

    public String getFotoUsuario() {
        if (fotoUsuario == null) {
            informacionUsuario();
        }
        return fotoUsuario;
    }

}
