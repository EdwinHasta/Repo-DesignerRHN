/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Comprobantes;
import Entidades.CortesProcesos;
import Entidades.Empleados;
import Entidades.SolucionesNodos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarComprobantesInterface {
    public Empleados buscarEmpleado(BigInteger secuencia);
    public List<Comprobantes> comprobantesEmpleado(BigInteger secuenciaEmpleado);
    public List<CortesProcesos> cortesProcesosComprobante(BigInteger secuenciaComprobante);
    public List<SolucionesNodos> solucionesNodosCorteProcesoEmpleado(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado);
    public List<SolucionesNodos> solucionesNodosCorteProcesoEmpleador(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado);
}
