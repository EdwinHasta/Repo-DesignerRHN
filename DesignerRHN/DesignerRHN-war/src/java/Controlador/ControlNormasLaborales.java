/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.NormasLaborales;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarNormasLaboralesInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author John Pineda
 */
@ManagedBean
@SessionScoped
public class ControlNormasLaborales implements Serializable {

    @EJB
    AdministrarNormasLaboralesInterface administrarNormasLaborales;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<NormasLaborales> listNormasLaborales;
    private List<NormasLaborales> filtrarNormasLaborales;
    private List<NormasLaborales> crearNormaLaboral;
    private List<NormasLaborales> modificarNormaLaboral;
    private List<NormasLaborales> borrarNormaLaboral;
    private NormasLaborales nuevoNormaLaboral;
    private NormasLaborales duplicarNormaLaboral;
    private NormasLaborales editarNormaLaboral;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger borradoVC;

    public ControlNormasLaborales() {
        listNormasLaborales = null;
        crearNormaLaboral = new ArrayList<NormasLaborales>();
        modificarNormaLaboral = new ArrayList<NormasLaborales>();
        borrarNormaLaboral = new ArrayList<NormasLaborales>();
        permitirIndex = true;
        editarNormaLaboral = new NormasLaborales();
        nuevoNormaLaboral = new NormasLaborales();
        duplicarNormaLaboral = new NormasLaborales();
        guardado = true;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlNormasLaborales.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlNormasLaborales eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listNormasLaborales.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlNormasLaborales.asignarIndex \n");
            index = indice;
            RequestContext context = RequestContext.getCurrentInstance();
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlNormasLaborales.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNormaLaboral:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNormaLaboral:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosNormaLaboral");
            bandera = 0;
            filtrarNormasLaborales = null;
            tipoLista = 0;
        }

        borrarNormaLaboral.clear();
        crearNormaLaboral.clear();
        modificarNormaLaboral.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listNormasLaborales = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosNormaLaboral");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNormaLaboral:codigo");
            codigo.setFilterStyle("width: 370px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNormaLaboral:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosNormaLaboral");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNormaLaboral:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNormaLaboral:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosNormaLaboral");
            bandera = 0;
            filtrarNormasLaborales = null;
            tipoLista = 0;
        }
    }

    public void modificarNormaLaboral(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR NORMA LABORAL");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR NORMA LABORAL, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearNormaLaboral.contains(listNormasLaborales.get(indice))) {
                    if (listNormasLaborales.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listNormasLaborales.size(); j++) {
                            if (j != indice) {
                                if (listNormasLaborales.get(indice).getCodigo() == listNormasLaborales.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listNormasLaborales.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listNormasLaborales.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarNormaLaboral.isEmpty()) {
                            modificarNormaLaboral.add(listNormasLaborales.get(indice));
                        } else if (!modificarNormaLaboral.contains(listNormasLaborales.get(indice))) {
                            modificarNormaLaboral.add(listNormasLaborales.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }
            } else {

                if (!crearNormaLaboral.contains(filtrarNormasLaborales.get(indice))) {
                    if (filtrarNormasLaborales.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listNormasLaborales.size(); j++) {
                            if (j != indice) {
                                if (listNormasLaborales.get(indice).getCodigo() == listNormasLaborales.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarNormasLaborales.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarNormasLaborales.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarNormaLaboral.isEmpty()) {
                            modificarNormaLaboral.add(filtrarNormasLaborales.get(indice));
                        } else if (!modificarNormaLaboral.contains(filtrarNormasLaborales.get(indice))) {
                            modificarNormaLaboral.add(filtrarNormasLaborales.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }

            }
            context.update("form:datosNormaLaboral");
            context.update("form:ACEPTAR");
        }

    }

    public void borrarNormasLaborales() {

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("Entro a borrarNormasLaborales");
                if (!modificarNormaLaboral.isEmpty() && modificarNormaLaboral.contains(listNormasLaborales.get(index))) {
                    int modIndex = modificarNormaLaboral.indexOf(listNormasLaborales.get(index));
                    modificarNormaLaboral.remove(modIndex);
                    borrarNormaLaboral.add(listNormasLaborales.get(index));
                } else if (!crearNormaLaboral.isEmpty() && crearNormaLaboral.contains(listNormasLaborales.get(index))) {
                    int crearIndex = crearNormaLaboral.indexOf(listNormasLaborales.get(index));
                    crearNormaLaboral.remove(crearIndex);
                } else {
                    borrarNormaLaboral.add(listNormasLaborales.get(index));
                }
                listNormasLaborales.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarNormasLaborales ");
                if (!modificarNormaLaboral.isEmpty() && modificarNormaLaboral.contains(filtrarNormasLaborales.get(index))) {
                    int modIndex = modificarNormaLaboral.indexOf(filtrarNormasLaborales.get(index));
                    modificarNormaLaboral.remove(modIndex);
                    borrarNormaLaboral.add(filtrarNormasLaborales.get(index));
                } else if (!crearNormaLaboral.isEmpty() && crearNormaLaboral.contains(filtrarNormasLaborales.get(index))) {
                    int crearIndex = crearNormaLaboral.indexOf(filtrarNormasLaborales.get(index));
                    crearNormaLaboral.remove(crearIndex);
                } else {
                    borrarNormaLaboral.add(filtrarNormasLaborales.get(index));
                }
                int VCIndex = listNormasLaborales.indexOf(filtrarNormasLaborales.get(index));
                listNormasLaborales.remove(VCIndex);
                filtrarNormasLaborales.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosNormaLaboral");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        try {
            if (tipoLista == 0) {
                borradoVC = administrarNormasLaborales.verificarVigenciasNormasEmpleadoNormaLaboral(listNormasLaborales.get(index).getSecuencia());
            } else {
                borradoVC = administrarNormasLaborales.verificarVigenciasNormasEmpleadoNormaLaboral(filtrarNormasLaborales.get(index).getSecuencia());
            }
            if (borradoVC.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrarNormasLaborales();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                borradoVC = new BigInteger("-1");
            }

        } catch (Exception e) {
            System.err.println("ERROR ControlNormasLaborales verificarBorrado ERROR " + e);
        }
    }

    public void guardarNormasLaborales() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Normas Labolares");
            if (!borrarNormaLaboral.isEmpty()) {
        administrarNormasLaborales.borrarNormasLaborales(borrarNormaLaboral);      
                //mostrarBorrados
                registrosBorrados = borrarNormaLaboral.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarNormaLaboral.clear();
            }
            if (!crearNormaLaboral.isEmpty()) {
                 administrarNormasLaborales.crearNormasLaborales(crearNormaLaboral);         
                crearNormaLaboral.clear();
            }
            if (!modificarNormaLaboral.isEmpty()) {
                administrarNormasLaborales.modificarNormasLaborales(modificarNormaLaboral);
                modificarNormaLaboral.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listNormasLaborales = null;
            context.update("form:datosNormaLaboral");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarNormaLaboral = listNormasLaborales.get(index);
            }
            if (tipoLista == 1) {
                editarNormaLaboral = filtrarNormasLaborales.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editCodigo");
                context.execute("editCodigo.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editDescripcion");
                context.execute("editDescripcion.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoNormaLaboral() {
        System.out.println("Agregar Norma Laboral");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoNormaLaboral.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoNormaLaboral.getCodigo());

            for (int x = 0; x < listNormasLaborales.size(); x++) {
                if (listNormasLaborales.get(x).getCodigo() == nuevoNormaLaboral.getCodigo()) {
                    duplicados++;
                }
            }
            System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

            if (duplicados > 0) {
                mensajeValidacion = " *Que NO Hayan Codigos Repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
            }
        }
        if (nuevoNormaLaboral.getNombre() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener Una  Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 2) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNormaLaboral:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNormaLaboral:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosNormaLaboral");
                bandera = 0;
                filtrarNormasLaborales = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoNormaLaboral.setSecuencia(l);

            crearNormaLaboral.add(nuevoNormaLaboral);

            listNormasLaborales.add(nuevoNormaLaboral);
            nuevoNormaLaboral = new NormasLaborales();

            context.update("form:datosNormaLaboral");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroNormaLaboral.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoNormaLaboral() {
        System.out.println("limpiarNuevoNormaLaboral");
        nuevoNormaLaboral = new NormasLaborales();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicarNormaLaborales() {
        System.out.println("duplicarNormaLaboral");
        if (index >= 0) {
            duplicarNormaLaboral = new NormasLaborales();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarNormaLaboral.setSecuencia(l);
                duplicarNormaLaboral.setCodigo(listNormasLaborales.get(index).getCodigo());
                duplicarNormaLaboral.setNombre(listNormasLaborales.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarNormaLaboral.setSecuencia(l);
                duplicarNormaLaboral.setCodigo(filtrarNormasLaborales.get(index).getCodigo());
                duplicarNormaLaboral.setNombre(filtrarNormasLaborales.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarNormaLaboral");
            context.execute("duplicarRegistroNormasLaborales.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR CONTROLNORMASLABORALES");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarNormaLaboral.getCodigo());
        System.err.println("ConfirmarDuplicar nombre " + duplicarNormaLaboral.getNombre());

        if (duplicarNormaLaboral.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listNormasLaborales.size(); x++) {
                if (listNormasLaborales.get(x).getCodigo() == duplicarNormaLaboral.getCodigo()) {
                    duplicados++;
                }
            }
            if (duplicados > 0) {
                mensajeValidacion = " *Que NO Existan Codigo Repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
                duplicados = 0;
            }
        }
        if (duplicarNormaLaboral.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarNormaLaboral.getSecuencia() + "  " + duplicarNormaLaboral.getCodigo());
            if (crearNormaLaboral.contains(duplicarNormaLaboral)) {
                System.out.println("Ya lo contengo.");
            }
            listNormasLaborales.add(duplicarNormaLaboral);
            crearNormaLaboral.add(duplicarNormaLaboral);
            context.update("form:datosNormaLaboral");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNormaLaboral:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNormaLaboral:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosNormaLaboral");
                bandera = 0;
                filtrarNormasLaborales = null;
                tipoLista = 0;
            }
            duplicarNormaLaboral = new NormasLaborales();
            RequestContext.getCurrentInstance().execute("duplicarRegistroNormasLaborales.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarNormasLaborales() {
        duplicarNormaLaboral = new NormasLaborales();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNormaLaboralExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "NormasLaborales", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNormaLaboralExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "NormasLaborales", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listNormasLaborales.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "NORMASLABORALES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
                System.out.println("resultado: " + resultado);
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    context.execute("confirmarRastro.show()");
                } else if (resultado == 3) {
                    context.execute("errorRegistroRastro.show()");
                } else if (resultado == 4) {
                    context.execute("errorTablaConRastro.show()");
                } else if (resultado == 5) {
                    context.execute("errorTablaSinRastro.show()");
                }
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("NORMASLABORALES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //-------------------------------------------------------------------------- 
    public List<NormasLaborales> getListNormasLaborales() {
        if (listNormasLaborales == null) {
            listNormasLaborales = administrarNormasLaborales.mostrarNormasLaborales();
        }
        return listNormasLaborales;
    }

    public void setListNormasLaborales(List<NormasLaborales> listNormasLaborales) {
        this.listNormasLaborales = listNormasLaborales;
    }

    public List<NormasLaborales> getFiltrarNormasLaborales() {
        return filtrarNormasLaborales;
    }

    public void setFiltrarNormasLaborales(List<NormasLaborales> filtrarNormasLaborales) {
        this.filtrarNormasLaborales = filtrarNormasLaborales;
    }

    public int getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(int registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public NormasLaborales getNuevoNormaLaboral() {
        return nuevoNormaLaboral;
    }

    public void setNuevoNormaLaboral(NormasLaborales nuevoNormaLaboral) {
        this.nuevoNormaLaboral = nuevoNormaLaboral;
    }

    public NormasLaborales getDuplicarNormaLaboral() {
        return duplicarNormaLaboral;
    }

    public void setDuplicarNormaLaboral(NormasLaborales duplicarNormaLaboral) {
        this.duplicarNormaLaboral = duplicarNormaLaboral;
    }

    public NormasLaborales getEditarNormaLaboral() {
        return editarNormaLaboral;
    }

    public void setEditarNormaLaboral(NormasLaborales editarNormaLaboral) {
        this.editarNormaLaboral = editarNormaLaboral;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}
