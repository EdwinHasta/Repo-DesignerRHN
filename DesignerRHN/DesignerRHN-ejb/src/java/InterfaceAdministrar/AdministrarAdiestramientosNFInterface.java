/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.AdiestramientosNF;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pantalla 'AdiestramientosNF'.
 * @author betelgeuse
 */
public interface AdministrarAdiestramientosNFInterface {
    /**
     * Método encargado de recuperar todos los AdiestramientosNF.
     * @return Retorna una lista de AdiestramientosNF.
     */
    public List<AdiestramientosNF> adiestramientosNF();
}
