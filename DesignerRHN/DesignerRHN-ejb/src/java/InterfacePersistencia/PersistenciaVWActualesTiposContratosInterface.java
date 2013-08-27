/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VWActualesTiposContratos;
import java.math.BigInteger;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVWActualesTiposContratosInterface {
    
        public VWActualesTiposContratos buscarTiposContratosEmpleado(BigInteger secuencia);

}
