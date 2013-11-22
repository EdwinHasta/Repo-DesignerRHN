/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.NovedadesSistema;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaNovedadesSistemaInterface {
    public void crear(NovedadesSistema novedades);
    public void editar(NovedadesSistema novedades);
    public void borrar(NovedadesSistema novedades);
    public List<NovedadesSistema> novedadesEmpleado(BigInteger secuenciaEmpleado);
    }
