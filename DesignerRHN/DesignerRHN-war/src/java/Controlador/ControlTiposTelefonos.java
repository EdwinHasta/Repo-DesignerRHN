/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Administrar.AdministrarTiposTelefonos;
import Entidades.TiposTelefonos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposTelefonosInterface;
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
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlTiposTelefonos implements Serializable{

    @EJB
    AdministrarTiposTelefonosInterface administrarTiposTelefonos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    
    //LISTAS
    private List<TiposTelefonos> listaTiposTelefonos;
    private List<TiposTelefonos> filtradoListaTiposTelefonos;
    //Crear TiposTelefonos
    public TiposTelefonos nuevoTipoTelefono;
    private List<TiposTelefonos> listaTiposTelefonosCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //Columnas Tabla TiposTelefonos
    private Column tiposTelefonosCodigos, tiposTelefonosNombres;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Otros
    private boolean aceptar;
    private int index;
    private int tipoActualizacion;
    private boolean permitirIndex;
    private int tipoLista;
    private int cualCelda;
    //RASTRO
    private BigInteger secRegistro;
    //editar celda
    private TiposTelefonos editarTipoTelefono;
    private boolean cambioEditor, aceptarEditar;
    //Modificar tipos Telefonos
    private List<TiposTelefonos> listaTiposTelefonosModificar;
    private boolean guardado, guardarOk;
    //Borrar Tipos Telefonos
    private List<TiposTelefonos> listaTiposTelefonosBorrar;
     //DUPLICAR
    private TiposTelefonos duplicarTipoTelefono;
    
    
    
    
    public ControlTiposTelefonos() {
       listaTiposTelefonosCrear = new ArrayList<TiposTelefonos>();
       listaTiposTelefonosBorrar = new ArrayList<TiposTelefonos>();
       listaTiposTelefonosModificar = new ArrayList<TiposTelefonos>();
       permitirIndex = true;
       aceptar = true;
       tipoLista = 0;
       secRegistro = null;
       //editar
        editarTipoTelefono = new TiposTelefonos();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        nuevoTipoTelefono = new TiposTelefonos();
    }
    
    
    
    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoTelefono = listaTiposTelefonos.get(index);
            }
            if (tipoLista == 1) {
                editarTipoTelefono = filtradoListaTiposTelefonos.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarCodigosTiposTelefonos");
                context.execute("editarCodigosTiposTelefonos.show()");
                System.out.println("1");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarNombresTiposTelefonos");
                context.execute("editarNombresTiposTelefonos.show()");
                System.out.println("2");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }
    
    
    
    //GUARDAR
    public void guardarCambiosTipoTelefono() {
        if (guardado == false) {
            System.out.println("Realizando Operaciones Ciudades");
            if (!listaTiposTelefonosBorrar.isEmpty()) {
                for (int i = 0; i < listaTiposTelefonosBorrar.size(); i++) {
                    System.out.println("Borrando...");
                     administrarTiposTelefonos.borrarTipoTelefono(listaTiposTelefonosBorrar.get(i));
                }
                System.out.println("Entra");
                listaTiposTelefonosBorrar.clear();
            }
            if (!listaTiposTelefonosCrear.isEmpty()) {
                for (int i = 0; i < listaTiposTelefonosCrear.size(); i++) {
                    System.out.println("Creando...");
                      administrarTiposTelefonos.crearTipoTelefono(listaTiposTelefonosCrear.get(i));
                    }
                }
                listaTiposTelefonosCrear.clear();
            }
            if (!listaTiposTelefonosModificar.isEmpty()) {
                administrarTiposTelefonos.modificarTipoTelefono(listaTiposTelefonosModificar);
                listaTiposTelefonosModificar.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listaTiposTelefonos = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTiposTelefonos");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            k = 0;
        


        index = -1;
        secRegistro = null;
    }

    public void salir() {
        if (bandera == 1) {
            System.out.println("Desactivar");
            tiposTelefonosCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposTelefonos:tiposTelefonosCodigos");
            tiposTelefonosCodigos.setFilterStyle("display: none; visibility: hidden;");
            tiposTelefonosNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposTelefonos:tiposTelefonosNombres");
            tiposTelefonosNombres.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposTelefonos");
            bandera = 0;
            filtradoListaTiposTelefonos = null;
            tipoLista = 0;
            RequestContext.getCurrentInstance().update("form:datosTiposTelefonos");
            tipoLista = 0;

        }

        listaTiposTelefonosBorrar.clear();
        listaTiposTelefonosCrear.clear();
        listaTiposTelefonosModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaTiposTelefonos  = null;
        guardado = true;
        permitirIndex = true;

    }
    
    //CREAR TIPO TELEFONO
    public void agregarNuevoTipoTelefono() {
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTipoTelefono.getNombre().equals(" ") || nuevoTipoTelefono.getNombre().equals("")) {
            mensajeValidacion = mensajeValidacion + " * Nombre de Tipo de Telefono \n";
            pasa++;
        }
        if (nuevoTipoTelefono.getCodigo() == null) {
            mensajeValidacion = mensajeValidacion + " * Codigo \n";
            pasa++;
        } 
        System.out.println("Valor Para: " + pasa);
        System.out.println("Valor Nombre: " + nuevoTipoTelefono.getNombre());
        System.out.println("Mensaje Validacion: " + mensajeValidacion);
        
        
        if (pasa == 0) {
            if (bandera == 1) {
                //CERRAR FILTRADO
            System.out.println("Desactivar");
            tiposTelefonosCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposTelefonos:tiposTelefonosCodigos");
            tiposTelefonosCodigos.setFilterStyle("display: none; visibility: hidden;");
            tiposTelefonosNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposTelefonos:tiposTelefonosNombres");
            tiposTelefonosNombres.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposTelefonos");
            bandera = 0;
            filtradoListaTiposTelefonos = null;
            tipoLista = 0;
            RequestContext.getCurrentInstance().update("form:datosTiposTelefonos");
            tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA CIUDADES.
            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoTelefono.setSecuencia(l);
            listaTiposTelefonosCrear.add(nuevoTipoTelefono);
            listaTiposTelefonos.add(nuevoTipoTelefono);
            nuevoTipoTelefono = new TiposTelefonos();
           
            context.update("form:datosTiposTelefonos");
            if (guardado == true) {
                guardado = false;

                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevoRegistroTipoTelefono.hide()");
            index = -1;
            secRegistro = null;
        } else {
            context.update("formularioDialogos:validacionNuevoTipoTelefono");
            context.execute("validacionNuevoTipoTelefono.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO TIPO TELEFONO

    public void limpiarNuevoTipoTelefono() {
        nuevoTipoTelefono = new TiposTelefonos();
        index = -1;
        secRegistro = null;
    }
    
    //FILTRADO
     public void activarCtrlF11() {
        System.out.println("TipoLista= " + tipoLista);
        if (bandera == 0) {
            System.out.println("Activar");
            System.out.println("TipoLista= " + tipoLista);
            tiposTelefonosCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposTelefonos:tiposTelefonosCodigos");
            tiposTelefonosCodigos.setFilterStyle("");
            tiposTelefonosNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposTelefonos:tiposTelefonosNombres");
            tiposTelefonosNombres.setFilterStyle("");
            RequestContext.getCurrentInstance().update("form:datosTiposTelefonos");
            bandera = 1;
            tipoLista = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            tiposTelefonosCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposTelefonos:tiposTelefonosCodigos");
            tiposTelefonosCodigos.setFilterStyle("display: none; visibility: hidden;");
            tiposTelefonosNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposTelefonos:tiposTelefonosNombres");
            tiposTelefonosNombres.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposTelefonos");
            bandera = 0;
            filtradoListaTiposTelefonos = null;
            tipoLista = 0;
        }
    }
     
      //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposTelefonosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TiposTelefonosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposTelefonosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TiposTelefonosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    
     //LIMPIAR NUEVO REGISTRO DE TIPO DE TELEFONO

    public void limpiarNuevoTiposTelefonos() {
        nuevoTipoTelefono = new TiposTelefonos();
        index = -1;
        secRegistro = null;
    }
    
    //BORRAR TIPO DE TELEFONO
    public void borrarTiposTelefonos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listaTiposTelefonosModificar.isEmpty() && listaTiposTelefonosModificar.contains(listaTiposTelefonos.get(index))) {
                    int modIndex = listaTiposTelefonosModificar.indexOf(listaTiposTelefonos.get(index));
                    listaTiposTelefonosModificar.remove(modIndex);
                    listaTiposTelefonosBorrar.add(listaTiposTelefonos.get(index));
                } else if (!listaTiposTelefonosCrear.isEmpty() && listaTiposTelefonosCrear.contains(listaTiposTelefonos.get(index))) {
                    int crearIndex = listaTiposTelefonosCrear.indexOf(listaTiposTelefonos.get(index));
                    listaTiposTelefonosCrear.remove(crearIndex);
                } else {
                    listaTiposTelefonosBorrar.add(listaTiposTelefonos.get(index));
                }
                listaTiposTelefonos.remove(index);
            }
            
            if (tipoLista == 1) {
                if (!listaTiposTelefonosModificar.isEmpty() && listaTiposTelefonosModificar.contains(filtradoListaTiposTelefonos.get(index))) {
                    int modIndex = listaTiposTelefonosModificar.indexOf(filtradoListaTiposTelefonos.get(index));
                    listaTiposTelefonosModificar.remove(modIndex);
                    listaTiposTelefonosBorrar.add(filtradoListaTiposTelefonos.get(index));
                } else if (!listaTiposTelefonosCrear.isEmpty() && listaTiposTelefonosCrear.contains(filtradoListaTiposTelefonos.get(index))) {
                    int crearIndex = listaTiposTelefonosCrear.indexOf(filtradoListaTiposTelefonos.get(index));
                    listaTiposTelefonosCrear.remove(crearIndex);
                } else {
                    listaTiposTelefonosBorrar.add(filtradoListaTiposTelefonos.get(index));
                }
                int CIndex = listaTiposTelefonos.indexOf(filtradoListaTiposTelefonos.get(index));
                listaTiposTelefonos.remove(CIndex);
                filtradoListaTiposTelefonos.remove(index);
                System.out.println("Realizado");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTiposTelefonos");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }
    
    
   //UBICACION CELDA
        public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secRegistro = listaTiposTelefonos.get(index).getSecuencia();
                
            } else {
                secRegistro = filtradoListaTiposTelefonos.get(index).getSecuencia();
                
                }
            }
         }
    
    
    //DUPLICAR TIPO TELEFONO
    public void duplicarTT() {
        if (index >= 0) {
            duplicarTipoTelefono = new TiposTelefonos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTipoTelefono.setSecuencia(l);
                duplicarTipoTelefono.setCodigo(listaTiposTelefonos.get(index).getCodigo());
                duplicarTipoTelefono.setNombre(listaTiposTelefonos.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarTipoTelefono.setSecuencia(l);
                duplicarTipoTelefono.setCodigo(filtradoListaTiposTelefonos.get(index).getCodigo());
                duplicarTipoTelefono.setNombre(filtradoListaTiposTelefonos.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTipoTelefono");
            context.execute("DuplicarRegistroTipoTelefono.show()");
            index = -1;
            secRegistro = null;
        }
    }

    //LIMPIAR DUPLICAR

    public void limpiarduplicarTipoTelefono() {
        duplicarTipoTelefono = new TiposTelefonos();
    }
    
    public void confirmarDuplicar() {

        listaTiposTelefonos.add(duplicarTipoTelefono);
        listaTiposTelefonosCrear.add(duplicarTipoTelefono);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTiposTelefonos");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            System.out.println("Desactivar");
            tiposTelefonosCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposTelefonos:tiposTelefonosCodigos");
            tiposTelefonosCodigos.setFilterStyle("display: none; visibility: hidden;");
            tiposTelefonosNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposTelefonos:tiposTelefonosNombres");
            tiposTelefonosNombres.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposTelefonos");
            bandera = 0;
            filtradoListaTiposTelefonos = null;
            tipoLista = 0;
            RequestContext.getCurrentInstance().update("form:datosTiposTelefonos");
            tipoLista = 0;
        }
        duplicarTipoTelefono = new TiposTelefonos();
    }
    
        public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            tiposTelefonosCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposTelefonos:tiposTelefonosCodigos");
            tiposTelefonosCodigos.setFilterStyle("display: none; visibility: hidden;");
            tiposTelefonosNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposTelefonos:tiposTelefonosNombres");
            tiposTelefonosNombres.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposTelefonos");
            bandera = 0;
            filtradoListaTiposTelefonos = null;
            tipoLista = 0;
            RequestContext.getCurrentInstance().update("form:datosTiposTelefonos");
            tipoLista = 0;

        }

        listaTiposTelefonosBorrar.clear();
        listaTiposTelefonosCrear.clear();
        listaTiposTelefonosModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaTiposTelefonos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTiposTelefonos");
    }
        
    //AUTOCOMPLETAR
    public void modificarTiposTelefonos(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaTiposTelefonosCrear.contains(listaTiposTelefonos.get(indice))) {

                    if (listaTiposTelefonosModificar.isEmpty()) {
                        listaTiposTelefonosModificar.add(listaTiposTelefonos.get(indice));
                    } else if (!listaTiposTelefonosModificar.contains(listaTiposTelefonos.get(indice))) {
                        listaTiposTelefonosModificar.add(listaTiposTelefonos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;

            } else {
                if (!listaTiposTelefonosCrear.contains(filtradoListaTiposTelefonos.get(indice))) {

                    if (listaTiposTelefonosModificar.isEmpty()) {
                        listaTiposTelefonosModificar.add(filtradoListaTiposTelefonos.get(indice));
                    } else if (!listaTiposTelefonosModificar.contains(filtradoListaTiposTelefonos.get(indice))) {
                        listaTiposTelefonosModificar.add(filtradoListaTiposTelefonos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosTiposTelefonos");
        } 

    }
    
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listaTiposTelefonos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSTELEFONOS");
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSTELEFONOS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
    
    //GETTER AND SETTERS
    public List<TiposTelefonos> getListaTiposTelefonos() {
        if (listaTiposTelefonos == null) {
            listaTiposTelefonos = administrarTiposTelefonos.tiposTelefonos();
            return listaTiposTelefonos;
        } else {
            return listaTiposTelefonos;
        } 
    }

    public void setListaTiposTelefonos(List<TiposTelefonos> listaTiposTelefonos) {
        this.listaTiposTelefonos = listaTiposTelefonos;
    }

    public List<TiposTelefonos> getFiltradoListaTiposTelefonos() {
        return filtradoListaTiposTelefonos;
    }

    public void setFiltradoListaTiposTelefonos(List<TiposTelefonos> filtradoListaTiposTelefonos) {
        this.filtradoListaTiposTelefonos = filtradoListaTiposTelefonos;
    }
    
    public TiposTelefonos getNuevoTipoTelefono() {
        return nuevoTipoTelefono;
    }

    public void setNuevoTipoTelefono(TiposTelefonos nuevoTipoTelefono) {
        this.nuevoTipoTelefono = nuevoTipoTelefono;
    }

    public TiposTelefonos getEditarTipoTelefono() {
        return editarTipoTelefono;
    }

    public void setEditarTipoTelefono(TiposTelefonos editarTipoTelefono) {
        this.editarTipoTelefono = editarTipoTelefono;
    }

    public TiposTelefonos getDuplicarTipoTelefono() {
        return duplicarTipoTelefono;
    }

    public void setDuplicarTipoTelefono(TiposTelefonos duplicarTipoTelefono) {
        this.duplicarTipoTelefono = duplicarTipoTelefono;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }
    
    

 

       
    
}
