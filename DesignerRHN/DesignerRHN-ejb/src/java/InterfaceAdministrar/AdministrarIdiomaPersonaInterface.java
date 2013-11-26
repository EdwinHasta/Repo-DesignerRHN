/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Idiomas;
import Entidades.IdiomasPersonas;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarIdiomaPersonaInterface {

    public void crearIdiomasPersonas(List<IdiomasPersonas> listaID);

    public void borrarIdiomasPersonas(List<IdiomasPersonas> listaID);

    public void editarIdiomasPersonas(List<IdiomasPersonas> listaID);

    public List<IdiomasPersonas> listIdiomasPersonas();

    public List<Idiomas> listIdiomas();
}
