/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Deportes;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaDeportesInterface {
    
    public void crear(Deportes deportes);
    public void editar(Deportes deportes);
    public void borrar(Deportes deportes);
    public Deportes buscarDeporte(Object id);
    public List<Deportes> buscarDeportes();
    
}
