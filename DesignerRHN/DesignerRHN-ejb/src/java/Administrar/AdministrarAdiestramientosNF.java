/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.AdiestramientosNF;
import InterfaceAdministrar.AdministrarAdiestramientosNFInterface;
import InterfacePersistencia.PersistenciaAdiestramientosNFInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'AdiestramientosNF'.
 * @author betelgeuse
 */
@Stateful
public class AdministrarAdiestramientosNF implements AdministrarAdiestramientosNFInterface{
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaAdiestramientosNF'.
     */
    @EJB
    PersistenciaAdiestramientosNFInterface persistenciaAdiestramientosNF;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<AdiestramientosNF> consultarAdiestramientosNF(){
        return persistenciaAdiestramientosNF.adiestramientosNF();
    }    
}
