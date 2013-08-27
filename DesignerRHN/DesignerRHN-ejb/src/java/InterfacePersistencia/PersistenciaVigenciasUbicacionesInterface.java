/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasUbicaciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasUbicacionesInterface {
    public void crear(VigenciasUbicaciones vigenciaUbicacion);
    public void editar(VigenciasUbicaciones vigenciaUbicacion);
    public void borrar(VigenciasUbicaciones vigenciaUbicacion);
    public VigenciasUbicaciones buscarVigenciaUbicacion(Object id);
    public List<VigenciasUbicaciones> buscarVigenciasUbicaciones();
    public List<VigenciasUbicaciones> buscarVigenciaUbicacionesEmpleado(BigInteger secEmpleado);
}
