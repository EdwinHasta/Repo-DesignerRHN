/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Niveles;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarNivelesInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlNiveles implements Serializable {

    @EJB
    AdministrarNivelesInterface administrarNiveles;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Niveles> listNiveles;
    private List<Niveles> filtrarNiveles;
    private List<Niveles> crearNiveles;
    private List<Niveles> modificarNiveles;
    private List<Niveles> borrarNiveles;
    private Niveles nuevoNivel;
    private Niveles duplicarNivel;
    private Niveles editarNivel;
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

    public ControlNiveles() {
        listNiveles = null;
        crearNiveles = new ArrayList<Niveles>();
        modificarNiveles = new ArrayList<Niveles>();
        borrarNiveles = new ArrayList<Niveles>();
        permitirIndex = true;
        editarNivel = new Niveles();
        nuevoNivel = new Niveles();
        duplicarNivel = new Niveles();
        guardado = true;
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarNiveles.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlNiveles.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlNiveles eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listNiveles.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlNiveles.asignarIndex \n");
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlNiveles.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNivel:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNivel:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosNivel");
            bandera = 0;
            filtrarNiveles = null;
            tipoLista = 0;
        }

        borrarNiveles.clear();
        crearNiveles.clear();
        modificarNiveles.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listNiveles = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosNivel");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNivel:codigo");
            codigo.setFilterStyle("width: 370px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNivel:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosNivel");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNivel:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNivel:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosNivel");
            bandera = 0;
            filtrarNiveles = null;
            tipoLista = 0;
        }
    }

    public void modificarNivel(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR TIPO EMPRESA");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EMPRESAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearNiveles.contains(listNiveles.get(indice))) {
                    if (listNiveles.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listNiveles.size(); j++) {
                            if (j != indice) {
                                if (listNiveles.get(indice).getCodigo() == listNiveles.get(j).getCodigo()) {
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
                    if (listNiveles.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listNiveles.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarNiveles.isEmpty()) {
                            modificarNiveles.add(listNiveles.get(indice));
                        } else if (!modificarNiveles.contains(listNiveles.get(indice))) {
                            modificarNiveles.add(listNiveles.get(indice));
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

                if (!crearNiveles.contains(filtrarNiveles.get(indice))) {
                    if (filtrarNiveles.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < filtrarNiveles.size(); j++) {
                            if (j != indice) {
                                if (filtrarNiveles.get(indice).getCodigo() == listNiveles.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listNiveles.size(); j++) {
                            if (j != indice) {
                                if (filtrarNiveles.get(indice).getCodigo() == listNiveles.get(j).getCodigo()) {
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

                    if (filtrarNiveles.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarNiveles.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarNiveles.isEmpty()) {
                            modificarNiveles.add(filtrarNiveles.get(indice));
                        } else if (!modificarNiveles.contains(filtrarNiveles.get(indice))) {
                            modificarNiveles.add(filtrarNiveles.get(indice));
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
            context.update("form:datosNivel");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoNiveles() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoNiveles");
                if (!modificarNiveles.isEmpty() && modificarNiveles.contains(listNiveles.get(index))) {
                    int modIndex = modificarNiveles.indexOf(listNiveles.get(index));
                    modificarNiveles.remove(modIndex);
                    borrarNiveles.add(listNiveles.get(index));
                } else if (!crearNiveles.isEmpty() && crearNiveles.contains(listNiveles.get(index))) {
                    int crearIndex = crearNiveles.indexOf(listNiveles.get(index));
                    crearNiveles.remove(crearIndex);
                } else {
                    borrarNiveles.add(listNiveles.get(index));
                }
                listNiveles.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoNiveles ");
                if (!modificarNiveles.isEmpty() && modificarNiveles.contains(filtrarNiveles.get(index))) {
                    int modIndex = modificarNiveles.indexOf(filtrarNiveles.get(index));
                    modificarNiveles.remove(modIndex);
                    borrarNiveles.add(filtrarNiveles.get(index));
                } else if (!crearNiveles.isEmpty() && crearNiveles.contains(filtrarNiveles.get(index))) {
                    int crearIndex = crearNiveles.indexOf(filtrarNiveles.get(index));
                    crearNiveles.remove(crearIndex);
                } else {
                    borrarNiveles.add(filtrarNiveles.get(index));
                }
                int VCIndex = listNiveles.indexOf(filtrarNiveles.get(index));
                listNiveles.remove(VCIndex);
                filtrarNiveles.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosNivel");
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
        BigInteger contaEvalConvocatoriasNivel;
        BigInteger contarPlantasNivel;
        BigInteger contarPlantasPersonalesNivel;

        try {
            System.err.println("Control Secuencia de ControlNiveles ");
            if (tipoLista == 0) {
                contaEvalConvocatoriasNivel = administrarNiveles.contarEvalConvocatoriasNivel(listNiveles.get(index).getSecuencia());
                contarPlantasNivel = administrarNiveles.contarPlantasNivel(listNiveles.get(index).getSecuencia());
                contarPlantasPersonalesNivel = administrarNiveles.contarPlantasPersonalesNivel(listNiveles.get(index).getSecuencia());
            } else {
                contaEvalConvocatoriasNivel = administrarNiveles.contarEvalConvocatoriasNivel(filtrarNiveles.get(index).getSecuencia());
                contarPlantasNivel = administrarNiveles.contarPlantasNivel(filtrarNiveles.get(index).getSecuencia());
                contarPlantasPersonalesNivel = administrarNiveles.contarPlantasPersonalesNivel(filtrarNiveles.get(index).getSecuencia());
            }
            if (contaEvalConvocatoriasNivel.equals(new BigInteger("0")) && contarPlantasNivel.equals(new BigInteger("0")) && contarPlantasPersonalesNivel.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoNiveles();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contaEvalConvocatoriasNivel = new BigInteger("-1");
                contarPlantasNivel = new BigInteger("-1");
                contarPlantasPersonalesNivel = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlNiveles verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarNiveles.isEmpty() || !crearNiveles.isEmpty() || !modificarNiveles.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarNiveles() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarNiveles");
            if (!borrarNiveles.isEmpty()) {
                administrarNiveles.borrarNiveles(borrarNiveles);

                //mostrarBorrados
                registrosBorrados = borrarNiveles.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarNiveles.clear();
            }
            if (!crearNiveles.isEmpty()) {
                administrarNiveles.crearNiveles(crearNiveles);
                crearNiveles.clear();
            }
            if (!modificarNiveles.isEmpty()) {
                administrarNiveles.modificarNiveles(modificarNiveles);
                modificarNiveles.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listNiveles = null;
            context.update("form:datosNivel");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarNivel = listNiveles.get(index);
            }
            if (tipoLista == 1) {
                editarNivel = filtrarNiveles.get(index);
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

    public void agregarNuevoNiveles() {
        System.out.println("agregarNuevoNiveles");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoNivel.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoNivel.getCodigo());

            for (int x = 0; x < listNiveles.size(); x++) {
                if (listNiveles.get(x).getCodigo() == nuevoNivel.getCodigo()) {
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
        if (nuevoNivel.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener una Descripción \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNivel:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNivel:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosNivel");
                bandera = 0;
                filtrarNiveles = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoNivel.setSecuencia(l);

            crearNiveles.add(nuevoNivel);

            listNiveles.add(nuevoNivel);
            nuevoNivel = new Niveles();
            context.update("form:datosNivel");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroNiveles.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoNiveles() {
        System.out.println("limpiarNuevoNiveles");
        nuevoNivel = new Niveles();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoNiveles() {
        System.out.println("duplicandoNiveles");
        if (index >= 0) {
            duplicarNivel = new Niveles();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarNivel.setSecuencia(l);
                duplicarNivel.setCodigo(listNiveles.get(index).getCodigo());
                duplicarNivel.setDescripcion(listNiveles.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarNivel.setSecuencia(l);
                duplicarNivel.setCodigo(filtrarNiveles.get(index).getCodigo());
                duplicarNivel.setDescripcion(filtrarNiveles.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroNiveles.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR TIPOS EMPRESAS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarNivel.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarNivel.getDescripcion());

        if (duplicarNivel.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listNiveles.size(); x++) {
                if (listNiveles.get(x).getCodigo() == duplicarNivel.getCodigo()) {
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
        if (duplicarNivel.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarNivel.getSecuencia() + "  " + duplicarNivel.getCodigo());
            if (crearNiveles.contains(duplicarNivel)) {
                System.out.println("Ya lo contengo.");
            }
            listNiveles.add(duplicarNivel);
            crearNiveles.add(duplicarNivel);
            context.update("form:datosNivel");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNivel:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosNivel:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosNivel");
                bandera = 0;
                filtrarNiveles = null;
                tipoLista = 0;
            }
            duplicarNivel = new Niveles();
            RequestContext.getCurrentInstance().execute("duplicarRegistroNiveles.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarNiveles() {
        duplicarNivel = new Niveles();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNivelExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "NIVELES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNivelExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "NIVELES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listNiveles.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "NIVELES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("NIVELES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<Niveles> getListNiveles() {
        if (listNiveles == null) {
            listNiveles = administrarNiveles.consultarNiveles();
        }
        return listNiveles;
    }

    public void setListNiveles(List<Niveles> listNiveles) {
        this.listNiveles = listNiveles;
    }

    public List<Niveles> getFiltrarNiveles() {
        return filtrarNiveles;
    }

    public void setFiltrarNiveles(List<Niveles> filtrarNiveles) {
        this.filtrarNiveles = filtrarNiveles;
    }

    public Niveles getNuevoNivel() {
        return nuevoNivel;
    }

    public void setNuevoNivel(Niveles nuevoNivel) {
        this.nuevoNivel = nuevoNivel;
    }

    public Niveles getDuplicarNivel() {
        return duplicarNivel;
    }

    public void setDuplicarNivel(Niveles duplicarNivel) {
        this.duplicarNivel = duplicarNivel;
    }

    public Niveles getEditarNivel() {
        return editarNivel;
    }

    public void setEditarNivel(Niveles editarNivel) {
        this.editarNivel = editarNivel;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
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

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}
