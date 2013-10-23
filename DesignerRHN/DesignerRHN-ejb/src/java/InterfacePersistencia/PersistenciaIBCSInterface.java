/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Ibcs;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaIBCSInterface {

    public void crear(Ibcs vigenciasFormasPagos);

    public void editar(Ibcs vigenciasFormasPagos);

    public void borrar(Ibcs vigenciasFormasPagos);

    public Ibcs buscarIbcs(BigInteger secuencia);

    public List<Ibcs> buscarIbcsPorEmpleado(BigInteger secEmpleado);

    public List<Ibcs> buscarVigenciasFormasPagosEmpleado();
}
