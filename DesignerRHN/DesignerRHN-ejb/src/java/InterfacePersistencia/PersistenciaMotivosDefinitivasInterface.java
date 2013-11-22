/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Motivosdefinitivas;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaMotivosDefinitivasInterface {

    public void crear(Motivosdefinitivas motivosDefinitivas);

    public void editar(Motivosdefinitivas motivosDefinitivas);

    public void borrar(Motivosdefinitivas motivosDefinitivas);

    public List<Motivosdefinitivas> buscarMotivosDefinitivas();
}
