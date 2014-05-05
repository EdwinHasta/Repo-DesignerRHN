/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empresas;
import Entidades.Monedas;
import Entidades.Proyectos;
import Entidades.PryClientes;
import Entidades.PryPlataformas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarProyectosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
public class ControlProyecto implements Serializable {

    @EJB
    AdministrarProyectosInterface administrarProyectos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Proyectos
    private List<Proyectos> listProyectos;
    private List<Proyectos> filtrarListProyectos;
    private Proyectos proyectoSeleccionado;
    //Empresas
    private List<Empresas> listEmpresas;
    private Empresas empresaSeleccionada;
    private List<Empresas> filtrarListEmpresas;
    //PryClientes
    private List<PryClientes> listPryClientes;
    private PryClientes clienteSeleccionado;
    private List<PryClientes> filtrarListPryClientes;
    //PryPlataformas
    private List<PryPlataformas> listPryPlataformas;
    private PryPlataformas plataformaSeleccionada;
    private List<PryPlataformas> filtrarListPryPlataformas;
    //Monedas
    private List<Monedas> listMonedas;
    private Monedas monedaSeleccionada;
    private List<Monedas> filtrarListMonedas;
    //Tipo Actualizacion
    private int tipoActualizacion;
    //Activo/Desactivo VP Crtl + F11
    private int banderaP;
    //Columnas Tabla VP
    private Column pryEmpresa, pryCodigo, pryNombre, pryCliente, pryPlataforma, pryMonto, pryMoneda, pryPersonas, pryFechaInicial, pryFechaFinal, pryDescripcion;
    //Otros
    private boolean aceptar;
    //modificar
    private List<Proyectos> listProyectoModificar;
    private boolean guardado;
    //crear VP
    public Proyectos nuevaProyectos;
    private BigInteger l;
    private int k;
    //borrar VL
    private List<Proyectos> listProyectoBorrar;
    //editar celda
    private Proyectos editarProyecto;
    //duplicar
    //Autocompletar
    private boolean permitirIndexP;
    //Variables Autompletar
    private String empresas, clientes, plataformas, monedas;
    private int indexP;
    private int cualCeldaP, tipoListaP;
    private Proyectos duplicarProyecto;
    private List<Proyectos> listProyectoCrear;
    private boolean cambioProyecto;
    private int tipoLista;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private Date fechaInic, fechaFin;
    private String paginaAnterior;

    public ControlProyecto() {
        proyectoSeleccionado = new Proyectos();
        backUpSecRegistro = null;
        tipoLista = 0;
        //Otros
        aceptar = true;
        listProyectoBorrar = new ArrayList<Proyectos>();
        k = 0;
        listProyectoModificar = new ArrayList<Proyectos>();
        editarProyecto = new Proyectos();
        tipoListaP = 0;
        guardado = true;

        banderaP = 0;
        permitirIndexP = true;
        indexP = -1;
        secRegistro = null;
        cualCeldaP = -1;
        listPryPlataformas = null;
        listPryClientes = null;
        listEmpresas = null;
        nuevaProyectos = new Proyectos();
        nuevaProyectos.setTipomoneda(new Monedas());
        nuevaProyectos.setEmpresa(new Empresas());
        nuevaProyectos.setPryCliente(new PryClientes());
        nuevaProyectos.setPryPlataforma(new PryPlataformas());
        listProyectoCrear = new ArrayList<Proyectos>();
        cambioProyecto = false;
    }

    public boolean validarFechasRegistro(int i) {
        boolean retorno = false;
        if (i == 0) {
            if (tipoListaP == 0) {
                if (listProyectos.get(indexP).getFechainicial() != null && listProyectos.get(indexP).getFechafinal() != null) {
                    if (listProyectos.get(indexP).getFechainicial().before(listProyectos.get(indexP).getFechafinal())) {
                        retorno = true;
                    } else {
                        retorno = false;
                    }
                }
                if (listProyectos.get(indexP).getFechainicial() != null && listProyectos.get(indexP).getFechafinal() == null) {
                    retorno = true;
                }
                if (listProyectos.get(indexP).getFechainicial() == null && listProyectos.get(indexP).getFechafinal() != null) {
                    retorno = false;
                }
            }
            if (tipoListaP == 1) {
                if (filtrarListProyectos.get(indexP).getFechainicial() != null && filtrarListProyectos.get(indexP).getFechafinal() != null) {
                    if (filtrarListProyectos.get(indexP).getFechainicial().before(filtrarListProyectos.get(indexP).getFechafinal())) {
                        retorno = true;
                    } else {
                        retorno = false;
                    }
                }
                if (filtrarListProyectos.get(indexP).getFechainicial() != null && filtrarListProyectos.get(indexP).getFechafinal() == null) {
                    retorno = true;
                }
                if (filtrarListProyectos.get(indexP).getFechainicial() == null && filtrarListProyectos.get(indexP).getFechafinal() != null) {
                    retorno = false;
                }
            }

        }
        if (i == 1) {
            if (nuevaProyectos.getFechafinal() != null && nuevaProyectos.getFechainicial() != null) {
                if (nuevaProyectos.getFechainicial().before(nuevaProyectos.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (nuevaProyectos.getFechafinal() != null && nuevaProyectos.getFechainicial() == null) {
                retorno = true;
            }
            if (nuevaProyectos.getFechafinal() == null && nuevaProyectos.getFechainicial() != null) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarProyecto.getFechafinal() != null && duplicarProyecto.getFechainicial() != null) {
                if (duplicarProyecto.getFechainicial().before(duplicarProyecto.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (duplicarProyecto.getFechafinal() != null && duplicarProyecto.getFechainicial() == null) {
                retorno = true;
            }
            if (duplicarProyecto.getFechafinal() == null && duplicarProyecto.getFechainicial() != null) {
                retorno = false;
            }
        }        return retorno;
    }

    public void modificacionesFechas(int i, int c) {
        Proyectos auxiliar = new Proyectos();
        if (tipoListaP == 0) {
            auxiliar = listProyectos.get(i);
        }
        if (tipoListaP == 1) {
            auxiliar = filtrarListProyectos.get(i);
        }
        boolean retorno = false;
        if ((auxiliar.getFechainicial() == null) && (auxiliar.getFechafinal() == null)) {
            retorno = true;
        }
        else if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            indexP = i;
            retorno = validarFechasRegistro(0);
        }
        else if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() == null)) {
            retorno = true;
        }
        else if ((auxiliar.getFechainicial() == null) && (auxiliar.getFechafinal() != null)) {
            retorno = false;
            fechaFin = null;
            fechaInic = null;
        }
        if (retorno == true) {
            cambiarIndiceP(i, c);
            modificarProyecto(i);
        } else {
            if (tipoListaP == 0) {
                listProyectos.get(indexP).setFechafinal(fechaFin);
                listProyectos.get(indexP).setFechainicial(fechaInic);
            }
            if (tipoListaP == 1) {
                filtrarListProyectos.get(indexP).setFechafinal(fechaFin);
                filtrarListProyectos.get(indexP).setFechainicial(fechaInic);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosProyectos");
            context.execute("errorFechas.show()");
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Modifica los elementos de la tabla VigenciaProrrateo que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarProyecto(int indice) {
        if (tipoListaP == 0) {
            if (!listProyectoCrear.contains(listProyectos.get(indice))) {
                if (listProyectoModificar.isEmpty()) {
                    listProyectoModificar.add(listProyectos.get(indice));
                } else if (!listProyectoModificar.contains(listProyectos.get(indice))) {
                    listProyectoModificar.add(listProyectos.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            cambioProyecto = true;
            indexP = -1;
            secRegistro = null;
        } else {
            int ind = listProyectos.indexOf(filtrarListProyectos.get(indice));
            indexP = ind;
            if (!listProyectoCrear.contains(filtrarListProyectos.get(indice))) {
                if (listProyectoModificar.isEmpty()) {
                    listProyectoModificar.add(filtrarListProyectos.get(indice));
                } else if (!listProyectoModificar.contains(filtrarListProyectos.get(indice))) {
                    listProyectoModificar.add(filtrarListProyectos.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            cambioProyecto = true;
            indexP = -1;
            secRegistro = null;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosProyectos");
    }
    
    public void recibirPaginaEntrante(String pagina){
        paginaAnterior = pagina;
        
        }
    
    public String redirigir(){
        return paginaAnterior;
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla VigenciaProrrateo
     * de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarProyecto(int indice, String confirmarCambio, String valorConfirmar) {
        indexP = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("EMPRESAS")) {
            if (tipoListaP == 0) {
                listProyectos.get(indice).getEmpresa().setNombre(empresas);
            } else {
                filtrarListProyectos.get(indice).getEmpresa().setNombre(empresas);
            }
            for (int i = 0; i < listEmpresas.size(); i++) {
                if (listEmpresas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listProyectos.get(indice).setEmpresa(listEmpresas.get(indiceUnicoElemento));
                } else {
                    filtrarListProyectos.get(indice).setEmpresa(listEmpresas.get(indiceUnicoElemento));
                }
                listEmpresas = null;
                getListEmpresas();
                cambioProyecto = true;
            } else {
                permitirIndexP = false;
                context.update("form:EmpresasDialogo");
                context.execute("EmpresasDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CLIENTES")) {
            if (tipoListaP == 0) {
                listProyectos.get(indice).getPryCliente().setNombre(clientes);
            } else {
                filtrarListProyectos.get(indice).getPryCliente().setNombre(clientes);
            }
            for (int i = 0; i < listPryClientes.size(); i++) {
                if (listPryClientes.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listProyectos.get(indice).setPryCliente(listPryClientes.get(indiceUnicoElemento));
                } else {
                    filtrarListProyectos.get(indice).setPryCliente(listPryClientes.get(indiceUnicoElemento));
                }
                listPryClientes = null;
                getListPryClientes();
                cambioProyecto = true;
            } else {
                permitirIndexP = false;
                context.update("form:ClientesDialogo");
                context.execute("ClientesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("PLATAFORMAS")) {
            if (tipoListaP == 0) {
                listProyectos.get(indice).getPryPlataforma().setDescripcion(plataformas);
            } else {
                filtrarListProyectos.get(indice).getPryPlataforma().setDescripcion(plataformas);
            }
            for (int i = 0; i < listPryPlataformas.size(); i++) {
                if (listPryPlataformas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listProyectos.get(indice).setPryPlataforma(listPryPlataformas.get(indiceUnicoElemento));
                } else {
                    filtrarListProyectos.get(indice).setPryPlataforma(listPryPlataformas.get(indiceUnicoElemento));
                }
                cambioProyecto = true;
                listPryPlataformas = null;
                getListPryPlataformas();
            } else {
                permitirIndexP = false;
                context.update("form:PlataformasDialogo");
                context.execute("PlataformasDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("MONEDAS")) {
            if (tipoListaP == 0) {
                listProyectos.get(indice).getTipomoneda().setNombre(monedas);
            } else {
                filtrarListProyectos.get(indice).getTipomoneda().setNombre(monedas);
            }
            for (int i = 0; i < listMonedas.size(); i++) {
                if (listMonedas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listProyectos.get(indice).setTipomoneda(listMonedas.get(indiceUnicoElemento));
                } else {
                    filtrarListProyectos.get(indice).setTipomoneda(listMonedas.get(indiceUnicoElemento));
                }
                cambioProyecto = true;
                listMonedas = null;
                getListMonedas();
            } else {
                permitirIndexP = false;
                context.update("form:MonedasDialogo");
                context.execute("MonedasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaP == 0) {
                if (!listProyectoCrear.contains(listProyectos.get(indice))) {

                    if (listProyectoModificar.isEmpty()) {
                        listProyectoModificar.add(listProyectos.get(indice));
                    } else if (!listProyectoModificar.contains(listProyectos.get(indice))) {
                        listProyectoModificar.add(listProyectos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambioProyecto = true;
                indexP = -1;
                secRegistro = null;
            } else {
                if (!listProyectoCrear.contains(filtrarListProyectos.get(indice))) {

                    if (listProyectoModificar.isEmpty()) {
                        listProyectoModificar.add(filtrarListProyectos.get(indice));
                    } else if (!listProyectoModificar.contains(filtrarListProyectos.get(indice))) {
                        listProyectoModificar.add(filtrarListProyectos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambioProyecto = true;
                indexP = -1;
                secRegistro = null;
            }
            cambioProyecto = true;
        }
        context.update("form:datosProyectos");
    }

    public void valoresBackupAutocompletarProyecto(int tipoNuevo, String Campo) {

        if (Campo.equals("EMPRESAS")) {
            if (tipoNuevo == 1) {
                empresas = nuevaProyectos.getEmpresa().getNombre();
            } else if (tipoNuevo == 2) {
                empresas = duplicarProyecto.getEmpresa().getNombre();
            }
        } else if (Campo.equals("CLIENTES")) {
            if (tipoNuevo == 1) {
                clientes = nuevaProyectos.getPryCliente().getNombre();
            } else if (tipoNuevo == 2) {
                clientes = duplicarProyecto.getPryCliente().getNombre();
            }
        } else if (Campo.equals("PLATAFORMAS")) {
            if (tipoNuevo == 1) {
                plataformas = nuevaProyectos.getPryPlataforma().getDescripcion();
            } else if (tipoNuevo == 2) {
                plataformas = duplicarProyecto.getPryPlataforma().getDescripcion();
            }
        } else if (Campo.equals("MONEDAS")) {
            if (tipoNuevo == 1) {
                monedas = nuevaProyectos.getTipomoneda().getNombre();
            } else if (tipoNuevo == 2) {
                monedas = duplicarProyecto.getTipomoneda().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoProyecto(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("EMPRESAS")) {
            if (tipoNuevo == 1) {
                nuevaProyectos.getEmpresa().setNombre(empresas);
            } else if (tipoNuevo == 2) {
                duplicarProyecto.getEmpresa().setNombre(empresas);
            }
            for (int i = 0; i < listEmpresas.size(); i++) {
                if (listEmpresas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaProyectos.setEmpresa(listEmpresas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaEmpresaP");
                } else if (tipoNuevo == 2) {
                    duplicarProyecto.setEmpresa(listEmpresas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarEmpresaP");
                }
                listEmpresas = null;
                getListEmpresas();
            } else {
                context.update("form:EmpresasDialogo");
                context.execute("EmpresasDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaEmpresaP");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarEmpresaP");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("CLIENTES")) {
            if (tipoNuevo == 1) {
                nuevaProyectos.getPryCliente().setNombre(clientes);
            } else if (tipoNuevo == 2) {
                duplicarProyecto.getPryCliente().setNombre(clientes);
            }
            for (int i = 0; i < listPryClientes.size(); i++) {
                if (listPryClientes.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }

            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaProyectos.setPryCliente(listPryClientes.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaClienteP");
                } else if (tipoNuevo == 2) {
                    duplicarProyecto.setPryCliente(listPryClientes.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarClienteP");
                }
                listPryClientes = null;
                getListPryClientes();
            } else {
                context.update("form:ClientesDialogo");
                context.execute("ClientesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaClienteP");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarClienteP");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("PLATAFORMAS")) {
            if (tipoNuevo == 1) {
                nuevaProyectos.getPryPlataforma().setDescripcion(clientes);
            } else if (tipoNuevo == 2) {
                duplicarProyecto.getPryPlataforma().setDescripcion(clientes);
            }
            for (int i = 0; i < listPryPlataformas.size(); i++) {
                if (listPryPlataformas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaProyectos.setPryPlataforma(listPryPlataformas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaPlataformaP");
                    duplicarProyecto.setPryPlataforma(listPryPlataformas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarPlataformaP");
                }
                listPryPlataformas = null;
                getListPryPlataformas();
            } else {
                context.update("form:PlataformasDialogo");
                context.execute("PlataformasDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaPlataformaP");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarPlataformaP");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("MONEDAS")) {
            if (tipoNuevo == 1) {
                nuevaProyectos.getTipomoneda().setNombre(monedas);
            } else if (tipoNuevo == 2) {
                duplicarProyecto.getTipomoneda().setNombre(monedas);
            }
            for (int i = 0; i < listMonedas.size(); i++) {
                if (listMonedas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaProyectos.setTipomoneda(listMonedas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaMonedaP");
                } else if (tipoNuevo == 2) {
                    duplicarProyecto.setTipomoneda(listMonedas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarMonedaP");
                }
                listMonedas = null;
                getListMonedas();
            } else {
                context.update("form:PlataformasDialogo");
                context.execute("PlataformasDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaMonedaP");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarMonedaP");
                }
            }
        }
    }

    public void cambiarIndiceP(int indice, int celda) {
        if (permitirIndexP == true) {
            indexP = indice;
            cualCeldaP = celda;
            if (tipoListaP == 0) {
                fechaFin = listProyectos.get(indexP).getFechafinal();
                fechaInic = listProyectos.get(indexP).getFechainicial();
                secRegistro = listProyectos.get(indexP).getSecuencia();
                if (cualCeldaP == 0) {
                    empresas = listProyectos.get(indexP).getEmpresa().getNombre();
                }
                if (cualCeldaP == 3) {
                    clientes = listProyectos.get(indexP).getPryCliente().getNombre();
                }
                if (cualCeldaP == 4) {
                    plataformas = listProyectos.get(indexP).getPryPlataforma().getDescripcion();
                }
                if (cualCeldaP == 6) {
                    monedas = listProyectos.get(indexP).getTipomoneda().getNombre();
                }
            }
            if (tipoListaP == 1) {
                fechaFin = filtrarListProyectos.get(indexP).getFechafinal();
                fechaInic = filtrarListProyectos.get(indexP).getFechainicial();
                secRegistro = filtrarListProyectos.get(indexP).getSecuencia();
                if (cualCeldaP == 0) {
                    empresas = filtrarListProyectos.get(indexP).getEmpresa().getNombre();
                }
                if (cualCeldaP == 3) {
                    clientes = filtrarListProyectos.get(indexP).getPryCliente().getNombre();
                }
                if (cualCeldaP == 4) {
                    plataformas = filtrarListProyectos.get(indexP).getPryPlataforma().getDescripcion();
                }
                if (cualCeldaP == 6) {
                    monedas = filtrarListProyectos.get(indexP).getTipomoneda().getNombre();
                }
            }
            System.out.println("Indice : " + indexP + " // Celda : " + cualCeldaP);

        }
    }

    //GUARDAR
    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        guardarCambiosP();
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina VigenciasProrrateos
     */
    public void guardarCambiosP() {
        if (guardado == false) {
            if (!listProyectoBorrar.isEmpty()) {
                administrarProyectos.borrarProyectos(listProyectoBorrar);
                listProyectoBorrar.clear();
            }
            if (!listProyectoCrear.isEmpty()) {
                administrarProyectos.crearProyectos(listProyectoCrear);
                listProyectoCrear.clear();
            }
            if (!listProyectoModificar.isEmpty()) {
                administrarProyectos.editarProyectos(listProyectoModificar);
                listProyectoModificar.clear();
            }
            listProyectos = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosProyectos");
            k = 0;
        }
        cambioProyecto = false;
        indexP = -1;
        secRegistro = null;
    }

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    /**
     * Cancela las modifaciones de la tabla vigencias prorrateos
     */
    public void cancelarModificacionP() {
        if (banderaP == 1) {
            pryEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryEmpresa");
            pryEmpresa.setFilterStyle("display: none; visibility: hidden;");
            pryCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryCodigo");
            pryCodigo.setFilterStyle("display: none; visibility: hidden;");
            pryNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryNombre");
            pryNombre.setFilterStyle("display: none; visibility: hidden;");
            pryCliente = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryCliente");
            pryCliente.setFilterStyle("display: none; visibility: hidden;");
            pryPlataforma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryPlataforma");
            pryPlataforma.setFilterStyle("display: none; visibility: hidden;");
            pryMonto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryMonto");
            pryMonto.setFilterStyle("display: none; visibility: hidden;");
            pryMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryMoneda");
            pryMoneda.setFilterStyle("display: none; visibility: hidden;");
            pryPersonas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryPersonas");
            pryPersonas.setFilterStyle("display: none; visibility: hidden;");
            pryFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryFechaInicial");
            pryFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            pryFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryFechaFinal");
            pryFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            pryDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryDescripcion");
            pryDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosProyectos");
            banderaP = 0;
            filtrarListProyectos = null;
            tipoListaP = 0;
        }
        listPryPlataformas = null;
        listPryClientes = null;
        listEmpresas = null;
        listProyectoBorrar.clear();
        listProyectoCrear.clear();
        listProyectoModificar.clear();
        indexP = -1;
        secRegistro = null;
        k = 0;
        listProyectos = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosProyectos");
        cambioProyecto = false;
        nuevaProyectos = new Proyectos();
        nuevaProyectos.setTipomoneda(new Monedas());
        nuevaProyectos.setEmpresa(new Empresas());
        nuevaProyectos.setPryCliente(new PryClientes());
        nuevaProyectos.setPryPlataforma(new PryPlataformas());
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (indexP >= 0) {
            if (tipoListaP == 0) {
                editarProyecto = listProyectos.get(indexP);
            }
            if (tipoListaP == 1) {
                editarProyecto = filtrarListProyectos.get(indexP);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaP == 0) {
                context.update("formularioDialogos:editarEmpresaPD");
                context.execute("editarEmpresaPD.show()");
                cualCeldaP = -1;
            } else if (cualCeldaP == 1) {
                context.update("formularioDialogos:editarCodigoPD");
                context.execute("editarCodigoPD.show()");
                cualCeldaP = -1;
            } else if (cualCeldaP == 2) {
                context.update("formularioDialogos:editarNombrePD");
                context.execute("editarNombrePD.show()");
                cualCeldaP = -1;
            } else if (cualCeldaP == 3) {
                context.update("formularioDialogos:editarClientePD");
                context.execute("editarClientePD.show()");
                cualCeldaP = -1;
            } else if (cualCeldaP == 4) {
                context.update("formularioDialogos:editarPlataformaPD");
                context.execute("editarPlataformaPD.show()");
                cualCeldaP = -1;
            } else if (cualCeldaP == 5) {
                context.update("formularioDialogos:editarMontoPD");
                context.execute("editarMontoPD.show()");
                cualCeldaP = -1;
            } else if (cualCeldaP == 6) {
                context.update("formularioDialogos:editarMonedaPD");
                context.execute("editarMonedaPD.show()");
                cualCeldaP = -1;
            } else if (cualCeldaP == 7) {
                context.update("formularioDialogos:editarPersonasPD");
                context.execute("editarPersonasPD.show()");
                cualCeldaP = -1;
            } else if (cualCeldaP == 8) {
                context.update("formularioDialogos:editarFechaInicialPD");
                context.execute("editarFechaInicialPD.show()");
                cualCeldaP = -1;
            } else if (cualCeldaP == 9) {
                context.update("formularioDialogos:editarFechaFinalPD");
                context.execute("editarFechaFinalPD.show()");
                cualCeldaP = -1;
            } else if (cualCeldaP == 10) {
                context.update("formularioDialogos:editarDescripcionPD");
                context.execute("editarDescripcionPD.show()");
                cualCeldaP = -1;
            }
        }
        indexP = -1;
        secRegistro = null;
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Agrega una nueva Vigencia Prorrateo
     */
    public void agregarNuevaP() {
        if (validarFechasRegistro(1) == true) {
            cambioProyecto = true;
            //CERRAR FILTRADO
            if (banderaP == 1) {
                pryEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryEmpresa");
                pryEmpresa.setFilterStyle("display: none; visibility: hidden;");
                pryCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryCodigo");
                pryCodigo.setFilterStyle("display: none; visibility: hidden;");
                pryNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryNombre");
                pryNombre.setFilterStyle("display: none; visibility: hidden;");
                pryCliente = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryCliente");
                pryCliente.setFilterStyle("display: none; visibility: hidden;");
                pryPlataforma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryPlataforma");
                pryPlataforma.setFilterStyle("display: none; visibility: hidden;");
                pryMonto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryMonto");
                pryMonto.setFilterStyle("display: none; visibility: hidden;");
                pryMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryMoneda");
                pryMoneda.setFilterStyle("display: none; visibility: hidden;");
                pryPersonas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryPersonas");
                pryPersonas.setFilterStyle("display: none; visibility: hidden;");
                pryFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryFechaInicial");
                pryFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                pryFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryFechaFinal");
                pryFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                pryDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryDescripcion");
                pryDescripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosProyectos");
                banderaP = 0;
                filtrarListProyectos = null;
                tipoListaP = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS
            k++;
            l = BigInteger.valueOf(k);
            nuevaProyectos.setSecuencia(l);
            if (listProyectos == null) {
                listProyectos = new ArrayList<Proyectos>();
            }
            listProyectos.add(nuevaProyectos);
            listProyectoCrear.add(nuevaProyectos);
            //
            nuevaProyectos = new Proyectos();
            nuevaProyectos.setTipomoneda(new Monedas());
            nuevaProyectos.setEmpresa(new Empresas());
            nuevaProyectos.setPryCliente(new PryClientes());
            nuevaProyectos.setPryPlataforma(new PryPlataformas());
            limpiarNuevaP();
            //
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            indexP = -1;
            secRegistro = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosProyectos");
            context.execute("NuevoRegistroP.hide()");

        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorFechas.show()");
        }
    }

    /**
     * Limpia los elementos de una nueva vigencia prorrateo
     */
    public void limpiarNuevaP() {
        nuevaProyectos = new Proyectos();
        nuevaProyectos.setTipomoneda(new Monedas());
        nuevaProyectos.setEmpresa(new Empresas());
        nuevaProyectos.setPryCliente(new PryClientes());
        nuevaProyectos.setPryPlataforma(new PryPlataformas());
        indexP = -1;
        secRegistro = null;

    }

    public void cancelarNuevaP() {
        nuevaProyectos = new Proyectos();
        nuevaProyectos.setTipomoneda(new Monedas());
        nuevaProyectos.setEmpresa(new Empresas());
        nuevaProyectos.setPryCliente(new PryClientes());
        nuevaProyectos.setPryPlataforma(new PryPlataformas());
        indexP = -1;
        secRegistro = null;
        cambioProyecto = false;
    }

    //DUPLICAR VL
    /**
     * Metodo que verifica que proceso de duplicar se genera con respecto a la
     * posicion en la pagina que se tiene
     */
    public void verificarDuplicarProyecto() {
        if (indexP >= 0) {
            duplicarProyectoM();
        }
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Duplica una registro de VigenciaProrrateos
     */
    public void duplicarProyectoM() {
        if (indexP >= 0) {
            duplicarProyecto = new Proyectos();
            k++;
            BigDecimal var = BigDecimal.valueOf(k);
            l = BigInteger.valueOf(k);

            if (tipoListaP == 0) {
                duplicarProyecto.setSecuencia(l);
                duplicarProyecto.setFechafinal(listProyectos.get(indexP).getFechafinal());
                duplicarProyecto.setFechainicial(listProyectos.get(indexP).getFechainicial());
                duplicarProyecto.setEmpresa(listProyectos.get(indexP).getEmpresa());
                duplicarProyecto.setNombreproyecto(listProyectos.get(indexP).getNombreproyecto());
                duplicarProyecto.setCodigo(listProyectos.get(indexP).getCodigo());
                duplicarProyecto.setPryCliente(listProyectos.get(indexP).getPryCliente());
                duplicarProyecto.setPryPlataforma(listProyectos.get(indexP).getPryPlataforma());
                duplicarProyecto.setMonto(listProyectos.get(indexP).getMonto());
                duplicarProyecto.setTipomoneda(listProyectos.get(indexP).getTipomoneda());
                duplicarProyecto.setDescripcionproyecto(listProyectos.get(indexP).getDescripcionproyecto());
            }
            if (tipoListaP == 1) {
                duplicarProyecto.setSecuencia(l);
                duplicarProyecto.setFechafinal(filtrarListProyectos.get(indexP).getFechafinal());
                duplicarProyecto.setFechainicial(filtrarListProyectos.get(indexP).getFechainicial());
                duplicarProyecto.setEmpresa(filtrarListProyectos.get(indexP).getEmpresa());
                duplicarProyecto.setNombreproyecto(filtrarListProyectos.get(indexP).getNombreproyecto());
                duplicarProyecto.setCodigo(filtrarListProyectos.get(indexP).getCodigo());
                duplicarProyecto.setPryCliente(filtrarListProyectos.get(indexP).getPryCliente());
                duplicarProyecto.setPryPlataforma(filtrarListProyectos.get(indexP).getPryPlataforma());
                duplicarProyecto.setMonto(filtrarListProyectos.get(indexP).getMonto());
                duplicarProyecto.setTipomoneda(filtrarListProyectos.get(indexP).getTipomoneda());
                duplicarProyecto.setDescripcionproyecto(filtrarListProyectos.get(indexP).getDescripcionproyecto());
            }
            cambioProyecto = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarP");
            context.execute("DuplicarRegistroP.show()");
            indexP = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicarP() {
        if (validarFechasRegistro(2) == true) {
            cambioProyecto = true;
            k++;
            l = BigInteger.valueOf(k);
            duplicarProyecto.setSecuencia(l);
            if (listProyectos == null) {
                listProyectos = new ArrayList<Proyectos>();
            }
            listProyectos.add(duplicarProyecto);
            listProyectoCrear.add(duplicarProyecto);
            indexP = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            if (banderaP == 1) {
                //CERRAR FILTRADO
                pryEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryEmpresa");
                pryEmpresa.setFilterStyle("display: none; visibility: hidden;");
                pryCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryCodigo");
                pryCodigo.setFilterStyle("display: none; visibility: hidden;");
                pryNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryNombre");
                pryNombre.setFilterStyle("display: none; visibility: hidden;");
                pryCliente = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryCliente");
                pryCliente.setFilterStyle("display: none; visibility: hidden;");
                pryPlataforma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryPlataforma");
                pryPlataforma.setFilterStyle("display: none; visibility: hidden;");
                pryMonto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryMonto");
                pryMonto.setFilterStyle("display: none; visibility: hidden;");
                pryMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryMoneda");
                pryMoneda.setFilterStyle("display: none; visibility: hidden;");
                pryPersonas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryPersonas");
                pryPersonas.setFilterStyle("display: none; visibility: hidden;");
                pryFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryFechaInicial");
                pryFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                pryFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryFechaFinal");
                pryFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                pryDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryDescripcion");
                pryDescripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosProyectos");
                banderaP = 0;
                filtrarListProyectos = null;
                tipoListaP = 0;
            }
            duplicarProyecto = new Proyectos();
            limpiarduplicarP();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosProyectos");
            context.execute("DuplicarRegistroP.hide()");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorFechas.show()");
        }
    }

    public void limpiarduplicarP() {
        duplicarProyecto = new Proyectos();
        duplicarProyecto.setTipomoneda(new Monedas());
        duplicarProyecto.setEmpresa(new Empresas());
        duplicarProyecto.setPryCliente(new PryClientes());
        duplicarProyecto.setPryPlataforma(new PryPlataformas());
    }

    public void cancelarDuplicadoP() {
        cambioProyecto = false;
        duplicarProyecto = new Proyectos();
        duplicarProyecto.setTipomoneda(new Monedas());
        duplicarProyecto.setEmpresa(new Empresas());
        duplicarProyecto.setPryCliente(new PryClientes());
        duplicarProyecto.setPryPlataforma(new PryPlataformas());
        indexP = -1;
        secRegistro = null;
    }

    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void validarBorradoProyecto() {
        if (indexP >= 0) {
            borrarP();
        }
    }

    /**
     * Metodo que borra una vigencia prorrateo
     */
    public void borrarP() {
        cambioProyecto = true;
        if (tipoListaP == 0) {
            if (!listProyectoModificar.isEmpty() && listProyectoModificar.contains(listProyectos.get(indexP))) {
                int modIndex = listProyectoModificar.indexOf(listProyectos.get(indexP));
                listProyectoModificar.remove(modIndex);
                listProyectoBorrar.add(listProyectos.get(indexP));
            } else if (!listProyectoCrear.isEmpty() && listProyectoCrear.contains(listProyectos.get(indexP))) {
                int crearIndex = listProyectoCrear.indexOf(listProyectos.get(indexP));
                listProyectoCrear.remove(crearIndex);
            } else {
                listProyectoBorrar.add(listProyectos.get(indexP));
            }
            listProyectos.remove(indexP);
        }
        if (tipoListaP == 1) {
            if (!listProyectoModificar.isEmpty() && listProyectoModificar.contains(filtrarListProyectos.get(indexP))) {
                int modIndex = listProyectoModificar.indexOf(filtrarListProyectos.get(indexP));
                listProyectoModificar.remove(modIndex);
                listProyectoBorrar.add(filtrarListProyectos.get(indexP));
            } else if (!listProyectoCrear.isEmpty() && listProyectoCrear.contains(filtrarListProyectos.get(indexP))) {
                int crearIndex = listProyectoCrear.indexOf(filtrarListProyectos.get(indexP));
                listProyectoCrear.remove(crearIndex);
            } else {
                listProyectoBorrar.add(filtrarListProyectos.get(indexP));
            }
            int VPIndex = listProyectos.indexOf(filtrarListProyectos.get(indexP));
            listProyectos.remove(VPIndex);
            filtrarListProyectos.remove(indexP);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosProyectos");
        indexP = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
        }

    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado por medio de la opcion en el toolbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        filtradoProyecto();
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo
     */
    public void filtradoProyecto() {
        if (banderaP == 0) {
            pryEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryEmpresa");
            pryEmpresa.setFilterStyle("width: 90px");
            pryCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryCodigo");
            pryCodigo.setFilterStyle("width: 60px");
            pryNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryNombre");
            pryNombre.setFilterStyle("width: 80px");
            pryCliente = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryCliente");
            pryCliente.setFilterStyle("width: 80px");
            pryPlataforma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryPlataforma");
            pryPlataforma.setFilterStyle("width: 90px");
            pryMonto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryMonto");
            pryMonto.setFilterStyle("width: 60px");
            pryMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryMoneda");
            pryMoneda.setFilterStyle("width: 90px");
            pryPersonas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryPersonas");
            pryPersonas.setFilterStyle("width: 60px");
            pryFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryFechaInicial");
            pryFechaInicial.setFilterStyle("width: 60px");
            pryFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryFechaFinal");
            pryFechaFinal.setFilterStyle("width: 60px");
            pryDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryDescripcion");
            pryDescripcion.setFilterStyle("width: 200px");
            RequestContext.getCurrentInstance().update("form:datosProyectos");
            tipoListaP = 1;
            banderaP = 1;
        } else if (banderaP == 1) {
            pryEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryEmpresa");
            pryEmpresa.setFilterStyle("display: none; visibility: hidden;");
            pryCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryCodigo");
            pryCodigo.setFilterStyle("display: none; visibility: hidden;");
            pryNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryNombre");
            pryNombre.setFilterStyle("display: none; visibility: hidden;");
            pryCliente = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryCliente");
            pryCliente.setFilterStyle("display: none; visibility: hidden;");
            pryPlataforma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryPlataforma");
            pryPlataforma.setFilterStyle("display: none; visibility: hidden;");
            pryMonto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryMonto");
            pryMonto.setFilterStyle("display: none; visibility: hidden;");
            pryMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryMoneda");
            pryMoneda.setFilterStyle("display: none; visibility: hidden;");
            pryPersonas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryPersonas");
            pryPersonas.setFilterStyle("display: none; visibility: hidden;");
            pryFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryFechaInicial");
            pryFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            pryFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryFechaFinal");
            pryFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            pryDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryDescripcion");
            pryDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosProyectos");
            banderaP = 0;
            filtrarListProyectos = null;
            tipoListaP = 0;
        }

    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (banderaP == 1) {
            pryEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryEmpresa");
            pryEmpresa.setFilterStyle("display: none; visibility: hidden;");
            pryCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryCodigo");
            pryCodigo.setFilterStyle("display: none; visibility: hidden;");
            pryNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryNombre");
            pryNombre.setFilterStyle("display: none; visibility: hidden;");
            pryCliente = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryCliente");
            pryCliente.setFilterStyle("display: none; visibility: hidden;");
            pryPlataforma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryPlataforma");
            pryPlataforma.setFilterStyle("display: none; visibility: hidden;");
            pryMonto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryMonto");
            pryMonto.setFilterStyle("display: none; visibility: hidden;");
            pryMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryMoneda");
            pryMoneda.setFilterStyle("display: none; visibility: hidden;");
            pryPersonas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryPersonas");
            pryPersonas.setFilterStyle("display: none; visibility: hidden;");
            pryFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryFechaInicial");
            pryFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            pryFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryFechaFinal");
            pryFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            pryDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProyectos:pryDescripcion");
            pryDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosProyectos");
            banderaP = 0;
            filtrarListProyectos = null;
            tipoListaP = 0;
        }
        listProyectoBorrar.clear();
        listProyectoCrear.clear();
        listProyectoModificar.clear();
        indexP = -1;
        secRegistro = null;
        k = 0;
        listProyectos = null;
        guardado = true;
        cambioProyecto = false;
        tipoActualizacion = -1;
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO) (list = ESTRUCTURAS - MOTIVOSLOCALIZACIONES - PROYECTOS)

    /**
     * Metodo que ejecuta los dialogos de estructuras, motivos localizaciones,
     * proyectos
     *
     * @param indice Fila de la tabla
     * @param dlg Dialogo
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     * VigenciaProrrateoProyecto
     */
    public void asignarIndex(Integer indice, int dlg, int LND) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            indexP = indice;
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("form:EmpresasDialogo");
            context.execute("EmpresasDialogo.show()");
        } else if (dlg == 1) {
            context.update("form:ClientesDialogo");
            context.execute("ClientesDialogo.show()");
        } else if (dlg == 2) {
            context.update("form:PlataformasDialogo");
            context.execute("PlataformasDialogo.show()");
        } else if (dlg == 3) {
            context.update("form:MonedasDialogo");
            context.execute("MonedasDialogo.show()");
        }

    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el motivo localizacion seleccionado (vigencia
     * localizacion)
     */
    public void actualizarEmpresa() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listProyectos.get(indexP).setEmpresa(empresaSeleccionada);
                if (!listProyectoCrear.contains(listProyectos.get(indexP))) {
                    if (listProyectoModificar.isEmpty()) {
                        listProyectoModificar.add(listProyectos.get(indexP));
                    } else if (!listProyectoModificar.contains(listProyectos.get(indexP))) {
                        listProyectoModificar.add(listProyectos.get(indexP));
                    }
                }
                if (guardado == true) {
                    guardado = false;
                }
                cambioProyecto = true;
                permitirIndexP = true;

            } else {
                filtrarListProyectos.get(indexP).setEmpresa(empresaSeleccionada);
                if (!listProyectoCrear.contains(filtrarListProyectos.get(indexP))) {
                    if (listProyectoModificar.isEmpty()) {
                        listProyectoModificar.add(filtrarListProyectos.get(indexP));
                    } else if (!listProyectoModificar.contains(filtrarListProyectos.get(indexP))) {
                        listProyectoModificar.add(filtrarListProyectos.get(indexP));
                    }
                }
                if (guardado == true) {
                    guardado = false;
                }
                cambioProyecto = true;
                permitirIndexP = true;

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarEmpresaP");
        } else if (tipoActualizacion == 1) {
            nuevaProyectos.setEmpresa(empresaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaEmpresaP");
        } else if (tipoActualizacion == 2) {
            duplicarProyecto.setEmpresa(empresaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEmpresaP");
        }
        filtrarListEmpresas = null;
        empresaSeleccionada = null;
        aceptar = true;
        indexP = -1;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela la seleccion del motivo localizacion (vigencia
     * localizacion)
     */
    public void cancelarCambioEmpresa() {
        filtrarListEmpresas = null;
        empresaSeleccionada = null;
        aceptar = true;
        indexP = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexP = true;
    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el proyecto seleccionado (vigencia localizacion)
     */
    public void actualizarCliente() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listProyectos.get(indexP).setPryCliente(clienteSeleccionado);
                if (!listProyectoCrear.contains(listProyectos.get(indexP))) {
                    if (listProyectoModificar.isEmpty()) {
                        listProyectoModificar.add(listProyectos.get(indexP));
                    } else if (!listProyectoModificar.contains(listProyectos.get(indexP))) {
                        listProyectoModificar.add(listProyectos.get(indexP));
                    }
                }
            } else {
                filtrarListProyectos.get(indexP).setPryCliente(clienteSeleccionado);
                if (!listProyectoCrear.contains(filtrarListProyectos.get(indexP))) {
                    if (listProyectoModificar.isEmpty()) {
                        listProyectoModificar.add(filtrarListProyectos.get(indexP));
                    } else if (!listProyectoModificar.contains(filtrarListProyectos.get(indexP))) {
                        listProyectoModificar.add(filtrarListProyectos.get(indexP));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambioProyecto = true;
            permitirIndexP = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarClienteP");
        } else if (tipoActualizacion == 1) {
            nuevaProyectos.setPryCliente(clienteSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaClienteP");
        } else if (tipoActualizacion == 2) {
            duplicarProyecto.setPryCliente(clienteSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarClienteP");
        }
        filtrarListPryClientes = null;
        clienteSeleccionado = null;
        aceptar = true;
        indexP = -1;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela la seleccion del proyecto (vigencia localizacion)
     */
    public void cancelarCambioCliente() {
        filtrarListPryClientes = null;
        clienteSeleccionado = null;
        aceptar = true;
        indexP = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexP = true;
    }

    public void actualizarPlataforma() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listProyectos.get(indexP).setPryPlataforma(plataformaSeleccionada);
                if (!listProyectoCrear.contains(listProyectos.get(indexP))) {
                    if (listProyectoModificar.isEmpty()) {
                        listProyectoModificar.add(listProyectos.get(indexP));
                    } else if (!listProyectoModificar.contains(listProyectos.get(indexP))) {
                        listProyectoModificar.add(listProyectos.get(indexP));
                    }
                }
            } else {
                filtrarListProyectos.get(indexP).setPryPlataforma(plataformaSeleccionada);
                if (!listProyectoCrear.contains(filtrarListProyectos.get(indexP))) {
                    if (listProyectoModificar.isEmpty()) {
                        listProyectoModificar.add(filtrarListProyectos.get(indexP));
                    } else if (!listProyectoModificar.contains(filtrarListProyectos.get(indexP))) {
                        listProyectoModificar.add(filtrarListProyectos.get(indexP));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambioProyecto = true;
            permitirIndexP = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarPlataformaP");
        } else if (tipoActualizacion == 1) {
            nuevaProyectos.setPryPlataforma(plataformaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaPlataformaP");
        } else if (tipoActualizacion == 2) {
            duplicarProyecto.setPryPlataforma(plataformaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarPlataformaP");
        }
        filtrarListPryPlataformas = null;
        plataformaSeleccionada = null;
        aceptar = true;
        indexP = -1;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioPlataforma() {
        filtrarListPryPlataformas = null;
        plataformaSeleccionada = null;
        aceptar = true;
        indexP = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexP = true;
    }

    public void actualizarMoneda() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listProyectos.get(indexP).setTipomoneda(monedaSeleccionada);
                if (!listProyectoCrear.contains(listProyectos.get(indexP))) {
                    if (listProyectoModificar.isEmpty()) {
                        listProyectoModificar.add(listProyectos.get(indexP));
                    } else if (!listProyectoModificar.contains(listProyectos.get(indexP))) {
                        listProyectoModificar.add(listProyectos.get(indexP));
                    }
                }
            } else {
                filtrarListProyectos.get(indexP).setTipomoneda(monedaSeleccionada);
                if (!listProyectoCrear.contains(filtrarListProyectos.get(indexP))) {
                    if (listProyectoModificar.isEmpty()) {
                        listProyectoModificar.add(filtrarListProyectos.get(indexP));
                    } else if (!listProyectoModificar.contains(filtrarListProyectos.get(indexP))) {
                        listProyectoModificar.add(filtrarListProyectos.get(indexP));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambioProyecto = true;
            permitirIndexP = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarMonedaP");
        } else if (tipoActualizacion == 1) {
            nuevaProyectos.setTipomoneda(monedaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaMonedaP");
        } else if (tipoActualizacion == 2) {
            duplicarProyecto.setTipomoneda(monedaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarMonedaP");
        }
        filtrarListMonedas = null;
        monedaSeleccionada = null;
        aceptar = true;
        indexP = -1;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioMoneda() {
        filtrarListMonedas = null;
        monedaSeleccionada = null;
        aceptar = true;
        indexP = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexP = true;
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexP >= 0) {
            if (cualCeldaP == 0) {
                context.update("form:EmpresasDialogo");
                context.execute("EmpresasDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaP == 3) {
                context.update("form:ClientesDialogo");
                context.execute("ClientesDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaP == 4) {
                context.update("form:PlataformasDialogo");
                context.execute("PlataformasDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaP == 6) {
                context.update("form:MonedasDialogo");
                context.execute("MonedasDialogo.show()");
                tipoActualizacion = 0;
            }
        }

    }

    /**
     * Metodo que activa el boton aceptar de la pagina y los dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    /**
     * Valida la tabla a exportar en PDF con respecto al index activo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        exportPDF_P();
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF_P() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarP:datosProyectoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "ProyectosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexP = -1;
        secRegistro = null;
    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        exportXLS_P();
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Afiliaciones
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS_P() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarP:datosProyectoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "ProyectosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexP = -1;
        secRegistro = null;
    }

    //EVENTO FILTRAR
    /**
     * Evento que cambia la lista real a la filtrada
     */
    public void eventoFiltrar() {
        if (indexP >= 0) {
            if (tipoListaP == 0) {
                tipoListaP = 1;
            }
        }
    }

    public void dialogoProyecto() {
        filtrarListProyectos = null;
        proyectoSeleccionado = new Proyectos();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:BuscarProyectoDialogo");
        context.execute("BuscarProyectoDialogo.show()");
    }

    public void actualizarProyecto() {
        listProyectos = null;
        filtrarListProyectos = null;
        listProyectos = new ArrayList<Proyectos>();
        listProyectos.add(proyectoSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosProyectos");
        proyectoSeleccionado = new Proyectos();
    }

    public void cancelarActualizarProyecto() {
        proyectoSeleccionado = new Proyectos();
    }

    public void mostrarTodos() {
        listProyectos = null;
        filtrarListProyectos = null;
        listProyectos = administrarProyectos.Proyectos();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosProyectos");
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listProyectos != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "PROYECTOS");
                backUpSecRegistro = secRegistro;
                secRegistro = null;
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
            if (administrarRastros.verificarHistoricosTabla("PROYECTOS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        indexP = -1;
    }

    //GET - SET 
    public List<Proyectos> getListProyectos() {
        try {
            if (listProyectos == null) {
                listProyectos = new ArrayList<Proyectos>();
                listProyectos = administrarProyectos.Proyectos();
                for (int i = 0; i < listProyectos.size(); i++) {
                    if (listProyectos.get(i).getEmpresa() == null) {
                        listProyectos.get(i).setEmpresa(new Empresas());
                    }
                    if (listProyectos.get(i).getTipomoneda() == null) {
                        listProyectos.get(i).setTipomoneda(new Monedas());
                    }
                    if (listProyectos.get(i).getPryCliente() == null) {
                        listProyectos.get(i).setPryCliente(new PryClientes());
                    }
                    if (listProyectos.get(i).getPryPlataforma() == null) {
                        listProyectos.get(i).setPryPlataforma(new PryPlataformas());
                    }
                }
            }
            return listProyectos;
        } catch (Exception e) {
            System.out.println("Error en getListProyectos : " + e.toString());
            return null;
        }
    }

    public void setListProyectos(List<Proyectos> setListProyectos) {
        this.listProyectos = setListProyectos;
    }

    public List<Proyectos> getFiltrarListProyectos() {
        return filtrarListProyectos;
    }

    public void setFiltrarListProyectos(List<Proyectos> filtrar) {
        this.filtrarListProyectos = filtrar;
    }

    public List<Empresas> getListEmpresas() {
        try {
            if (listEmpresas == null) {
                listEmpresas = new ArrayList<Empresas>();
                listEmpresas = administrarProyectos.listEmpresas();
            }
            return listEmpresas;
        } catch (Exception e) {
            System.out.println("Error getListEmpresas " + e.toString());
            return null;
        }
    }

    public void setListEmpresas(List<Empresas> setListEmpresas) {
        this.listEmpresas = setListEmpresas;
    }

    public Empresas getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresas seleccionado) {
        this.empresaSeleccionada = seleccionado;
    }

    public List<Empresas> getFiltrarListEmpresas() {
        return filtrarListEmpresas;
    }

    public void setFiltrarListEmpresas(List<Empresas> setFiltrarListEmpresas) {
        this.filtrarListEmpresas = setFiltrarListEmpresas;
    }

    public List<PryClientes> getListPryClientes() {
        try {
            if (listPryClientes == null) {
                listPryClientes = new ArrayList<PryClientes>();
                listPryClientes = administrarProyectos.listPryClientes();
            }
            return listPryClientes;
        } catch (Exception e) {
            System.out.println("Error getListPryClientes " + e.toString());
            return null;
        }
    }

    public void setListPryClientes(List<PryClientes> setListPryClientes) {
        this.listPryClientes = setListPryClientes;
    }

    public PryClientes getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(PryClientes seleccionado) {
        this.clienteSeleccionado = seleccionado;
    }

    public List<PryClientes> getFiltrarListPryClientes() {
        return filtrarListPryClientes;
    }

    public void setFiltrarListPryClientes(List<PryClientes> setFiltrarListPryClientes) {
        this.filtrarListPryClientes = setFiltrarListPryClientes;
    }

    public List<Proyectos> getListProyectoModificar() {
        return listProyectoModificar;
    }

    public void setListProyectoModificar(List<Proyectos> setListProyectoModificar) {
        this.listProyectoModificar = setListProyectoModificar;
    }

    public Proyectos getNuevaProyectos() {
        return nuevaProyectos;
    }

    public void setNuevaProyectos(Proyectos setNuevaProyectos) {
        this.nuevaProyectos = setNuevaProyectos;
    }

    public List<Proyectos> getListProyectoBorrar() {
        return listProyectoBorrar;
    }

    public void setListProyectoBorrar(List<Proyectos> setListProyectoBorrar) {
        this.listProyectoBorrar = setListProyectoBorrar;
    }

    public Proyectos getEditarProyecto() {
        return editarProyecto;
    }

    public void setEditarProyecto(Proyectos setEditarProyecto) {
        this.editarProyecto = setEditarProyecto;
    }

    public Proyectos getDuplicarProyecto() {
        return duplicarProyecto;
    }

    public void setDuplicarProyecto(Proyectos setDuplicarProyecto) {
        this.duplicarProyecto = setDuplicarProyecto;
    }

    public List<Proyectos> getListProyectoCrear() {
        return listProyectoCrear;
    }

    public void setListProyectoCrear(List<Proyectos> setListProyectoCrear) {
        this.listProyectoCrear = setListProyectoCrear;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<PryPlataformas> getListPryPlataformas() {
        try {
            if (listPryPlataformas == null) {
                listPryPlataformas = new ArrayList<PryPlataformas>();
                listPryPlataformas = administrarProyectos.listPryPlataformas();
            }
            return listPryPlataformas;
        } catch (Exception e) {
            return null;
        }

    }

    public void setListPryPlataformas(List<PryPlataformas> setListPryPlataformas) {
        this.listPryPlataformas = setListPryPlataformas;
    }

    public PryPlataformas getPlataformaSeleccionada() {
        return plataformaSeleccionada;
    }

    public void setPlataformaSeleccionada(PryPlataformas setPlataformaSeleccionada) {
        this.plataformaSeleccionada = setPlataformaSeleccionada;
    }

    public List<PryPlataformas> getFiltrarListPryPlataformas() {
        return filtrarListPryPlataformas;
    }

    public void setFiltrarListPryPlataformas(List<PryPlataformas> setFiltrarListPryPlataformas) {
        this.filtrarListPryPlataformas = setFiltrarListPryPlataformas;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public BigInteger getBackUpSecRegistro() {
        return backUpSecRegistro;
    }

    public void setBackUpSecRegistro(BigInteger backUpSecRegistro) {
        this.backUpSecRegistro = backUpSecRegistro;
    }

    public List<Monedas> getListMonedas() {
        if (listMonedas == null) {
            listMonedas = new ArrayList<Monedas>();
            listMonedas = administrarProyectos.listMonedas();
        }
        return listMonedas;
    }

    public void setListMonedas(List<Monedas> listMonedas) {
        this.listMonedas = listMonedas;
    }

    public Monedas getMonedaSeleccionada() {
        return monedaSeleccionada;
    }

    public void setMonedaSeleccionada(Monedas monedaSeleccionada) {
        this.monedaSeleccionada = monedaSeleccionada;
    }

    public List<Monedas> getFiltrarListMonedas() {
        return filtrarListMonedas;
    }

    public void setFiltrarListMonedas(List<Monedas> filtrarListMonedas) {
        this.filtrarListMonedas = filtrarListMonedas;
    }

    public Proyectos getProyectoSeleccionado() {
        return proyectoSeleccionado;
    }

    public void setProyectoSeleccionado(Proyectos proyectoSeleccionado) {
        this.proyectoSeleccionado = proyectoSeleccionado;
    }
}
