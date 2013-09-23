/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Unidades;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaUnidadesInterface {
    public void crear(Unidades unidad);
    public void editar(Unidades unidad);
    public void borrar(Unidades unidad);
    public List<Unidades> lovUnidades();
}
