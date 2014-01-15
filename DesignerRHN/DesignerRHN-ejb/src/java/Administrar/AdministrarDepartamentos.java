/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Departamentos;
import InterfaceAdministrar.AdministrarDepartamentosInterface;
import InterfacePersistencia.PersistenciaDepartamentosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'Departamentos'.
 * @author betelgeuse
 */
@Stateful
public class AdministrarDepartamentos implements AdministrarDepartamentosInterface{
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaDepartamentos'.
     */
    @EJB
    PersistenciaDepartamentosInterface persistenciaDepartamentos;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<Departamentos> consultarDepartamentos(){
        List<Departamentos> listaDepartamentos;
        listaDepartamentos = persistenciaDepartamentos.departamentos();
        return listaDepartamentos;
    }
}
