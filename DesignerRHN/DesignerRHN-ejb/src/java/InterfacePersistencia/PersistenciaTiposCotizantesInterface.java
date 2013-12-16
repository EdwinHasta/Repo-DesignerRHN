/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.TiposCotizantes;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'TiposCotizantes' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaTiposCotizantesInterface {
    /**
     * Método encargado de buscar todos los TiposCotizantes existentes en la base de datos, ordenados por código.
     * @return Retorna una lista de TiposCotizantes ordenados por código.
     */
    public List<TiposCotizantes> lovTiposCotizantes();
}
