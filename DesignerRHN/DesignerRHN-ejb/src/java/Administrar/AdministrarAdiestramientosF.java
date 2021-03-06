/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;


import Entidades.AdiestramientosF;
import InterfaceAdministrar.AdministrarAdiestramientosFInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaAdiestramientosFInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
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
    /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
        @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    @Override
    public List<AdiestramientosF> consultarAdiestramientosF(){
        return persistenciaAdiestramientosF.adiestramientosF(em);
    }
}


