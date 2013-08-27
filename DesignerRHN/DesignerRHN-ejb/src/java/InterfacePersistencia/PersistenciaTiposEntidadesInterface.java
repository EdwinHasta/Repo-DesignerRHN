package InterfacePersistencia;

import Entidades.TiposEntidades;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaTiposEntidadesInterface {
    
    /**
     * Crea una nueva TiposEntidades
     * @param tiposEntidades Objeto a crear
     */
    public void crear(TiposEntidades tiposEntidades);
    /**
     * Edita un TiposEntidades
     * @param tiposEntidades Objeto a editar
     */
    public void editar(TiposEntidades tiposEntidades);
    /**
     * Borra un TiposEntidades
     * @param tiposEntidades Objeto a borrar
     */
    public void borrar(TiposEntidades tiposEntidades);
    /**
     * Obtiene un TiposEntidades por su llave primaria Id
     * @param id Llave Primaria ID
     * @return TiposEntidades que cumple con la llave primaria
     */
    public TiposEntidades buscarTipoEntidad(Object id);
    /**
     * Obtiene la lista total de TiposEntidades
     * @return Lista de TiposEntidades
     */
    public List<TiposEntidades> buscarTiposEntidades();
    /**
     * Obtiene un TiposEntidades por su secuencia
     * @param secuencia Secuencia TiposEntidades
     * @return TiposEntidades que cumple con la secuencia
     */
    public TiposEntidades buscarTiposEntidadesSecuencia(BigInteger secuencia);
    
}
