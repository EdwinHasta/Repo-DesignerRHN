/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Ciudades;
import Entidades.Departamentos;
import InterfaceAdministrar.AdministrarCiudadesInterface;
import InterfaceAdministrar.AdministrarDepartamentosInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.column.Column;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlCiudades implements Serializable {

    @EJB
    AdministrarCiudadesInterface administrarCiudades;
    @EJB
    AdministrarDepartamentosInterface administrarDepartamentos;
    //Listas
    private List<Departamentos> listaDepartamentos;
    private List<Departamentos> filtradoListaDepatartamentos;
    private Departamentos seleccionDepartamento;
    private List<Ciudades> listaCiudades;
    private List<Ciudades> filtradoListaCiudades;
    //Otros
    private boolean aceptar;
    private int index;
    private int tipoActualizacion;
    private boolean permitirIndex;
    private int tipoLista;
    private int cualCelda;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column ciudadesCodigos, ciudadesNombres, nombresDepartamentos, ciudadesCodigosAlternativos;
    //RASTRO
    private BigInteger secRegistro;
    //Modificar Ciudades
    private List<Ciudades> listaCiudadesModificar;
    private boolean guardado, guardarOk;
    //Crear Ciudades
    public Ciudades nuevaCiudad;
    private List<Ciudades> listaCiudadesCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //borrar VC
    private List<Ciudades> listaCiudadesBorrar;
    //AUTOCOMPLETAR
    private String Departamento;
    //editar celda
    private Ciudades editarCiudad;
    private boolean cambioEditor, aceptarEditar;

    public ControlCiudades() {
        permitirIndex = true;
        aceptar = true;
        tipoLista = 0;
        listaCiudadesBorrar = new ArrayList<Ciudades>();
        listaCiudadesCrear = new ArrayList<Ciudades>();
        listaCiudadesModificar = new ArrayList<Ciudades>();
        //INICIALIZAR LOVS
        listaDepartamentos = new ArrayList<Departamentos>();
        //editar
        editarCiudad = new Ciudades();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        nuevaCiudad = new Ciudades();
        nuevaCiudad.setDepartamento(new Departamentos());
        nuevaCiudad.getDepartamento().setNombre(" ");

    }

    public void asignarIndex(Integer indice) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = 0;
        context.update("formularioDialogos:departamentosDialogo");
        context.execute("departamentosDialogo.show()");
    }

    public void llamarLovDepartamento(int tipoN) {
        if (tipoN == 1) {
            tipoActualizacion = 1;
        } else if (tipoN == 2) {
            tipoActualizacion = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:departamentosDialogo");
        context.execute("departamentosDialogo.show()");
    }

    //Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        System.out.println("Valor" + permitirIndex);
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            System.out.println("Valor: " + celda);
            secRegistro = listaCiudades.get(index).getSecuencia();

            if (cualCelda == 3) {
                Departamento = listaCiudades.get(index).getDepartamento().getNombre();
            }
        }
    }

    //AUTOCOMPLETAR
    public void modificarCiudades(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaCiudadesCrear.contains(listaCiudades.get(indice))) {

                    if (listaCiudadesModificar.isEmpty()) {
                        listaCiudadesModificar.add(listaCiudades.get(indice));
                    } else if (!listaCiudadesModificar.contains(listaCiudades.get(indice))) {
                        listaCiudadesModificar.add(listaCiudades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;

            } else {
                if (!listaCiudadesCrear.contains(filtradoListaCiudades.get(indice))) {

                    if (listaCiudadesModificar.isEmpty()) {
                        listaCiudadesModificar.add(filtradoListaCiudades.get(indice));
                    } else if (!listaCiudadesModificar.contains(filtradoListaCiudades.get(indice))) {
                        listaCiudadesModificar.add(filtradoListaCiudades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosCiudades");
        } else if (confirmarCambio.equalsIgnoreCase("DEPARTAMENTOS")) {
            if (tipoLista == 0) {
                listaCiudades.get(indice).getDepartamento().setNombre(Departamento);
            } else {
                filtradoListaCiudades.get(indice).getDepartamento().setNombre(Departamento);
            }

            for (int i = 0; i < listaDepartamentos.size(); i++) {
                if (listaDepartamentos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaCiudades.get(indice).setDepartamento(listaDepartamentos.get(indiceUnicoElemento));
                } else {
                    filtradoListaCiudades.get(indice).setDepartamento(listaDepartamentos.get(indiceUnicoElemento));
                }
                listaDepartamentos.clear();
                getListaDepartamentos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:departamentosDialogo");
                context.execute("departamentosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaCiudadesCrear.contains(listaCiudades.get(indice))) {
                    if (listaCiudadesModificar.isEmpty()) {
                        listaCiudadesModificar.add(listaCiudades.get(indice));
                    } else if (!listaCiudadesModificar.contains(listaCiudades.get(indice))) {
                        listaCiudadesModificar.add(listaCiudades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaCiudadesCrear.contains(filtradoListaCiudades.get(indice))) {

                    if (listaCiudadesModificar.isEmpty()) {
                        listaCiudadesModificar.add(filtradoListaCiudades.get(indice));
                    } else if (!listaCiudadesModificar.contains(filtradoListaCiudades.get(indice))) {
                        listaCiudadesModificar.add(filtradoListaCiudades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosCiudades");
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarCiudad = listaCiudades.get(index);
            }
            if (tipoLista == 1) {
                editarCiudad = filtradoListaCiudades.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarCodigos");
                context.execute("editarCodigos.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarNombresCiudades");
                context.execute("editarNombresCiudades.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarDepartamentos");
                context.execute("editarDepartamentos.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarCodigosAlternativos");
                context.execute("editarCodigosAlternativos.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

//LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 2) {
                context.update("form:departamentosDialogo");
                context.execute("departamentosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            System.out.println("Activar");
            ciudadesCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCiudades:ciudadesCodigos");
            ciudadesCodigos.setFilterStyle("width: 60px");
            ciudadesNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCiudades:ciudadesNombres");
            ciudadesNombres.setFilterStyle("");
            nombresDepartamentos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCiudades:nombresDepartamentos");
            nombresDepartamentos.setFilterStyle("");
            ciudadesCodigosAlternativos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCiudades:ciudadesCodigosAlternativos");
            ciudadesCodigosAlternativos.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosCiudades");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            ciudadesCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCiudades:ciudadesCodigos");
            ciudadesCodigos.setFilterStyle("display: none; visibility: hidden;");
            ciudadesNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCiudades:ciudadesNombres");
            ciudadesNombres.setFilterStyle("display: none; visibility: hidden;");
            nombresDepartamentos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCiudades:nombresDepartamentos");
            nombresDepartamentos.setFilterStyle("display: none; visibility: hidden;");
            ciudadesCodigosAlternativos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCiudades:ciudadesCodigosAlternativos");
            ciudadesCodigosAlternativos.setFilterStyle("display: none; visibility: hidden;");
            bandera = 0;
            filtradoListaCiudades = null;
            RequestContext.getCurrentInstance().update("form:datosCiudades");
            tipoLista = 0;
        }
    }

    //CREAR CIUDAD
    public void agregarNuevaCiudad() {
        boolean pasa = false;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaCiudad.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + " * Nombre de la Ciudad \n";
            pasa = false;
        } else {
            pasa = true;
        }
        if (nuevaCiudad.getDepartamento().getNombre()== null) {
            mensajeValidacion = mensajeValidacion + "   * Departamento \n";
            pasa = false;
        } else {
            pasa = true;
        }


        if (pasa == true) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                ciudadesCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCiudades:ciudadesCodigos");
                ciudadesCodigos.setFilterStyle("display: none; visibility: hidden;");
                ciudadesNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCiudades:ciudadesNombres");
                ciudadesNombres.setFilterStyle("display: none; visibility: hidden;");
                nombresDepartamentos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCiudades:nombresDepartamentos");
                nombresDepartamentos.setFilterStyle("display: none; visibility: hidden;");
                ciudadesCodigosAlternativos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCiudades:ciudadesCodigosAlternativos");
                ciudadesCodigosAlternativos.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosCiudades");
                bandera = 0;
                filtradoListaCiudades = null;
                tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA CIUDADES.
            k++;
            l = BigInteger.valueOf(k);
            nuevaCiudad.setSecuencia(l);
            listaCiudadesCrear.add(nuevaCiudad);
            listaCiudades.add(nuevaCiudad);
            nuevaCiudad = new Ciudades();
            nuevaCiudad.setNombre(Departamento);
            nuevaCiudad.setDepartamento(new Departamentos());
            nuevaCiudad.getDepartamento().setNombre(" ");
            context.update("form:datosCiudades");
            if (guardado == true) {
                guardado = false;
               
                //  RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevoRegistroCiudad.hide()");
            index = -1;
            secRegistro = null;
        } else {
            context.update("formularioDialogos:validacionNuevaCiudad");
            context.execute("validacionNuevaCiudad.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO CIUDAD

    public void limpiarNuevaCiudad() {
        nuevaCiudad = new Ciudades();
        nuevaCiudad.setDepartamento(new Departamentos());
        nuevaCiudad.getDepartamento().setNombre(" ");
        index = -1;
        secRegistro = null;
    }

    
    public void valoresBackupAutocompletar(int tipoNuevo) {

            if (tipoNuevo == 1) {
                Departamento = nuevaCiudad.getDepartamento().getNombre();
            } else if (tipoNuevo == 2) {
//                Departamento = duplicarVTC.getMotivocontrato().getNombre();
            }
     }
public void autocompletarNuevoyDuplicado(String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
              if (tipoNuevo == 1) {
                nuevaCiudad.getDepartamento().setNombre(Departamento);
            } else if (tipoNuevo == 2) {
             //   duplicarVTC.getMotivocontrato().setNombre(Motivo);
            }
            for (int i = 0; i < listaDepartamentos.size(); i++) {
                if (listaDepartamentos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaCiudad.setDepartamento(listaDepartamentos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoDepartamento");
                } else if (tipoNuevo == 2) {
                    //duplicarVTC.setMotivocontrato(listaMotivosContratos.get(indiceUnicoElemento));
                    //context.update("formularioDialogos:duplicarMotivoContrato");
                }
                listaDepartamentos.clear();
                getListaDepartamentos();
            } else {
                context.update("form:departamentosDialogo");
                context.execute("departamentosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoDepartamento");
                } else if (tipoNuevo == 2) {
                  //  context.update("formularioDialogos:duplicarMotivoContrato");
                }
            }
        }

    
    //METODOS L.O.V CIUDADES
    public void actualizarDepartamentos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaCiudades.get(index).setDepartamento(seleccionDepartamento);
                if (!listaCiudadesCrear.contains(listaCiudades.get(index))) {
                    if (listaCiudadesModificar.isEmpty()) {
                        listaCiudadesModificar.add(listaCiudades.get(index));
                    } else if (!listaCiudadesModificar.contains(listaCiudades.get(index))) {
                        listaCiudadesModificar.add(listaCiudades.get(index));
                    }
                }
            } else {
                filtradoListaCiudades.get(index).setDepartamento(seleccionDepartamento);
                if (!listaCiudadesCrear.contains(filtradoListaCiudades.get(index))) {
                    if (listaCiudadesModificar.isEmpty()) {
                        listaCiudadesModificar.add(filtradoListaCiudades.get(index));
                    } else if (!listaCiudadesModificar.contains(filtradoListaCiudades.get(index))) {
                        listaCiudadesModificar.add(filtradoListaCiudades.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosCiudades");
        } else if (tipoActualizacion == 1) {
            nuevaCiudad.setDepartamento(seleccionDepartamento);
             context.update("formularioDialogos:nuevoDepartamento");
        } else if (tipoActualizacion == 2) {
            /*duplicarVC.setMotivocambiocargo(motivoSeleccionado);
             context.update("formularioDialogos:duplicarVC");*/
        }
        filtradoListaDepatartamentos = null;
        seleccionDepartamento = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("departamentosDialogo.hide()");
        context.reset("formularioDialogos:LOVDepartamentos:globalFilter");
        context.update("formularioDialogos:LOVDepartamentos");
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void cancelarCambioDepartamentos() {
        filtradoListaDepatartamentos = null;
        seleccionDepartamento = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    //BORRAR CIUDADES
    public void borrarCiudades() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listaCiudadesModificar.isEmpty() && listaCiudadesModificar.contains(listaCiudades.get(index))) {
                    int modIndex = listaCiudadesModificar.indexOf(listaCiudades.get(index));
                    listaCiudadesModificar.remove(modIndex);
                    listaCiudadesBorrar.add(listaCiudades.get(index));
                } else if (!listaCiudadesCrear.isEmpty() && listaCiudadesCrear.contains(listaCiudades.get(index))) {
                    int crearIndex = listaCiudadesCrear.indexOf(listaCiudades.get(index));
                    listaCiudadesCrear.remove(crearIndex);
                } else {
                    listaCiudadesBorrar.add(listaCiudades.get(index));
                }
                listaCiudades.remove(index);
            }
            if (tipoLista == 1) {
                if (!listaCiudadesModificar.isEmpty() && listaCiudadesModificar.contains(filtradoListaCiudades.get(index))) {
                    int modIndex = listaCiudadesModificar.indexOf(filtradoListaCiudades.get(index));
                    listaCiudadesModificar.remove(modIndex);
                    listaCiudadesBorrar.add(filtradoListaCiudades.get(index));
                } else if (!listaCiudadesCrear.isEmpty() && listaCiudadesCrear.contains(filtradoListaCiudades.get(index))) {
                    int crearIndex = listaCiudadesCrear.indexOf(filtradoListaCiudades.get(index));
                    listaCiudadesCrear.remove(crearIndex);
                } else {
                    listaCiudadesBorrar.add(filtradoListaCiudades.get(index));
                }
                int CIndex = listaCiudades.indexOf(filtradoListaCiudades.get(index));
                listaCiudades.remove(CIndex);
                filtradoListaCiudades.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCiudades");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("DEP")) {
            if (tipoNuevo == 1) {
                Departamento = nuevaCiudad.getDepartamento().getNombre();
            } else if (tipoNuevo == 2) {
                //   Motivo = duplicarVTC.getMotivocontrato().getNombre();
            }
        }

    }

//GETTER AND SETTER
    public List<Ciudades> getListaCiudades() {
        if (listaCiudades == null) {
//            listAficiones = administrarCarpetaDesigner.buscarAficiones();
            listaCiudades = administrarCiudades.Ciudades();
            return listaCiudades;
        } else {
            return listaCiudades;
        }
    }

    public void setListaCiudades(List<Ciudades> listaCiudades) {
        this.listaCiudades = listaCiudades;
    }

    public List<Ciudades> getFiltradoListaCiudades() {
        return filtradoListaCiudades;
    }

    public void setFiltradoListaCiudades(List<Ciudades> filtradoListaCiudades) {
        this.filtradoListaCiudades = filtradoListaCiudades;
    }

    public List<Departamentos> getListaDepartamentos() {
        if (listaDepartamentos.isEmpty()) {
            listaDepartamentos = administrarDepartamentos.lovDepartamentos();
        }
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamentos> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public List<Departamentos> getFiltradoListaDepatartamentos() {
        return filtradoListaDepatartamentos;
    }

    public void setFiltradoListaDepatartamentos(List<Departamentos> filtradoListaDepatartamentos) {
        this.filtradoListaDepatartamentos = filtradoListaDepatartamentos;
    }

    public Departamentos getSeleccionDepartamento() {
        return seleccionDepartamento;
    }

    public void setSeleccionDepartamento(Departamentos seleccionDepartamento) {
        this.seleccionDepartamento = seleccionDepartamento;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public Ciudades getEditarCiudad() {
        return editarCiudad;
    }

    public void setEditarCiudad(Ciudades editarCiudad) {
        this.editarCiudad = editarCiudad;
    }

    public boolean isAceptarEditar() {
        return aceptarEditar;
    }

    public void setAceptarEditar(boolean aceptarEditar) {
        this.aceptarEditar = aceptarEditar;
    }

    public Ciudades getNuevaCiudad() {
        return nuevaCiudad;
    }

    public void setNuevaCiudad(Ciudades nuevaCiudad) {
        this.nuevaCiudad = nuevaCiudad;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }
}
