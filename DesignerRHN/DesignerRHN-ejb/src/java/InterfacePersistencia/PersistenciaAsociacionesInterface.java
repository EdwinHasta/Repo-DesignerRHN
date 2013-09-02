/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Asociaciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaAsociacionesInterface {
    
    public void crear(Asociaciones asociaciones);
    public void editar(Asociaciones asociaciones);
    public void borrar(Asociaciones asociaciones);
    public Asociaciones buscarAsociacion(Object id);
    public List<Asociaciones> buscarAsociaciones();
    public Asociaciones buscarAsociacionesSecuencia(BigInteger secuencia);
    
}
