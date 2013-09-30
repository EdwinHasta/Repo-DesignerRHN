/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.EstadosAfiliaciones;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaEstadosAfiliacionesInterface {

    public void crear(EstadosAfiliaciones afiliaciones);

    public void editar(EstadosAfiliaciones afiliaciones);

    public void borrar(EstadosAfiliaciones afiliaciones);

    public EstadosAfiliaciones buscarEstadoAfiliacion(Object id);

    public List<EstadosAfiliaciones> buscarEstadosAfiliaciones();
}
