/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import ClasesAyuda.ColumnasBusquedaAvanzada;
import ClasesAyuda.ParametrosQueryBusquedaAvanzada;
import Entidades.ColumnasEscenarios;
import Entidades.Empleados;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface AdministrarBusquedaAvanzadaInterface {

    public List<Empleados> ejecutarQueryBusquedaAvanzadaPorModulos(String query);

    public String armarQueryModulosBusquedaAvanzada(List<ParametrosQueryBusquedaAvanzada> listaParametro);
    
    public List<ColumnasEscenarios> buscarColumnasEscenarios();
    
    public List<ColumnasBusquedaAvanzada> obtenerQVWEmpleadosCorteParaEmpleado(List<Empleados> listaEmpleadosResultados, List<String> campos);

}
