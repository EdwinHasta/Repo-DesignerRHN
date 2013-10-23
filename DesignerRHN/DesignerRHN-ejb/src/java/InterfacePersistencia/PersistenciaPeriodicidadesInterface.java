/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Periodicidades;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaPeriodicidadesInterface {
    
    public void crear(Periodicidades periodicidades);
    
    public void editar(Periodicidades periodicidades);
    
    public void borrar(Periodicidades periodicidades);

    public boolean verificarCodigoPeriodicidad(BigInteger codigoPeriodicidad);

    public Periodicidades buscarPeriodicidades(BigInteger secuencia);

    public List<Periodicidades> buscarPeriodicidades();
}
