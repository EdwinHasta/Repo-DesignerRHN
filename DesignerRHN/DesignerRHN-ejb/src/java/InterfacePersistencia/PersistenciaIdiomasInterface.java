/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Idiomas;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaIdiomasInterface {
    
    public void crear(Idiomas idiomas);
    public void editar(Idiomas idiomas);
    public void borrar(Idiomas idiomas);
    public Idiomas buscarIdioma(Object id);
    public List<Idiomas> buscarIdiomas();
    
}
