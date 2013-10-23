/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.MetodosPagos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaMetodosPagosInterface {

    public void crear(MetodosPagos metodosPagos);

    public void editar(MetodosPagos metodosPagos);

    public void borrar(MetodosPagos metodosPagos);

    public MetodosPagos buscarMetodosPagosEmpleado(BigInteger secuencia);

    public List<MetodosPagos> buscarMetodosPagos();
}
