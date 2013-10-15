/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Encargaturas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaEncargaturasInterface {

    public List<Encargaturas> reemplazoPersona(BigInteger secuenciaEmpleado);

    public void crear(Encargaturas encargaturas);

    public void editar(Encargaturas encargaturas);

    public void borrar(Encargaturas encargaturas);

    public List<Encargaturas> buscarEncargaturas();

    public List<Encargaturas> encargaturasEmpleado(BigInteger secuenciaEmpleado);
    
}
