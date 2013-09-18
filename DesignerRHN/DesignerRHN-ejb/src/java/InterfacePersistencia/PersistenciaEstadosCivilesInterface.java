/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.EstadosCiviles;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaEstadosCivilesInterface {
    
    public void crear(EstadosCiviles estadosCiviles);
    public void editar(EstadosCiviles estadosCiviles);
    public void borrar(EstadosCiviles estadosCiviles);
    public EstadosCiviles buscarEstadoCivil(Object id);
    public List<EstadosCiviles> buscarEstadosCiviles();
    
}
