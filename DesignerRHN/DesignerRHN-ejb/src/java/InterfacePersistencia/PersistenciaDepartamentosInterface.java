/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Departamentos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'Departamentos' de la base de datos.
 *
 * @author betelgeuse
 */
public interface PersistenciaDepartamentosInterface {

    public void crear(Departamentos departamentos);

    public void editar(Departamentos departamentos);

    public void borrar(Departamentos departamentos);

    public Departamentos consultarDepartamento(BigInteger secuencia);

    public List<Departamentos> consultarDepartamentos();

    public BigInteger contarSoAccidentesMedicosDepartamento(BigInteger secuencia);

    public BigInteger contarCiudadesDepartamento(BigInteger secuencia);

    public BigInteger contarCapModulosDepartamento(BigInteger secuencia);

    public BigInteger contarBienProgramacionesDepartamento(BigInteger secuencia);
}
