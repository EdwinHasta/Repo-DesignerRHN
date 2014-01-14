/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.Cursos;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pantalla 'Cursos'.
 * @author betelgeuse
 */
public interface AdministrarCursosInterface {
    /**
     * Método encargado de recuperar todos los Cursos.
     * @return Retorna una lista de Cursos.
     */
    public List<Cursos> listaCursos();
}
