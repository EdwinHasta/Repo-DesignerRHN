/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Cursos;
import InterfaceAdministrar.AdministrarCursosInterface;
import InterfacePersistencia.PersistenciaCursosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'Cursos'.
 * @author betelgeuse
 */
@Stateful
public class AdministrarCursos implements AdministrarCursosInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaCursos'.
     */
    @EJB
    PersistenciaCursosInterface persistenciaCursos;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<Cursos> listaCursos(){
        List<Cursos> listaCursos;
        listaCursos = persistenciaCursos.cursos();
        return listaCursos;
    }
}


