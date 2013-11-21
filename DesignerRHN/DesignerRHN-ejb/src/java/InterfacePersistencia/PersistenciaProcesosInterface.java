/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Procesos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaProcesosInterface {
    
    public void crear(Procesos procesos);
    public void editar(Procesos procesos);
    public void borrar(Procesos procesos);
    public Procesos buscarProceso(Object id);
    public List<Procesos> buscarProcesos();
    public Procesos buscarProcesosSecuencia(BigInteger secuencia);
    public List<Procesos> lovProcesos();
    public List<Procesos> procesosParametros();
}
