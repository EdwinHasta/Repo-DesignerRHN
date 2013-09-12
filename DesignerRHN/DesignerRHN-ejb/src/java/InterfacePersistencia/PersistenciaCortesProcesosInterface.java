/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.CortesProcesos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaCortesProcesosInterface {
    public void crear(CortesProcesos corteProceso);
    public void editar(CortesProcesos corteProceso);
    public void borrar(CortesProcesos corteProceso);
    public CortesProcesos buscarCorteProceso(Object id);
    public List<CortesProcesos> buscarCortesProcesos();
    public List<CortesProcesos> cortesProcesosComprobante(BigInteger secuenciaComprobante);
}
