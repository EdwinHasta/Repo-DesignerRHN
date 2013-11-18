/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Mvrs;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaMvrsInterface {
    
    public void crear(Mvrs mvrs);

    public void editar(Mvrs mvrs);

    public void borrar(Mvrs mvrs);

    public Mvrs buscarMvr(Object id);

    public List<Mvrs> buscarMvrs();

    public Mvrs buscarMvrSecuencia(BigInteger secuencia);

    public List<Mvrs> buscarMvrsEmpleado(BigInteger secuencia);
    
}
