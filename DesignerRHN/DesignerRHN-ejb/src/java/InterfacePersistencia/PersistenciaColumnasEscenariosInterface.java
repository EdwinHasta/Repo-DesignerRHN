/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import ClasesAyuda.ColumnasBusquedaAvanzada;
import Entidades.ColumnasEscenarios;
import Entidades.Empleados;
import Entidades.QVWEmpleadosCorte;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface PersistenciaColumnasEscenariosInterface {
    
    public List<ColumnasEscenarios> buscarColumnasEscenarios();
    public List<ColumnasBusquedaAvanzada> buscarQVWEmpleadosCorteCodigoEmpleado(List<Empleados> listaEmpleadosResultados, List<String> campos);
    public List<QVWEmpleadosCorte> buscarQVWEmpleadosCorteCodigoEmpleadoCodigo(List<BigInteger> listaEmpleadosResultados, String campos);
    
}
