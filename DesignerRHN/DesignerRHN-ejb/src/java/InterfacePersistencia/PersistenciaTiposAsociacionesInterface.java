/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposAsociaciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaTiposAsociacionesInterface {
    
    public void crear(TiposAsociaciones asociaciones);
    public void editar(TiposAsociaciones asociaciones);
    public void borrar(TiposAsociaciones asociaciones);
    public TiposAsociaciones buscarTipoAsociacion(Object id);
    public List<TiposAsociaciones> buscarTiposAsociaciones();
    public TiposAsociaciones buscarTiposAsociacionesSecuencia(BigInteger secuencia);
    
}
