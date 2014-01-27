/**
 * Documentación a cargo de Andres Pineda
 */
package InterfaceAdministrar;

import Entidades.Categorias;
import Entidades.Escalafones;
import Entidades.SubCategorias;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la
 * pantalla 'Escalafon'.
 *
 * @author betelgeuse
 */
public interface AdministrarEscalafonesInterface {

    /**
     * Método encargado de recuperar todos los Escalafones.
     *
     * @return Retorna una lista de Escalafones.
     */
    public List<Escalafones> listaEscalafones();

    /**
     * Método encargado de crear Escalafones.
     *
     * @param listaE Lista de los Escalafones que se van a crear.
     */
    public void crearEscalafones(List<Escalafones> listaE);

    /**
     * Método encargado de editar Escalafones.
     *
     * @param listaE Lista de los Escalafones que se van a modificar.
     */
    public void editarEscalafones(List<Escalafones> listaE);

    /**
     * Método encargado de borrar Escalafones.
     *
     * @param listaE Lista de los Escalafones que se van a eliminar.
     */
    public void borrarEscalafones(List<Escalafones> listaE);

    /**
     * Método encargado de recuperar todos los Categorias.
     *
     * @return Retorna una lista de Categorias.
     */
    public List<Categorias> lovCategorias();

    /**
     * Método encargado de recuperar todos los SubCategorias.
     *
     * @return Retorna una lista de SubCategorias.
     */
    public List<SubCategorias> lovSubCategorias();

}
