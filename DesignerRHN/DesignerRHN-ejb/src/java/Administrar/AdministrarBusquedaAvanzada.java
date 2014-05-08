/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import ClasesAyuda.ColumnasBusquedaAvanzada;
import ClasesAyuda.ParametrosQueryBusquedaAvanzada;
import Entidades.ColumnasEscenarios;
import Entidades.Empleados;
import Entidades.QVWEmpleadosCorte;
import Entidades.ResultadoBusquedaAvanzada;
import InterfaceAdministrar.AdministrarBusquedaAvanzadaInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaColumnasEscenariosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author PROYECTO01
 */
@Stateful
public class AdministrarBusquedaAvanzada implements AdministrarBusquedaAvanzadaInterface {

    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    PersistenciaColumnasEscenariosInterface persistenciaColumnasEscenarios;
	/**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;
    private boolean usoWhere = false;

        @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    
    @Override
    public List<ColumnasEscenarios> buscarColumnasEscenarios() {
        try {
            List<ColumnasEscenarios> lista = persistenciaColumnasEscenarios.buscarColumnasEscenarios(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error buscarColumnasEscenarios Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> ejecutarQueryBusquedaAvanzadaPorModulos(String query) {
        try {
            List<Empleados> lista = persistenciaEmpleado.buscarEmpleadosBusquedaAvanzada(em,query);
            return lista;
        } catch (Exception e) {
            System.out.println("Error ejecutarQueryBusquedaAvanzadaPorModulos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<BigInteger> ejecutarQueryBusquedaAvanzadaPorModulosCodigo(String query) {
        try {
            List<BigInteger> lista = persistenciaEmpleado.buscarEmpleadosBusquedaAvanzadaCodigo(em,query);
            return lista;
        } catch (Exception e) {
            System.out.println("Error ejecutarQueryBusquedaAvanzadaPorModulos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public String armarQueryModulosBusquedaAvanzada(List<ParametrosQueryBusquedaAvanzada> listaParametro) {
        //try {
        usoWhere = false;
        String query = "";
        int tam = listaParametro.size();
        System.out.println("Tamaño Lista Administrar : " + tam);
        if (tam > 0) {
            int i = 0;
            List<String> listaPorModulo = listaParametrosPorModulos(listaParametro);
            while (i < tam) {
                String modulo = listaParametro.get(i).getModulo();
                List<ParametrosQueryBusquedaAvanzada> listaAuxiliar = new ArrayList<ParametrosQueryBusquedaAvanzada>();
                int indiceInicial = i;
                System.out.println("indiceInicial : " + indiceInicial);
                int indiceFinal = listaPorModulo.lastIndexOf(modulo);
                int indiceFinalAux = indiceFinal + 1;
                System.out.println("indiceFinal : " + indiceFinal);
                for (int j = indiceInicial; j < indiceFinalAux; j++) {
                    listaAuxiliar.add(listaParametro.get(j));
                }
                query = query + crearQueryPorModulo(listaAuxiliar);
                int aumentoi = indiceFinal - indiceInicial;
                if (aumentoi == 0) {
                    i++;
                } else {
                    i = i + aumentoi + 1;
                }
            }
        }
        return query;
        //} catch (Exception e) {
        //System.out.println("Error armarQueryModulosBusquedaAvanzada Admi : " + e.toString());
        //return "";
        //}
    }

    private String crearQueryPorModulo(List<ParametrosQueryBusquedaAvanzada> listaAuxiliar) {
        String queryAux = "";
        String modulo = listaAuxiliar.get(0).getModulo();
        System.out.println("Modulo Actual Consulta : " + modulo);
        if (modulo.equalsIgnoreCase("CARGO")) {
            System.out.println("Entro a CARGO");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BCARGO")) {
                    if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VWACTUALESCARGOS V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    } else {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VIGENCIASCARGOS V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("CARGODESDE")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("CARGOHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }

                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("CARGO")) {
                    queryAux = queryAux + " AND  V.CARGO = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("ESTRUCTURA")) {
                    queryAux = queryAux + " AND  V.ESTRUCTURA = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("CENTROCOSTO")) {
                    queryAux = queryAux + " AND  EXISTS (SELECT 1 FROM ESTRUCTURAS ES WHERE ES.SECUENCIA=V.ESTRUCTURA";
                    queryAux = queryAux + " AND ES.CENTROCOSTO=" + listaAuxiliar.get(i).getValorParametro() + ")";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("EMPLEADOJEFE")) {
                    queryAux = queryAux + " AND  V.EMPLEADOJEFE = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("MOTIVOCAMBIOCARGO")) {
                    queryAux = queryAux + " AND  V.MOTIVOCAMBIOCARGO = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("PAPEL")) {
                    queryAux = queryAux + " AND  V.PAPEL = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("CENTROCOSTO")) {
            System.out.println("Entro a CENTROCOSTO");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BCENTROCOSTO")) {
                    if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VWACTUALESLOCALIZACIONES V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    } else {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VIGENCIASLOCALIZACIONES V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("CENTROCOSTODESDE")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("CENTROCOSTOHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }

                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("LOCALIZACION")) {
                    queryAux = queryAux + " AND  V.LOCALIZACION = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("MOTIVOLOCALIZACION")) {
                    queryAux = queryAux + " AND  V.MOTIVO = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("SUELDO")) {
            System.out.println("Entro a SUELDO");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BSUELDO")) {
                    if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VWACTUALESSUELDOS V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    } else {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VIGENCIASSUELDOS V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("SUELDODESDE")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("SUELDOHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }

                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("TIPOSUELDO")) {
                    queryAux = queryAux + " AND  V.TIPOSUELDO = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("MOTIVOCAMBIOSUELDO")) {
                    queryAux = queryAux + " AND  V.MOTIVOCAMBIOSUELDO = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("SUELDOMINIMO")) {
                    queryAux = queryAux + " AND  V.VALOR >= " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("SUELDOMAXIMO")) {
                    queryAux = queryAux + " AND  V.VALOR <= " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("FECHACONTRATO")) {
            System.out.println("Entro a FECHACONTRATO");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BFECHACONTRATO")) {
                    if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VWactualesTiposcontratos V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    } else {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VIGENCIASTIPOSCONTRATOS V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("FECHACONTRATODESDE")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("FECHACONTRATOHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }

                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("TIPOCONTRATO")) {
                    queryAux = queryAux + " AND  V.TIPOCONTRATO = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("MOTIVOCONTRATO")) {
                    queryAux = queryAux + " AND  V.MOTIVOCONTRATO = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("TIPOTRABAJADOR")) {
            System.out.println("Entro a TIPOTRABAJADOR");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BTIPOTRABAJADOR")) {
                    if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VWactualesTiposTrabajadores V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    } else {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VIGENCIASTIPOSTRABAJADORES V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("TIPOTRABAJADORDESDE")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("TIPOTRABAJADORHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }

                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("TIPOTRABAJADOR")) {
                    queryAux = queryAux + " AND  V.TIPOTRABAJADOR = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("TIPOSALARIO")) {
            System.out.println("Entro a TIPOSALARIO");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BTIPOSALARIO")) {
                    if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VWactualesReformasLaborales V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    } else {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VIGENCIASREFORMASLABORALES V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("TIPOSALARIODESDE")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("TIPOSALARIOHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }

                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("REFORMA")) {
                    queryAux = queryAux + " AND  V.REFORMALABORAL = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("NORMALABORAL")) {
            System.out.println("Entro a NORMALABORAL");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BNORMALABORAL")) {
                    if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VWactualesNormasEmpleados V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    } else {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VIGENCIASNORMASEMPLEADOS V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("NORMALABORALDESDE")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("NORMALABORALHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }

                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("NORMA")) {
                    queryAux = queryAux + " AND  V.NORMALABORAL = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("LEGISLACIONLABORAL")) {
            System.out.println("Entro a LEGISLACIONLABORAL");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BLEGISLACIONLABORAL")) {
                    if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VWactualesContratos V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    } else {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VIGENCIASCONTRATOS V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("LEGISLACIONLABORALDESDE")) {
                    queryAux = queryAux + " AND  V.FECHAINICIAL >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("LEGISLACIONLABORALHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAINICIAL <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("LEGISLACIONLABORALDESDEF")) {
                    queryAux = queryAux + " AND  V.FECHAFINAL >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("LEGISLACIONLABORALHASTAF")) {
                    queryAux = queryAux + " AND  V.FECHAFINAL <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("CONTRATO")) {
                    queryAux = queryAux + " AND  V.CONTRATO = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("UBICACION")) {
            System.out.println("Entro a UBICACION");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BUBICACION")) {
                    if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VWACTUALESUBICACIONES V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    } else {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VIGENCIASUBICACIONES V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("UBICACIONDESDE")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("UBICACIONHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("UBICACION")) {
                    queryAux = queryAux + " AND  V.UBICACION = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("AFILIACIONES")) {
            System.out.println("Entro a AFILIACIONES");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            String v_vcRETENCIONYSEGSOCXPERSONA = persistenciaEmpresas.consultarPrimeraEmpresa(em);
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BUBICACION")) {
                    if (v_vcRETENCIONYSEGSOCXPERSONA.equalsIgnoreCase("N")) {
                        if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                            queryAux = queryAux + "EXISTS (SELECT 1 FROM VWACTUALESAFILIACIONES V ";
                            queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                        } else {
                            queryAux = queryAux + "EXISTS (SELECT 1 FROM VIGENCIASAFILIACIONES V ";
                            queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                        }
                    } else {
                        if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                            queryAux = queryAux + "EXISTS (SELECT 1 FROM VWACTUALESAFILIACIONESPERSONA V ";
                            queryAux = queryAux + "WHERE V.PERSONA = EM.PERSONA ";
                        } else {
                            queryAux = queryAux + "EXISTS (SELECT 1 FROM VIGENCIASAFILIACIONESPERSONA V ";
                            queryAux = queryAux + "WHERE V.PERSONA = EM.PERSONA ";
                        }
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("AFILIACIONESDESDE")) {
                    queryAux = queryAux + " AND  V.FECHAINICIAL >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("AFILIACIONESHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAINICIAL <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("TERCERO")) {
                    queryAux = queryAux + " AND  V.TERCERO = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("TIPOENTIDAD")) {
                    queryAux = queryAux + " AND  V.TIPOENTIDAD = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("ESTADO")) {
                    queryAux = queryAux + " AND  V.ESTADOAFILIACION = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("FORMAPAGO")) {
            System.out.println("Entro a FORMAPAGO");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BFORMAPAGO")) {
                    if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VWACTUALESFormasPagos V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    } else {
                        queryAux = queryAux + "EXISTS (SELECT 1 FROM VIGENCIASFORMASPAGOS V ";
                        queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("FORMAPAGODESDE")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("FORMAPAGOHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("FORMAPAGO")) {
                    queryAux = queryAux + " AND  V.FORMAPAGO = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("SUCURSAL")) {
                    queryAux = queryAux + " AND  V.SUCURSAL = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("MVRS")) {
            System.out.println("Entro a MVRS");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            String v_vcRETENCIONYSEGSOCXPERSONA = persistenciaEmpresas.consultarPrimeraEmpresa(em);
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BMVRS")) {
                    if (v_vcRETENCIONYSEGSOCXPERSONA.equalsIgnoreCase("N")) {
                        if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                            queryAux = queryAux + "EXISTS (SELECT 1 FROM VWACTUALESMvrs V ";
                            queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                        } else {
                            queryAux = queryAux + "EXISTS (SELECT 1 FROM MVRS V ";
                            queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                        }
                    } else {
                        if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                            queryAux = queryAux + "EXISTS (SELECT 1 FROM VWACTUALESMvrsPersona V ";
                            queryAux = queryAux + "WHERE V.PERSONA = EM.PERSONA ";
                        } else {
                            queryAux = queryAux + "EXISTS (SELECT 1 FROM MVRSPERSONA V ";
                            queryAux = queryAux + "WHERE V.PERSONA = EM.PERSONA ";
                        }
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("MVRSDESDE")) {
                    queryAux = queryAux + " AND  V.FECHAINICIAL >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("MVRSHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAINICIAL <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("MOTIVO")) {
                    queryAux = queryAux + " AND  V.MOTIVO = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("SUELDOMINIMO")) {
                    queryAux = queryAux + " AND  V.VALOR = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("SUELDOMAXIMO")) {
                    queryAux = queryAux + " AND  V.VALOR = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("SETS")) {
            System.out.println("Entro a SETS");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            String v_vcRETENCIONYSEGSOCXPERSONA = persistenciaEmpresas.consultarPrimeraEmpresa(em);
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("BSETS")) {
                    if (v_vcRETENCIONYSEGSOCXPERSONA.equalsIgnoreCase("N")) {
                        if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                            queryAux = queryAux + "EXISTS (SELECT 1 FROM VWACTUALESsets V ";
                            queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                        } else {
                            queryAux = queryAux + "EXISTS (SELECT 1 FROM sets V ";
                            queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
                        }
                    } else {
                        if (listaAuxiliar.get(i).getValorParametro().equalsIgnoreCase("A")) {
                            queryAux = queryAux + "EXISTS (SELECT 1 FROM VWACTUALESsetsPERSONA V ";
                            queryAux = queryAux + "WHERE V.PERSONA = EM.PERSONA ";
                        } else {
                            queryAux = queryAux + "EXISTS (SELECT 1 FROM setsPERSONA V ";
                            queryAux = queryAux + "WHERE V.PERSONA = EM.PERSONA ";
                        }
                    }
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("SETSDESDE")) {
                    queryAux = queryAux + " AND  V.FECHAINICIAL >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("SETSHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAINICIAL <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("SETSDESDEF")) {
                    queryAux = queryAux + " AND  V.FECHAFINAL >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("SETSHASTAF")) {
                    queryAux = queryAux + " AND  V.FECHAFINAL <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("METODO")) {
                    queryAux = queryAux + " AND  V.TIPOSET = " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("PROMEDIOMINIMO")) {
                    queryAux = queryAux + " AND  V.PROMEDIO >= " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("PROMEDIOMAXIMO")) {
                    queryAux = queryAux + " AND V.PROMEDIO <= " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("VACACIONES")) {
            System.out.println("Entro a VACACIONES");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            queryAux = queryAux + "EXISTS (SELECT 1 FROM NOVEDADESSISTEMA N ";
            queryAux = queryAux + "WHERE N.EMPLEADO = EM.SECUENCIA  AND N.TIPO = 'VACACION' ";
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("FECHASALIDADESDE")) {
                    queryAux = queryAux + " AND  N.FECHAINICIALDISFRUTE >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("FECHASALIDAHASTA")) {
                    queryAux = queryAux + " AND  N.FECHAINICIALDISFRUTE <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("FECHAREGRESODESDE")) {
                    queryAux = queryAux + " AND  N.FECHASIGUIENTEFINVACA >= " + listaAuxiliar.get(i).getValorParametro();
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("FECHAREGRESOHASTA")) {
                    queryAux = queryAux + " AND  N.FECHASIGUIENTEFINVACA <= " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("FECHARETIRO")) {
            System.out.println("Entro a FECHARETIRO");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            queryAux = queryAux + "EXISTS (SELECT 1 FROM RETIRADOS R,vIGENCIASTIPOSTRABAJADORES VI ";
            queryAux = queryAux + "WHERE R.vigenciatipotrabajador = VI.secuencia AND VI.EMPLEADO = EM.SECUENCIA ";
            System.out.println("listaAuxiliar : " + listaAuxiliar.size());
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("FECHARETIRODESDE")) {
                    queryAux = queryAux + " AND  R.FECHARETIRO >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("FECHARETIROHASTA")) {
                    queryAux = queryAux + " AND  R.FECHARETIRO <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("MOTIVO")) {
                    queryAux = queryAux + " AND  R.MOTIVORETIRO = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        if (modulo.equalsIgnoreCase("JORNADALABORAL")) {
            System.out.println("Entro a JORNADALABORAL");
            if (usoWhere == false) {
                queryAux = " WHERE ";
                usoWhere = true;
            } else {
                queryAux = " AND ";
            }
            queryAux = queryAux + "EXISTS (SELECT 1 FROM VWACTUALESJORNADAS V ";
            queryAux = queryAux + "WHERE V.EMPLEADO = EM.SECUENCIA ";
            for (int i = 0; i < listaAuxiliar.size(); i++) {
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("JORNADALABORALDESDE")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA >= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("JORNADALABORALHASTA")) {
                    queryAux = queryAux + " AND  V.FECHAVIGENCIA <= TO_CHAR(" + listaAuxiliar.get(i).getValorParametro() + ",'DD/MM/YYYY')";
                }
                if (listaAuxiliar.get(i).getNombreParametro().equalsIgnoreCase("JORNADA")) {
                    queryAux = queryAux + " AND  V.JORNADATRABAJO = " + listaAuxiliar.get(i).getValorParametro();
                }
            }
            queryAux = queryAux + ")";
        }
        return queryAux;
    }

    private List<String> listaParametrosPorModulos(List<ParametrosQueryBusquedaAvanzada> listaAuxiliar) {
        List<String> retorno = new ArrayList<String>();
        for (int i = 0; i < listaAuxiliar.size(); i++) {
            retorno.add(listaAuxiliar.get(i).getModulo());
        }
        return retorno;
    }

    @Override
    public List<ColumnasBusquedaAvanzada> obtenerQVWEmpleadosCorteParaEmpleado(List<Empleados> listaEmpleadosResultados, List<String> campos) {
        try {
            System.out.println("entro administrar");
            List<ColumnasBusquedaAvanzada> retorno = persistenciaColumnasEscenarios.buscarQVWEmpleadosCorteCodigoEmpleado(em,listaEmpleadosResultados, campos);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error obtenerQVWEmpleadosCorteParaEmpleado Admi : " + e.toString());
            return null;
        }

    }
    
    //@Override 
    public List<ResultadoBusquedaAvanzada> obtenerQVWEmpleadosCorteParaEmpleadoCodigo(List<BigInteger> listaCodigosEmpleados, String campos) {
        try {
            System.out.println("entro administrar");
            List<ResultadoBusquedaAvanzada> retorno = persistenciaColumnasEscenarios.buscarQVWEmpleadosCorteCodigoEmpleadoCodigo(em,listaCodigosEmpleados, campos);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error obtenerQVWEmpleadosCorteParaEmpleado Admi : " + e.toString());
            return null;
        }

    }

}
