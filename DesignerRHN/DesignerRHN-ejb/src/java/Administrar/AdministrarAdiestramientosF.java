/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;


import Entidades.AdiestramientosF;
import InterfaceAdministrar.AdministrarAdiestramientosFInterface;
import InterfacePersistencia.PersistenciaAdiestramientosFInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'AdiestramientosF'.
 * @author betelgeuse
 */
@Stateful
public class AdministrarAdiestramientosF implements AdministrarAdiestramientosFInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaAdiestramientosF'.
     */
    @EJB
    PersistenciaAdiestramientosFInterface persistenciaAdiestramientosF;
    
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    
    @Override
    public List<AdiestramientosF> consultarAdiestramientosF(){
        return persistenciaAdiestramientosF.adiestramientosF();
    }
}


