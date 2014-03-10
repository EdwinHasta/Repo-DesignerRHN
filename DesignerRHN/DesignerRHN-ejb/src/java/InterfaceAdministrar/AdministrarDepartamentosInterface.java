/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.Departamentos;
import Entidades.Paises;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la
 * pantalla 'Departamentos'.
 *
 * @author betelgeuse
 */
public interface AdministrarDepartamentosInterface {

    public void modificarDepartamentos(List<Departamentos> listaDepartamentos);

    public void borrarDepartamentos(List<Departamentos> listaDepartamentos);

    public void crearDepartamentos(List<Departamentos> listaDepartamentos);

    public List<Departamentos> consultarDepartamentos();

    public List<Paises> consultarLOVPaises();

    public Departamentos consultarTipoIndicador(BigInteger secMotivoDemanda);

    public BigInteger contarBienProgramacionesDepartamento(BigInteger secuenciaVigenciasIndicadores);

    public BigInteger contarCapModulosDepartamento(BigInteger secuenciaVigenciasIndicadores);

    public BigInteger contarSoAccidentesMedicosDepartamento(BigInteger secuenciaVigenciasIndicadores);

    public BigInteger contarCiudadesDepartamento(BigInteger secuenciaVigenciasIndicadores);
}
