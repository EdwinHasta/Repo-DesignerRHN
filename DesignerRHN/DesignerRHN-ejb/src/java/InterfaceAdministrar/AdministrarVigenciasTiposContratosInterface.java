/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Ciudades;
import Entidades.Empleados;
import Entidades.MotivosContratos;
import Entidades.TiposContratos;
import Entidades.VigenciasTiposContratos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarVigenciasTiposContratosInterface {

    public List<VigenciasTiposContratos> vigenciasTiposContratosEmpleado(BigInteger secEmpleado);

    public void modificarVTC(List<VigenciasTiposContratos> listVTCModificadas);

    public void borrarVTC(VigenciasTiposContratos vigenciasTipoContrato);

    public void crearVTC(VigenciasTiposContratos vigenciasTipoContrato);

    public Empleados buscarEmpleado(BigInteger secuencia);

    public List<Ciudades> ciudades();

    public List<MotivosContratos> motivosContratos();

    public List<TiposContratos> tiposContratos();
}
