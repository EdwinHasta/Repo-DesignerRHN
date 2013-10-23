/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasFormasPagos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaVigenciasFormasPagosInterface {

    public void crear(VigenciasFormasPagos vigenciasFormasPagos);

    public void editar(VigenciasFormasPagos vigenciasFormasPagos);

    public void borrar(VigenciasFormasPagos vigenciasFormasPagos);

    public VigenciasFormasPagos buscarVigenciasNormasEmpleado(BigInteger secuencia);

    public List<VigenciasFormasPagos> buscarVigenciasFormasPagosPorEmpleado(BigInteger secEmpleado);

    public List<VigenciasFormasPagos> buscarVigenciasFormasPagosEmpleado();
}
