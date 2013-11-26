/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposIndicadores;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaTiposIndicadoresInterface {
    
    public void crear(TiposIndicadores tiposIndicadores);
    public void editar(TiposIndicadores tiposIndicadores);
    public void borrar(TiposIndicadores tiposIndicadores);
    public TiposIndicadores buscarTipoIndicador(Object id);
    public List<TiposIndicadores> buscarTiposIndicadores();
    public TiposIndicadores buscarTiposIndicadoresSecuencia(BigInteger secuencia);
    
}
