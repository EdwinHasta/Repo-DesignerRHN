/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Rubrospresupuestales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaRubrosPresupuestalesInterface {

    public void crear(Rubrospresupuestales rubrospresupuestales);

    public void editar(Rubrospresupuestales rubrospresupuestales);

    public void borrar(Rubrospresupuestales rubrospresupuestales);

    public Rubrospresupuestales buscarRubro(Object id);

    public List<Rubrospresupuestales> buscarRubros();

    public Rubrospresupuestales buscarRubrosSecuencia(BigInteger secuencia);
}
