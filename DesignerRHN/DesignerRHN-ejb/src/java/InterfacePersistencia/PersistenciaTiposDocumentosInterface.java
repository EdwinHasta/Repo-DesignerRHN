/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.TiposDocumentos;
import java.util.List;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'TiposDocumentos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaTiposDocumentosInterface {
    /**
     * Método encargado de buscar todos los TiposDocumentos existentes en la base de datos, ordenados por nombreCorto.
     * @return Retorna una lista de TiposDocumentos ordenados por nombreCorto.
     */
    public List<TiposDocumentos> tiposDocumentos();
}
