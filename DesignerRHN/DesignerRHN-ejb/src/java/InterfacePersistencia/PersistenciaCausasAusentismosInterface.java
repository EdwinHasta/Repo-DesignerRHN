/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Causasausentismos;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaCausasAusentismosInterface {

    public void crear(Causasausentismos causasAusentismos);

    public void editar(Causasausentismos causasAusentismos);

    public void borrar(Causasausentismos causasAusentismos);

    public List<Causasausentismos> buscarCausasAusentismos();
}
