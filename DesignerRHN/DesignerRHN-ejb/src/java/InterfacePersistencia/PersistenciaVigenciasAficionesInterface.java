/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasAficiones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasAficionesInterface {

    public List<VigenciasAficiones> aficionesPersona(BigInteger secuenciaPersona);

    public List<VigenciasAficiones> aficionesTotalesSecuenciaPersona(BigInteger secuenciaP);

    public void crear(VigenciasAficiones vigenciasAficiones);

    public void editar(VigenciasAficiones vigenciasAficiones);

    public void borrar(VigenciasAficiones vigenciasAficiones);

    public VigenciasAficiones buscarvigenciaAficion(Object id);
}
