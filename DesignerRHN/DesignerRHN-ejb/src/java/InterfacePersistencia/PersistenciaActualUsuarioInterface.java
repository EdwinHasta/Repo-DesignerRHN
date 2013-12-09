/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.ActualUsuario;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrator
 */
public interface PersistenciaActualUsuarioInterface {
    public ActualUsuario actualUsuarioBD();
    public String actualAliasBD();
    public String actualAliasBD_EM(EntityManager emg);
}
