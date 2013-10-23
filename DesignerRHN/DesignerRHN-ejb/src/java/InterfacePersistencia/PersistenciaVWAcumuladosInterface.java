/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VWAcumulados;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaVWAcumuladosInterface {
        public List<VWAcumulados> buscarVigenciasNormasEmpleadosPorEmpleado (BigInteger secEmpleado);

}
