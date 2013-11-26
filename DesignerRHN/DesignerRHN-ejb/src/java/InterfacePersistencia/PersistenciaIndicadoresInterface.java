/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Indicadores;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaIndicadoresInterface {
    
    public void crear(Indicadores indicadores);
    public void editar(Indicadores indicadores);
    public void borrar(Indicadores indicadores);
    public Indicadores buscarIndicador(Object id);
    public List<Indicadores> buscarIndicadores();
    public Indicadores buscarIndicadoresSecuencia(BigInteger secuencia);
    
}
