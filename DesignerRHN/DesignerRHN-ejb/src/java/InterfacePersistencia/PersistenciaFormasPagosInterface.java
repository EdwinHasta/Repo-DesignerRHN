/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.FormasPagos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaFormasPagosInterface {

    public void crear(FormasPagos formasPagos);

    public void editar(FormasPagos formasPagos);

    public void borrar(FormasPagos formasPagos);

    public FormasPagos buscarFormasPagos(BigInteger secuencia);

    public List<FormasPagos> buscarFormasPagosPorEmpleado(BigInteger secEmpleado);

    public List<FormasPagos> buscarFormasPagos();
}
