/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Cargos;
import InterfaceAdministrar.AdministrarCargosInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'Cargos'.
 * @author betelgeuse
 */
@Stateful
public class AdministrarCargos implements  AdministrarCargosInterface{
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaCargos'.
     */
    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<Cargos> cargos() {
        return persistenciaCargos.buscarCargos();        
    }
}
