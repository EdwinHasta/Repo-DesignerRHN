/**
 * Documentación a cargo de Andres Pineda
 */
package Administrar;

import Entidades.Categorias;
import Entidades.Escalafones;
import Entidades.SubCategorias;
import InterfaceAdministrar.AdministrarEscalafonesInterface;
import InterfacePersistencia.PersistenciaCategoriasInterface;
import InterfacePersistencia.PersistenciaEscalafonesInterface;
import InterfacePersistencia.PersistenciaSubCategoriasInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'Escalafon'.
 *
 * @author AndresPineda
 */
@Stateless
public class AdministrarEscalafones implements AdministrarEscalafonesInterface{

    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEscalafones'.
     */
    @EJB
    PersistenciaEscalafonesInterface persistenciaEscalafones;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaCategorias'.
     */
    @EJB
    PersistenciaCategoriasInterface persistenciaCategorias;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaSubCategorias'.
     */
    @EJB
    PersistenciaSubCategoriasInterface persistenciaSubCategorias;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------    
    @Override
    public List<Escalafones> listaEscalafones() {
        try {
            List<Escalafones> lista = persistenciaEscalafones.buscarEscalafones();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaEscalafones Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearEscalafones(List<Escalafones> listaE) {
        try {
            for (int i = 0; i < listaE.size(); i++) {
                persistenciaEscalafones.crear(listaE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearEscalafones Admi : " + e.toString());
        }
    }

    @Override
    public void editarEscalafones(List<Escalafones> listaE) {
        try {
            for (int i = 0; i < listaE.size(); i++) {
                persistenciaEscalafones.editar(listaE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarEscalafones Admi : " + e.toString());
        }
    }

    @Override
    public void borrarEscalafones(List<Escalafones> listaE) {
        try {
            for (int i = 0; i < listaE.size(); i++) {
                persistenciaEscalafones.borrar(listaE.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarEscalafones Admi : " + e.toString());
        }
    }

    @Override
    public List<Categorias> lovCategorias() {
        try {
            List<Categorias> lista = persistenciaCategorias.buscarCategorias();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovCategorias Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SubCategorias> lovSubCategorias() {
        try {
            List<SubCategorias> lista = persistenciaSubCategorias.consultarSubCategorias();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovSubCategorias Admi : " + e.toString());
            return null;
        }
    }
}
