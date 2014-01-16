/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.Cargos;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pantalla 'Cargos'.
 * @author betelgeuse
 */
public interface AdministrarCargosInterface {
    /**
     * Método encargado de recuperar todos los Cargos.
     * @return Retorna una lista de Cargos.
     */
    public List<Cargos> consultarCargos();   
}
