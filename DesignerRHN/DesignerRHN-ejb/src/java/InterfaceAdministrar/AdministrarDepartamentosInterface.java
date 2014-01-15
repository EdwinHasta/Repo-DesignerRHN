/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.Departamentos;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pantalla 'Departamentos'.
 * @author betelgeuse
 */
public interface AdministrarDepartamentosInterface {
    /**
     * Método encargado de recuperar todos los Departamentos.
     * @return Retorna una lista de Departamentos.
     */
    public List<Departamentos> consultarDepartamentos();
}
