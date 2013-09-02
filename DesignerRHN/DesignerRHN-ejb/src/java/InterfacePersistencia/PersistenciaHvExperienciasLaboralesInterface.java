/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.HvExperienciasLaborales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaHvExperienciasLaboralesInterface {
    public List<HvExperienciasLaborales> experienciaLaboralPersona(BigInteger secuenciaHV);
}
