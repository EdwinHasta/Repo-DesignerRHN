/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Actividades;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaActividadesInterface {
    
    public void crear(Actividades actividades);
    public void editar(Actividades actividades);
    public void borrar(Actividades actividades);
    public Actividades buscarActividad(Object id);
    public List<Actividades> buscarActividades();
    
}
