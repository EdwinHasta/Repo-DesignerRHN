/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Soausentismos;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaSoausentismosInterface {
    
    public void crear(Soausentismos soausentismos);
    public void editar(Soausentismos soausentismos);
    public void borrar(Soausentismos soausentismos);
    public List<Soausentismos> ausentismosEmpleado (BigInteger secuenciaEmpleado);
    
}
