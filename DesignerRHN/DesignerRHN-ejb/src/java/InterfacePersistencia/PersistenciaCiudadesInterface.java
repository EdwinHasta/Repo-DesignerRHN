/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Ciudades;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaCiudadesInterface {
    public List<Ciudades> ciudades();
    public void crear(Ciudades ciudades);
    public void editar(Ciudades ciudades);
    public void borrar(Ciudades ciudades);
}
