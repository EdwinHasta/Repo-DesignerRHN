/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Ciudades;
import Entidades.Direcciones;
import Entidades.Personas;
import InterfaceAdministrar.AdministrarDireccionesInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaDireccionesInterface;
import InterfacePersistencia.PersistenciaPersonasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'Direcciones'.
 *
 * @author betelgeuse
 */
@Stateful
public class AdministrarDirecciones implements AdministrarDireccionesInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaPersonas'.
     */
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'PersistenciaCiudades'.
     */
    @EJB
    PersistenciaCiudadesInterface PersistenciaCiudades;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaDirecciones'.
     */
    @EJB
    PersistenciaDireccionesInterface persistenciaDirecciones;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<Direcciones> consultarDireccionesPersona(BigInteger secPersona) {
        try {
            return persistenciaDirecciones.direccionesPersona(secPersona);
        } catch (Exception e) {
            System.err.println("Error AdministrarTelefonos.telefonosPersona " + e);
            return null;
        }
    }

    @Override
    public Personas consultarPersona(BigInteger secPersona) {
        return persistenciaPersonas.buscarPersonaSecuencia(secPersona);
    }

    @Override
    public List<Ciudades> consultarLOVCiudades() {
        return PersistenciaCiudades.ciudades();
    }

    @Override
    public void modificarDirecciones(List<Direcciones> listaDirecciones) {
        Direcciones d;
        for (int i = 0; i < listaDirecciones.size(); i++) {
            System.out.println("Modificando...");
            if (listaDirecciones.get(i).getCiudad().getSecuencia() == null) {
                listaDirecciones.get(i).setCiudad(null);
                d = listaDirecciones.get(i);
            } else {
                d = listaDirecciones.get(i);
            }
            persistenciaDirecciones.editar(d);
        }
    }

    @Override
    public void borrarDirecciones(List<Direcciones> listaDirecciones) {
        for (int i = 0; i < listaDirecciones.size(); i++) {
            System.out.println("Borrando...");
            if (listaDirecciones.get(i).getHipoteca() == null) {
                listaDirecciones.get(i).setHipoteca("N");
            }
            persistenciaDirecciones.borrar(listaDirecciones.get(i));
        }        
    }

    @Override
    public void crearDirecciones(List<Direcciones> listaDirecciones) {
        for (int i = 0; i < listaDirecciones.size(); i++) {
            System.out.println("Borrando...");
            if (listaDirecciones.get(i).getHipoteca() == null) {
                listaDirecciones.get(i).setHipoteca("N");
            }
            persistenciaDirecciones.crear(listaDirecciones.get(i));
        }      
    }
}
