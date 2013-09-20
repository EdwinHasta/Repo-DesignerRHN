/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Comprobantes;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaComprobantesInterface {
    public void crear(Comprobantes comprobante);
    public void editar(Comprobantes comprobante);
    public void borrar(Comprobantes comprobante);
    public Comprobantes buscarComprobante(Object id);
    public List<Comprobantes> buscarComprobantes();
    public List<Comprobantes> comprobantesEmpleado(BigInteger secuenciaEmpleado);
    public BigInteger numeroMaximoComprobante();
}
