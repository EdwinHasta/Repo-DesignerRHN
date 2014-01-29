/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empresas;
import InterfaceAdministrar.AdministrarPapelesInterface;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlAcercaDe implements Serializable {

    @EJB
    AdministrarPapelesInterface administrarPapeles;

    private boolean aceptar;

//EMPRESA
    private List<Empresas> listaEmpresas;
    private List<Empresas> filtradoListaEmpresas;

    private Empresas empresaSeleccionada;
    private String correo;
    private String version;
    private String grh;
    private String licensiaDeUso;
    private String textoCopyRight;
    private String correo1;
    private String correo2;
    private String derechos;

    /**
     * Creates a new instance of ControlAcercaDe
     */
    public ControlAcercaDe() {
        listaEmpresas = null;
        empresaSeleccionada = null;
        aceptar = true;
        filtradoListaEmpresas = null;
        correo = "www.nomina.com.co";
        version = "Version 2014";
        grh = "Gerencia Integral de Recursos Humanos       y Administración de Nómina";
        licensiaDeUso = "Acme Corp";
        textoCopyRight = "ADVERTENCIA:\n"
                + "Este programa está protegido por leyes y tratados nacionales e internacionales. La reproducción o distribución no autorizada de este programa o de parte del mismo dará lugar a graves penalizaciones tanto civiles como penales y será objeto de cuantas acciones judiciales correspondan en derecho.\n"
                + "-----------------------------\n"
                + "MÓDULOS\n"
                + "Designer Personal \n"
                + "Designer Nómina\n"
                + "Designer Integración\n"
                + "Designer Gerencial\n"
                + "Designer Administración\n"
                + "---------------------------\n"
                + "ESTE PRODUCTO SE HA CREADO USANDO:\n"
                + "Glassfish [32 bits] Versión 3.1.2 (Community version)\n"
                + "JasperReports [32 bits] Versión 9.0.2.12.2 (Community version)\n"
                + "NetBeans [32 bits] Versión 7.4\n"
                + "JSF Versión 2\n"
                + "JPA 2 implementación EclipseLink Versión 2.5.0 \n"
                + "Java JEE 6";
        correo1 = "gerencia@nomina.com.co";
        correo2 = "www.nomina.com.co/wiki";
        derechos = "1998 - 2014 Todos los Derechos Reservados";
    }

    public void lovEmpresas() {
        RequestContext.getCurrentInstance().execute("EmpresasDialogo.show()");
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void cambiarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("Cambiar empresa  GUARDADO = " + empresaSeleccionada.getNombre());

        context.update("form:nombreEmpresa");
        filtradoListaEmpresas = null;
        aceptar = true;
        context.execute("EmpresasDialogo.hide()");
        context.reset("formularioDialogos:lovEmpresas:globalFilter");
        context.update("formularioDialogos:lovEmpresas");
        context.update("form:PanelTotal");
    }

    public void cancelarCambioEmpresa() {
        filtradoListaEmpresas = null;
    }

    public List<Empresas> getListaEmpresas() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            if (listaEmpresas == null) {
                listaEmpresas = administrarPapeles.consultarEmpresas();
                if (!listaEmpresas.isEmpty()) {
                    empresaSeleccionada = listaEmpresas.get(0);
                }
                context.update("form:PanelTotal");
            }
            return listaEmpresas;
        } catch (Exception e) {
            System.out.println("ERROR LISTA EMPRESAS " + e);
            return null;
        }
    }

    public void setListaEmpresas(List<Empresas> listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<Empresas> getFiltradoListaEmpresas() {
        return filtradoListaEmpresas;
    }

    public void setFiltradoListaEmpresas(List<Empresas> filtradoListaEmpresas) {
        this.filtradoListaEmpresas = filtradoListaEmpresas;
    }

    public Empresas getEmpresaSeleccionada() {
        if (empresaSeleccionada == null) {
            getListaEmpresas();
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("EMPRESA SELECCIONADA  : " + empresaSeleccionada.getNombre());
            context.update("form:PanelTotal");
        }
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresas empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGrh() {
        return grh;
    }

    public void setGrh(String grh) {
        this.grh = grh;
    }

    public String getLicensiaDeUso() {
        return licensiaDeUso;
    }

    public void setLicensiaDeUso(String licensiaDeUso) {
        this.licensiaDeUso = licensiaDeUso;
    }

    public String getTextoCopyRight() {
        return textoCopyRight;
    }

    public void setTextoCopyRight(String textoCopyRight) {
        this.textoCopyRight = textoCopyRight;
    }

    public String getCorreo1() {
        return correo1;
    }

    public void setCorreo1(String correo1) {
        this.correo1 = correo1;
    }

    public String getCorreo2() {
        return correo2;
    }

    public void setCorreo2(String correo2) {
        this.correo2 = correo2;
    }

    public String getDerechos() {
        return derechos;
    }

    public void setDerechos(String derechos) {
        this.derechos = derechos;
    }

}
