/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.AdiestramientosF;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pantalla 'AdiestramientosF'. 
 * @author betelgeuse
 */
public interface AdministrarAdiestramientosFInterface {    
    /**
     * Método encargado de recuperar todos los AdiestramientosF.
     * @return Retorna una lista de AdiestramientosF.
     */
    public List<AdiestramientosF> consultarAdiestramientosF();
}
