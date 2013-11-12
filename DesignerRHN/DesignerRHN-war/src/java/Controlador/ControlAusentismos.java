/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Causasausentismos;
import Entidades.Clasesausentismos;
import Entidades.Diagnosticoscategorias;
import Entidades.Empleados;
import Entidades.Enfermedades;
import Entidades.Ibcs;
import Entidades.Soaccidentes;
import Entidades.Soausentismos;
import Entidades.Terceros;
import Entidades.Tiposausentismos;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarSoausentismosInterface;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class ControlAusentismos implements Serializable {

    @EJB
    AdministrarSoausentismosInterface administrarAusentismos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //LISTA FICTI PORCENTAJES
    private List<String> listaPorcentaje;
    private List<String> filtradosListaPorcentajes;
    private String seleccionPorcentajes;
    //LISTA FICTI IBCS
    private List<Ibcs> listaIBCS;
    private List<Ibcs> filtradosListaIBCS;
    private Ibcs seleccionIBCS;
    //LISTA FICTI FORMA LIQUIDACION
    private List<String> listaForma;
    private List<String> filtradosListaForma;
    private String seleccionForma;
    //SECUENCIA DEL EMPLEADO
    private BigInteger secuenciaEmpleado;
    //LISTA AUSENTISMOS
    private List<Soausentismos> listaAusentismos;
    private List<Soausentismos> filtradosListaAusentismos;
    //LISTA DE ARRIBA
    private List<Empleados> listaEmpleadosAusentismo;
    private List<Empleados> filtradosListaEmpleadosAusentismo;
    private Empleados seleccionMostrar; //Seleccion Mostrar
    //editar celda
    private Soausentismos editarAusentismos;
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista;
    //OTROS
    private boolean aceptar;
    private int index;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private boolean permitirIndex;
    //RASTROS
    private BigInteger secRegistro;
    private boolean guardado, guardarOk;
    //Crear Novedades
    private List<Soausentismos> listaAusentismosCrear;
    public Soausentismos nuevoAusentismo;
    private int k;
    private BigInteger l;
    private String mensajeValidacion;
    //Modificar Novedades
    private List<Soausentismos> listaAusentismosModificar;
    //Borrar Novedades
    private List<Soausentismos> listaAusentismosBorrar;
    //L.O.V EMPLEADOS
    private List<Empleados> listaEmpleados;
    private List<Empleados> filtradoslistaEmpleados;
    private Empleados seleccionEmpleados;
    //Autocompletar
    private String TipoAusentismo, Tercero, ClaseAusentismo, CausaAusentismo, Porcentaje, Forma, AD, Enfermedad, Diagnostico;
    private String BaseLiquidacion; // faltan más campos
    //L.O.V TIPO AUSENTISMO
    private List<Tiposausentismos> listaTiposAusentismos;
    private List<Tiposausentismos> filtradoslistaTiposAusentismos;
    private Tiposausentismos seleccionTiposAusentismos;
    //L.O.V CLASE AUSENTISMO
    private List<Clasesausentismos> listaClasesAusentismos;
    private List<Clasesausentismos> filtradoslistaClasesAusentismos;
    private Clasesausentismos seleccionClasesAusentismos;
    //L.O.V CAUSA AUSENTISMO
    private List<Causasausentismos> listaCausasAusentismos;
    private List<Causasausentismos> filtradoslistaCausasAusentismos;
    private Causasausentismos seleccionCausasAusentismos;
    //L.O.V Descripcion Accidente
    private List<Soaccidentes> listaAccidentes;
    private List<Soaccidentes> filtradoslistaAccidentes;
    private Soaccidentes seleccionAccidentes;
    //L.O.V Enfermedades
    private List<Enfermedades> listaEnfermedades;
    private List<Enfermedades> filtradoslistaEnfermedades;
    private Enfermedades seleccionEnfermedades;
    //L.O.V Terceros
    private List<Terceros> listaTerceros;
    private List<Terceros> filtradoslistaTerceros;
    private Terceros seleccionTerceros;
    //L.O.V Diagnostivos
    private List<Diagnosticoscategorias> listaDiagnosticos;
    private List<Diagnosticoscategorias> filtradoslistaDiagnosticos;
    private Diagnosticoscategorias seleccionDiagnosticos;
    //Duplicar
    private Soausentismos duplicarAusentismo;

    public ControlAusentismos() {
        listaIBCS = null;
        listaAccidentes = null;
        listaDiagnosticos = null;
        listaPorcentaje = new ArrayList<String>();
        listaPorcentaje.add("50");
        listaPorcentaje.add("66.6666");
        listaPorcentaje.add("80");
        listaPorcentaje.add("100");
        listaForma = new ArrayList<String>();
        listaForma.add("BASICO");
        listaForma.add("IBC MES ANTERIOR");
        listaForma.add("IBC MES ENERO");
        listaForma.add("IBC MES INCAPACIDAD");
        listaForma.add("PROMEDIO ACUMULADOS 12 MESES");
        listaForma.add("PROMEDIO IBC 12 MESES");
        listaForma.add("PROMEDIO IBC 6 MESES");
        permitirIndex = true;
        listaAusentismos = null;
        listaEmpleados = null;
        listaEmpleadosAusentismo = null;
        permitirIndex = true;
        aceptar = true;
        secRegistro = null;
        guardado = true;
        tipoLista = 0;
        listaAusentismosBorrar = new ArrayList<Soausentismos>();
        listaAusentismosCrear = new ArrayList<Soausentismos>();
        listaAusentismosModificar = new ArrayList<Soausentismos>();
    }

    //Ubicacion Celda Arriba 
    public void cambiarEmpleado() {
        //Si ninguna de las 3 listas (crear,modificar,borrar) tiene algo, hace esto
        //{
        //if (listaAusentismosCrear.isEmpty() && listaAusentismosBorrar.isEmpty() && listaAusentismosModificar.isEmpty()) {
        secuenciaEmpleado = seleccionMostrar.getSecuencia();
        listaAusentismos = null;
        listaIBCS = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosAusentismosEmpleado");


        listaAusentismosCrear.clear();
        listaAusentismosBorrar.clear();
        listaAusentismosModificar.clear();
        System.out.println("SecuenciaEmpleado: " + secuenciaEmpleado);
    }
    //}
/*else {
     RequestContext context = RequestContext.getCurrentInstance();
     context.update("formularioDialogos:cambiar");
     context.execute("cambiar.show()");
     }*/

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public void asignarIndex(Integer indice, int dlg, int LND) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
            index = -1;
            secRegistro = null;
            System.out.println("Tipo Actualizacion: " + tipoActualizacion);
        } else if (LND == 2) {
            index = -1;
            secRegistro = null;
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("formularioDialogos:empleadosDialogo");
            context.execute("empleadosDialogo.show()");
        } else if (dlg == 1) {
            context.update("formularioDialogos:tiposAusentismosDialogo");
            context.execute("tiposAusentismosDialogo.show()");
        } else if (dlg == 2) {
            context.update("formularioDialogos:clasesAusentismosDialogo");
            context.execute("clasesAusentismosDialogo.show()");
        } else if (dlg == 3) {
            context.update("formularioDialogos:causasAusentismosDialogo");
            context.execute("causasAusentismosDialogo.show()");
        } else if (dlg == 4) {
            context.update("formularioDialogos:porcentajesDialogo");
            context.execute("porcentajesDialogo.show()");
        } else if (dlg == 5) {
            context.update("formularioDialogos:ibcsDialogo");
            context.execute("ibcsDialogo.show()");
        } else if (dlg == 6) {
            context.update("formularioDialogos:formasDialogo");
            context.execute("formasDialogo.show()");
        } else if (dlg == 7) {
            context.update("formlarioDialogos:accidentesDialogo");
            context.execute("accidentesDialogo.show()");
        } else if (dlg == 8) {
            context.update("formlarioDialogos:tercerosDialogo");
            context.execute("tercerosDialogo.show()");
        }
    }

    public void mostrarTodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaEmpleadosAusentismo.isEmpty()) {
            listaEmpleadosAusentismo.clear();
            listaEmpleadosAusentismo = administrarAusentismos.lovEmpleados();
        } else {
            listaEmpleadosAusentismo = administrarAusentismos.lovEmpleados();
        }
        if (!listaEmpleadosAusentismo.isEmpty()) {
            seleccionMostrar = listaEmpleadosAusentismo.get(0);
            listaEmpleadosAusentismo = null;
            getListaEmpleados();
        }
        listaAusentismos = null;
        context.update("form:datosEmpleados");
        context.update("form:datosAusentismosEmpleado");
        filtradosListaAusentismos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarEmpleados() {
        RequestContext context = RequestContext.getCurrentInstance();
        Empleados e = seleccionEmpleados;

        if (!listaEmpleadosAusentismo.isEmpty()) {
            listaEmpleadosAusentismo.clear();
            listaEmpleadosAusentismo.add(e);
            seleccionMostrar = listaEmpleadosAusentismo.get(0);
        } else {
            listaEmpleadosAusentismo.add(e);
        }
        secuenciaEmpleado = seleccionEmpleados.getSecuencia();
        listaAusentismos = null;
        context.execute("empleadosDialogo.hide()");
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.update("formularioDialogos:LOVEmpleados");
        context.update("form:datosEmpleados");
        context.update("form:datosAusentismosEmpleado");
        filtradosListaAusentismos = null;
        seleccionEmpleados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void actualizarPorcentajes() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setPorcentajeindividual(new BigDecimal(seleccionPorcentajes));
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setPorcentajeindividual(new BigDecimal(seleccionPorcentajes));
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setPorcentajeindividual(new BigDecimal(seleccionPorcentajes));
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setPorcentajeindividual(new BigDecimal(seleccionPorcentajes));
            context.update("formularioDialogos:duplicarAusentismo");

        }
        filtradosListaPorcentajes = null;
        seleccionPorcentajes = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("porcentajesDialogo.hide()");
        context.reset("formularioDialogos:LOVPorcentajes:globalFilter");
        context.update("formularioDialogos:LOVPorcentajes");
    }

    public void actualizarIBCS() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setBaseliquidacion(seleccionIBCS.getValor().toBigInteger());
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setBaseliquidacion(seleccionIBCS.getValor().toBigInteger());
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setBaseliquidacion((seleccionIBCS.getValor().toBigInteger()));
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setBaseliquidacion((seleccionIBCS.getValor().toBigInteger()));
            context.update("formularioDialogos:duplicarAusentismo");

        }
        filtradosListaIBCS = null;
        seleccionIBCS = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("ibcsDialogo.hide()");
        context.reset("formularioDialogos:LOVIbcs:globalFilter");
        context.update("formularioDialogos:LOVIbcs");
    }

    public void actualizarFormas() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setFormaliquidacion(seleccionForma);
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setFormaliquidacion(seleccionForma);
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setFormaliquidacion(seleccionForma);
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setFormaliquidacion(seleccionForma);
            context.update("formularioDialogos:duplicarAusentismo");

        }
        filtradosListaForma = null;
        seleccionForma = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("formasDialogo.hide()");
        context.reset("formularioDialogos:LOVFormas:globalFilter");
        context.update("formularioDialogos:LOVFormas");
    }

    public void actualizarAD() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaAusentismos.get(index).setAccidente(seleccionAccidentes);
                if (!listaAusentismosCrear.contains(listaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(index))) {
                        listaAusentismosModificar.add(listaAusentismos.get(index));
                    }
                }
            } else {
                filtradosListaAusentismos.get(index).setAccidente(seleccionAccidentes);
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(index))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(index))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosAusentismosEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevoAusentismo.setAccidente((seleccionAccidentes));
            context.update("formularioDialogos:nuevoAusentismo");
        } else if (tipoActualizacion == 2) {
            duplicarAusentismo.setAccidente((seleccionAccidentes));
            context.update("formularioDialogos:duplicarAusentismo");

        }
        filtradoslistaAccidentes = null;
        seleccionAccidentes = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("accidentesDialogo.hide()");
        context.reset("formularioDialogos:LOVAccidentes:globalFilter");
        context.update("formularioDialogos:LOVAccidentes");
    }

    public void cancelarCambioEmpleados() {
        filtradoslistaEmpleados = null;
        seleccionEmpleados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioPorcentajes() {
        filtradosListaPorcentajes = null;
        seleccionPorcentajes = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioFormas() {
        filtradosListaForma = null;
        seleccionForma = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioIBCS() {
        filtradosListaIBCS = null;
        seleccionIBCS = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioAD() {
        filtradoslistaAccidentes = null;
        seleccionAccidentes = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    //AUTOCOMPLETAR
    public void modificarAusentismos(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaAusentismosCrear.contains(listaAusentismos.get(indice))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(indice));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(indice))) {
                        listaAusentismosModificar.add(listaAusentismos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(indice))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(indice));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(indice))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
            }

            context.update("form:datosAusentismosEmpleado");
        } else if (confirmarCambio.equalsIgnoreCase("TIPO")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getTipo().setDescripcion(TipoAusentismo);
            } else {
                filtradosListaAusentismos.get(indice).getTipo().setDescripcion(TipoAusentismo);
            }

            for (int i = 0; i < listaAusentismos.size(); i++) {
                if (listaAusentismos.get(i).getTipo().getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setTipo(listaTiposAusentismos.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setTipo(listaTiposAusentismos.get(indiceUnicoElemento));
                }
                listaTiposAusentismos.clear();
                getListaTiposAusentismos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:tiposAusentismosDialogo");
                context.execute("tiposAusentismosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("TERCERO")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getTercero().setNombre(Tercero);
            } else {
                filtradosListaAusentismos.get(indice).getTercero().setNombre(Tercero);
            }

            for (int i = 0; i < listaAusentismos.size(); i++) {
                if (listaAusentismos.get(i).getTercero().getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setTipo(listaTiposAusentismos.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setTipo(listaTiposAusentismos.get(indiceUnicoElemento));
                }
                listaTiposAusentismos.clear();
                getListaTiposAusentismos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:tiposAusentismosDialogo");
                context.execute("tiposAusentismosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CLASE")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getClase().setDescripcion(ClaseAusentismo);
            } else {
                filtradosListaAusentismos.get(indice).getClase().setDescripcion(ClaseAusentismo);
            }

            for (int i = 0; i < listaAusentismos.size(); i++) {
                if (listaAusentismos.get(i).getClase().getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setClase(listaClasesAusentismos.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setClase(listaClasesAusentismos.get(indiceUnicoElemento));
                }
                listaClasesAusentismos.clear();
                getListaClasesAusentismos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:clasesAusentismosDialogo");
                context.execute("clasesAusentismosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CAUSA")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getCausa().setDescripcion(CausaAusentismo);
            } else {
                filtradosListaAusentismos.get(indice).getCausa().setDescripcion(CausaAusentismo);
            }

            for (int i = 0; i < listaAusentismos.size(); i++) {
                if (listaAusentismos.get(i).getCausa().getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setCausa(listaCausasAusentismos.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setCausa(listaCausasAusentismos.get(indiceUnicoElemento));
                }
                listaCausasAusentismos.clear();
                getListaCausasAusentismos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:causasAusentismosDialogo");
                context.execute("causasAusentismosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("PORCENTAJE")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).setPorcentajeindividual(new BigDecimal(Porcentaje));
            } else {
                filtradosListaAusentismos.get(indice).setPorcentajeindividual(new BigDecimal(Porcentaje));
            }

            for (int i = 0; i < listaAusentismos.size(); i++) {
                if ((listaAusentismos.get(i).getPorcentajeindividual()).toString().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setPorcentajeindividual(new BigDecimal(listaPorcentaje.get(indiceUnicoElemento)));
                } else {
                    filtradosListaAusentismos.get(indice).setPorcentajeindividual(new BigDecimal(listaPorcentaje.get(indiceUnicoElemento)));
                }
                listaPorcentaje.clear();
                getListaPorcentaje();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:porcentajesDialogo");
                context.execute("porcentajesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("BASE")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).setBaseliquidacion(new BigInteger(BaseLiquidacion));
            } else {
                filtradosListaAusentismos.get(indice).setBaseliquidacion(new BigInteger(BaseLiquidacion));
            }

            for (int i = 0; i < listaAusentismos.size(); i++) {
                if ((listaAusentismos.get(i).getBaseliquidacion()).toString().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setBaseliquidacion(listaIBCS.get(indiceUnicoElemento).getSecuencia());
                } else {
                    filtradosListaAusentismos.get(indice).setBaseliquidacion(listaIBCS.get(indiceUnicoElemento).getSecuencia());
                }
                listaIBCS.clear();
                getListaIBCS();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:ibcsDialogo");
                context.execute("ibcsDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("FORMA")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).setFormaliquidacion(Forma);
            } else {
                filtradosListaAusentismos.get(indice).setFormaliquidacion(Forma);
            }

            for (int i = 0; i < listaAusentismos.size(); i++) {
                if ((listaAusentismos.get(i).getFormaliquidacion()).startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setFormaliquidacion(listaForma.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setFormaliquidacion(listaForma.get(indiceUnicoElemento));
                }
                listaForma.clear();
                getListaForma();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:formasDialogo");
                context.execute("formasDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("AD")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getAccidente().setDescripcioncaso(AD);
            } else {
                filtradosListaAusentismos.get(indice).getAccidente().setDescripcioncaso(AD);
            }

            for (int i = 0; i < listaAusentismos.size(); i++) {
                if (listaAusentismos.get(i).getAccidente().getDescripcioncaso().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setAccidente(listaAccidentes.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setAccidente(listaAccidentes.get(indiceUnicoElemento));
                }
                listaAccidentes.clear();
                getListaAccidentes();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:accidentesDialogo");
                context.execute("accidentesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("DIAGNOSTICO")) {
            if (tipoLista == 0) {
                listaAusentismos.get(indice).getDiagnosticocategoria().setCodigo(Diagnostico);
            } else {
                filtradosListaAusentismos.get(indice).getDiagnosticocategoria().setCodigo(Diagnostico);
            }

            for (int i = 0; i < listaAusentismos.size(); i++) {
                if (listaAusentismos.get(i).getDiagnosticocategoria().getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaAusentismos.get(indice).setDiagnosticocategoria(listaDiagnosticos.get(indiceUnicoElemento));
                } else {
                    filtradosListaAusentismos.get(indice).setDiagnosticocategoria(listaDiagnosticos.get(indiceUnicoElemento));
                }
                listaDiagnosticos.clear();
                getListaDiagnosticos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:diagnosticosDialogo");
                context.execute("diagnosticosDialogo.show()");
                tipoActualizacion = 0;
            }
        }/* else if (confirmarCambio.equalsIgnoreCase("ENFERMEDAD")) {
         if (tipoLista == 0) {
         listaAusentismos.get(indice).getEnfermedad().setDescripcion(Enfermedad);
         } else {
         filtradosListaAusentismos.get(indice).getEnfermedad().setDescripcion(Enfermedad);
         }

         for (int i = 0; i < listaAusentismos.size(); i++) {
         if (listaAusentismos.get(i).getEnfermedad().getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
         indiceUnicoElemento = i;
         coincidencias++;
         }
         }
         if (coincidencias == 1) {
         if (tipoLista == 0) {
         listaAusentismos.get(indice).setEnfermedad(listaAccidentes.get(indiceUnicoElemento));
         } else {
         filtradosListaAusentismos.get(indice).setAccidente(listaAccidentes.get(indiceUnicoElemento));
         }
         listaAccidentes.clear();
         getListaAccidentes();
         } else {
         permitirIndex = false;
         context.update("formularioDialogos:accidentesDialogo");
         context.execute("accidentesDialogo.show()");
         tipoActualizacion = 0;
         }
         }*/
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaAusentismosCrear.contains(listaAusentismos.get(indice))) {
                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(listaAusentismos.get(indice));
                    } else if (!listaAusentismosModificar.contains(listaAusentismos.get(indice))) {
                        listaAusentismosModificar.add(listaAusentismos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaAusentismosCrear.contains(filtradosListaAusentismos.get(indice))) {

                    if (listaAusentismosModificar.isEmpty()) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(indice));
                    } else if (!listaAusentismosModificar.contains(filtradosListaAusentismos.get(indice))) {
                        listaAusentismosModificar.add(filtradosListaAusentismos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosAusentismosEmpleado");
    }

    //Ubicacion Celda Indice Abajo. //Van los que no son NOT NULL.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secRegistro = listaAusentismos.get(index).getSecuencia();
                if (cualCelda == 0) {
                    TipoAusentismo = listaAusentismos.get(index).getTipo().getDescripcion();
                } else if (cualCelda == 1) {
                    ClaseAusentismo = listaAusentismos.get(index).getClase().getDescripcion();
                }
            } else {
                secRegistro = filtradosListaAusentismos.get(index).getSecuencia();
                if (cualCelda == 0) {
                    TipoAusentismo = filtradosListaAusentismos.get(index).getTipo().getDescripcion();
                } else if (cualCelda == 1) {
                    ClaseAusentismo = filtradosListaAusentismos.get(index).getClase().getDescripcion();
                }
            }
        }
        System.out.println("Index: " + index + " Celda: " + celda);
    }

    //GETTER & SETTER
    public List<Soausentismos> getListaAusentismos() {
        if (listaAusentismos == null) {
            listaAusentismos = administrarAusentismos.ausentismosEmpleado(secuenciaEmpleado);
        }
        return listaAusentismos;
    }

    public void setListaAusentismos(List<Soausentismos> listaAusentismos) {
        this.listaAusentismos = listaAusentismos;
    }

    public List<Soausentismos> getFiltradosListaAusentismos() {
        return filtradosListaAusentismos;
    }

    public void setFiltradosListaAusentismos(List<Soausentismos> filtradosListaAusentismos) {
        this.filtradosListaAusentismos = filtradosListaAusentismos;
    }

    public List<Empleados> getListaEmpleadosAusentismo() {
        if (listaEmpleadosAusentismo == null) {
            listaEmpleadosAusentismo = administrarAusentismos.lovEmpleados();
            seleccionMostrar = listaEmpleadosAusentismo.get(1);
            System.out.println(seleccionMostrar.getSecuencia());
        }
        return listaEmpleadosAusentismo;
    }

    public void setListaEmpleadosAusentismo(List<Empleados> listaEmpleadosAusentismo) {
        this.listaEmpleadosAusentismo = listaEmpleadosAusentismo;
    }

    public List<Empleados> getFiltradosListaEmpleadosAusentismo() {
        return filtradosListaEmpleadosAusentismo;
    }

    public void setFiltradosListaEmpleadosAusentismo(List<Empleados> filtradosListaEmpleadosAusentismo) {
        this.filtradosListaEmpleadosAusentismo = filtradosListaEmpleadosAusentismo;
    }

    public Empleados getSeleccionMostrar() {
        return seleccionMostrar;
    }

    public void setSeleccionMostrar(Empleados seleccionMostrar) {
        this.seleccionMostrar = seleccionMostrar;
    }

    public List<Empleados> getListaEmpleados() {
        if (listaEmpleados == null) {
            listaEmpleados = administrarAusentismos.lovEmpleados();
        }
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleados> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public List<Empleados> getFiltradoslistaEmpleados() {
        return filtradoslistaEmpleados;
    }

    public void setFiltradoslistaEmpleados(List<Empleados> filtradoslistaEmpleados) {
        this.filtradoslistaEmpleados = filtradoslistaEmpleados;
    }

    public Empleados getSeleccionEmpleados() {
        return seleccionEmpleados;
    }

    public void setSeleccionEmpleados(Empleados seleccionEmpleados) {
        this.seleccionEmpleados = seleccionEmpleados;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<Tiposausentismos> getListaTiposAusentismos() {
        if (listaTiposAusentismos == null) {
            listaTiposAusentismos = administrarAusentismos.lovTiposAusentismos();
        }
        return listaTiposAusentismos;
    }

    public void setListaTiposAusentismos(List<Tiposausentismos> listaTiposAusentismos) {
        this.listaTiposAusentismos = listaTiposAusentismos;
    }

    public List<Tiposausentismos> getFiltradoslistaTiposAusentismos() {
        return filtradoslistaTiposAusentismos;
    }

    public void setFiltradoslistaTiposAusentismos(List<Tiposausentismos> filtradoslistaTiposAusentismos) {
        this.filtradoslistaTiposAusentismos = filtradoslistaTiposAusentismos;
    }

    public List<Clasesausentismos> getListaClasesAusentismos() {
        if (listaClasesAusentismos == null) {
            listaClasesAusentismos = administrarAusentismos.lovClasesAusentismos();
        }
        return listaClasesAusentismos;
    }

    public void setListaClasesAusentismos(List<Clasesausentismos> listaClasesAusentismos) {
        this.listaClasesAusentismos = listaClasesAusentismos;
    }

    public List<Clasesausentismos> getFiltradoslistaClasesAusentismos() {
        return filtradoslistaClasesAusentismos;
    }

    public void setFiltradoslistaClasesAusentismos(List<Clasesausentismos> filtradoslistaClasesAusentismos) {
        this.filtradoslistaClasesAusentismos = filtradoslistaClasesAusentismos;
    }

    public List<Causasausentismos> getListaCausasAusentismos() {
        if (listaCausasAusentismos == null) {
            listaCausasAusentismos = administrarAusentismos.lovCausasAusentismos();
        }
        return listaCausasAusentismos;
    }

    public void setListaCausasAusentismos(List<Causasausentismos> listaCausasAusentismos) {
        this.listaCausasAusentismos = listaCausasAusentismos;
    }

    public List<Causasausentismos> getFiltradoslistaCausasAusentismos() {
        return filtradoslistaCausasAusentismos;
    }

    public void setFiltradoslistaCausasAusentismos(List<Causasausentismos> filtradoslistaCausasAusentismos) {
        this.filtradoslistaCausasAusentismos = filtradoslistaCausasAusentismos;
    }

    public List<String> getListaPorcentaje() {
        return listaPorcentaje;
    }

    public void setListaPorcentaje(List<String> listaPorcentaje) {
        this.listaPorcentaje = listaPorcentaje;
    }

    public List<String> getFiltradosListaPorcentajes() {
        return filtradosListaPorcentajes;
    }

    public void setFiltradosListaPorcentajes(List<String> filtradosListaPorcentajes) {
        this.filtradosListaPorcentajes = filtradosListaPorcentajes;
    }

    public String getSeleccionPorcentajes() {
        return seleccionPorcentajes;
    }

    public void setSeleccionPorcentajes(String seleccionPorcentajes) {
        this.seleccionPorcentajes = seleccionPorcentajes;
    }

    public BigInteger getSecuenciaEmpleado() {
        return secuenciaEmpleado;
    }

    public void setSecuenciaEmpleado(BigInteger secuenciaEmpleado) {
        this.secuenciaEmpleado = secuenciaEmpleado;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public List<Ibcs> getListaIBCS() {
        if (listaIBCS == null) {
            listaIBCS = administrarAusentismos.empleadosIBCS(secuenciaEmpleado);
        }
        return listaIBCS;
    }

    public void setListaIBCS(List<Ibcs> listaIBCS) {
        this.listaIBCS = listaIBCS;
    }

    public List<Ibcs> getFiltradosListaIBCS() {
        return filtradosListaIBCS;
    }

    public void setFiltradosListaIBCS(List<Ibcs> filtradosListaIBCS) {
        this.filtradosListaIBCS = filtradosListaIBCS;
    }

    public Ibcs getSeleccionIBCS() {
        return seleccionIBCS;
    }

    public void setSeleccionIBCS(Ibcs seleccionIBCS) {
        this.seleccionIBCS = seleccionIBCS;
    }

    public List<String> getListaForma() {
        return listaForma;
    }

    public void setListaForma(List<String> listaForma) {
        this.listaForma = listaForma;
    }

    public List<String> getFiltradosListaForma() {
        return filtradosListaForma;
    }

    public void setFiltradosListaForma(List<String> filtradosListaForma) {
        this.filtradosListaForma = filtradosListaForma;
    }

    public String getSeleccionForma() {
        return seleccionForma;
    }

    public void setSeleccionForma(String seleccionForma) {
        this.seleccionForma = seleccionForma;
    }

    public List<Soaccidentes> getListaAccidentes() {
        if (listaAccidentes == null) {
            listaAccidentes = administrarAusentismos.lovAccidentes(secuenciaEmpleado);
        }
        return listaAccidentes;
    }

    public void setListaAccidentes(List<Soaccidentes> listaAccidentes) {
        this.listaAccidentes = listaAccidentes;
    }

    public List<Soaccidentes> getFiltradoslistaAccidentes() {
        return filtradoslistaAccidentes;
    }

    public void setFiltradoslistaAccidentes(List<Soaccidentes> filtradoslistaAccidentes) {
        this.filtradoslistaAccidentes = filtradoslistaAccidentes;
    }

    public Soaccidentes getSeleccionAccidentes() {
        return seleccionAccidentes;
    }

    public void setSeleccionAccidentes(Soaccidentes seleccionAccidentes) {
        this.seleccionAccidentes = seleccionAccidentes;
    }

    public List<Enfermedades> getListaEnfermedades() {
        if (listaEnfermedades == null) {
            //Falta Enfermedades
        }
        return listaEnfermedades;
    }

    public void setListaEnfermedades(List<Enfermedades> listaEnfermedades) {
        this.listaEnfermedades = listaEnfermedades;
    }

    public List<Enfermedades> getFiltradoslistaEnfermedades() {
        return filtradoslistaEnfermedades;
    }

    public void setFiltradoslistaEnfermedades(List<Enfermedades> filtradoslistaEnfermedades) {
        this.filtradoslistaEnfermedades = filtradoslistaEnfermedades;
    }

    public Enfermedades getSeleccionEnfermedades() {
        return seleccionEnfermedades;
    }

    public void setSeleccionEnfermedades(Enfermedades seleccionEnfermedades) {
        this.seleccionEnfermedades = seleccionEnfermedades;
    }

    public List<Diagnosticoscategorias> getListaDiagnosticos() {
        if (listaDiagnosticos == null) {
            listaDiagnosticos = administrarAusentismos.lovDiagnosticos();
        }
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<Diagnosticoscategorias> listaDiagnosticos) {
        this.listaDiagnosticos = listaDiagnosticos;
    }

    public List<Diagnosticoscategorias> getFiltradoslistaDiagnosticos() {
        return filtradoslistaDiagnosticos;
    }

    public void setFiltradoslistaDiagnosticos(List<Diagnosticoscategorias> filtradoslistaDiagnosticos) {
        this.filtradoslistaDiagnosticos = filtradoslistaDiagnosticos;
    }

    public Diagnosticoscategorias getSeleccionDiagnosticos() {
        return seleccionDiagnosticos;
    }

    public void setSeleccionDiagnosticos(Diagnosticoscategorias seleccionDiagnosticos) {
        this.seleccionDiagnosticos = seleccionDiagnosticos;
    }

    public List<Terceros> getListaTerceros() {
        if (listaTerceros == null) {
            listaTerceros = administrarAusentismos.lovTerceros();
        }
        return listaTerceros;
    }

    public void setListaTerceros(List<Terceros> listaTerceros) {
        this.listaTerceros = listaTerceros;
    }

    public List<Terceros> getFiltradoslistaTerceros() {
        return filtradoslistaTerceros;
    }

    public void setFiltradoslistaTerceros(List<Terceros> filtradoslistaTerceros) {
        this.filtradoslistaTerceros = filtradoslistaTerceros;
    }

    public Terceros getSeleccionTerceros() {
        return seleccionTerceros;
    }

    public void setSeleccionTerceros(Terceros seleccionTerceros) {
        this.seleccionTerceros = seleccionTerceros;
    }
}
