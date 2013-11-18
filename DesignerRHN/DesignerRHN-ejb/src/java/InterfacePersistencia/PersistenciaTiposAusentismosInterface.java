/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Tiposausentismos;
import java.util.List;


public interface PersistenciaTiposAusentismosInterface {
    public void crear(Tiposausentismos tiposAusentismos);
    public void editar(Tiposausentismos tiposAusentismos);
    public void borrar(Tiposausentismos tiposAusentismos);
    public List<Tiposausentismos> buscarTiposAusentismos ();
}
