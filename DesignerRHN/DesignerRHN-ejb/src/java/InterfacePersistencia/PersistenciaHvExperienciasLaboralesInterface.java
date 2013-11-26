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

    public List<HvExperienciasLaborales> experienciasLaboralesSecuenciaEmpleado(BigInteger secuencia);

    public void crear(HvExperienciasLaborales experienciasLaborales);

    public void editar(HvExperienciasLaborales experienciasLaborales);

    public void borrar(HvExperienciasLaborales experienciasLaborales);

    public HvExperienciasLaborales buscarHvExperienciaLaboral(BigInteger secuencia);
}
