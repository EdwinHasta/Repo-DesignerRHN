package InterfacePersistencia;

import Entidades.ParametrosInformes;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaParametrosInformesInterface {
    
    /**
     * 
     * @param parametrosInformes 
     */
    public void crear(ParametrosInformes parametrosInformes);
    /**
     * 
     * @param parametrosInformes 
     */
    public void editar(ParametrosInformes parametrosInformes);
    /**
     * 
     * @param parametrosInformes 
     */
    public void borrar(ParametrosInformes parametrosInformes);
    /**
     * 
     * @param id
     * @return 
     */
    public ParametrosInformes buscarParametroInforme(Object id);
    /**
     * 
     * @return 
     */
    public List<ParametrosInformes> buscarParametrosInformes();
    /**
     * 
     * @param alias
     * @return 
     */
    public ParametrosInformes buscarParametroInformeUsuario(String alias);
    
}
