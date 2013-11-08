/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Clasesausentismos;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaClasesAusentismosInterface {

    public void crear(Clasesausentismos clasesAusentismos);

    public void editar(Clasesausentismos clasesAusentismos);

    public void borrar(Clasesausentismos clasesAusentismos);

    public List<Clasesausentismos> buscarClasesAusentismos();
}
