/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Parametros;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaParametrosInterface {

    public List<Parametros> parametrosComprobantes(String usuarioBD);
}
