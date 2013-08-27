package InterfacePersistencia;

import Entidades.TiposDescansos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaTiposDescansosInterface {
    
    /**
     * Crea un nuevo TiposDescansos
     * @param tiposDescansos Objeto a crear
     */
    public void crear(TiposDescansos tiposDescansos);
    /**
     * Edita un TiposDescansos
     * @param tiposDescansos Objeto a editar
     */
    public void editar(TiposDescansos tiposDescansos);
    /**
     * Borra un TiposDescansos
     * @param tiposDescansos Objeto a borrar
     */
    public void borrar(TiposDescansos tiposDescansos);
    /**
     * Busca un TiposDescansos por su llave primaria
     * @param id Llave Primaria ID
     * @return TiposDescansos que cumple con el ID
     */
    public TiposDescansos buscarTipoDescanso(Object id);
    /**
     * Obtien la lista de TiposDescansos
     * @return Lista de TiposDescansos
     */
    public List<TiposDescansos> buscarTiposDescansos();
    /**
     * Obtiene una TiposDescansos por su secuencia
     * @param secuencia Secuencia de TiposDescansos
     * @return TiposDescansos que cumple con la secuencia
     */
    public TiposDescansos buscarTiposDescansosSecuencia(BigInteger secuencia);
    
}
