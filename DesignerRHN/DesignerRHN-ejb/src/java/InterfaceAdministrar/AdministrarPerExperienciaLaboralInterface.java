/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.HvExperienciasLaborales;
import Entidades.MotivosRetiros;
import Entidades.SectoresEconomicos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarPerExperienciaLaboralInterface {

    public Empleados empleadoActual(BigInteger secuencia);

    public List<SectoresEconomicos> listSectoresEconomicos();

    public List<MotivosRetiros> listMotivosRetiros();

    public void crearExperienciaLaboral(List<HvExperienciasLaborales> listHEL);

    public void editarExperienciaLaboral(List<HvExperienciasLaborales> listHEL);

    public void borrarExperienciaLaboral(List<HvExperienciasLaborales> listHEL);

    public List<HvExperienciasLaborales> listExperienciasLaboralesSecuenciaEmpleado(BigInteger secuencia);
}
