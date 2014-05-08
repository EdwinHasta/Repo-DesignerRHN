package Administrar;

import Entidades.Ciudades;
import Entidades.Personas;
import Entidades.Telefonos;
import Entidades.TiposTelefonos;
import InterfaceAdministrar.AdministrarTelefonosInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaPersonasInterface;
import InterfacePersistencia.PersistenciaTelefonosInterface;
import InterfacePersistencia.PersistenciaTiposTelefonosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import InterfaceAdministrar.AdministrarSesionesInterface;
import javax.persistence.EntityManager;

@Stateful
public class AdministrarTelefonos implements AdministrarTelefonosInterface{

    @EJB
    PersistenciaTelefonosInterface persistenciaTelefonos;
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;
    @EJB
    PersistenciaTiposTelefonosInterface persistenciaTiposTelefonos;
    @EJB
    PersistenciaCiudadesInterface PersistenciaCiudades;
    /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;
    
    private Telefonos t;
    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    
    @Override
    public List<Telefonos> telefonosPersona(BigInteger secPersona) {
        try {
            return persistenciaTelefonos.telefonosPersona(em, secPersona);
        } catch (Exception e) {
            System.err.println("Error AdministrarTelefonos.telefonosPersona " + e);
            return null;
        }
    }
    
    @Override
    public Personas encontrarPersona(BigInteger secPersona){
        return persistenciaPersonas.buscarPersonaSecuencia(em, secPersona);
    } 
    
    //Lista de Valores TiposTelefonos
    
    @Override
    public List<TiposTelefonos>  lovTiposTelefonos(){
        return persistenciaTiposTelefonos.tiposTelefonos(em);
    }
    
    @Override
    public List<Ciudades>  lovCiudades(){
        return PersistenciaCiudades.ciudades(em);
    }
    
    @Override
    public void modificarTelefono(List<Telefonos> listaTelefonosModificar) {
        for (int i = 0; i < listaTelefonosModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaTelefonosModificar.get(i).getTipotelefono().getSecuencia() == null) {
                listaTelefonosModificar.get(i).setTipotelefono(null);
                t = listaTelefonosModificar.get(i);
            } else {
                t = listaTelefonosModificar.get(i);
            }
            if (listaTelefonosModificar.get(i).getCiudad().getSecuencia() == null) {
                listaTelefonosModificar.get(i).setCiudad(null);
                t = listaTelefonosModificar.get(i);
            } else {
                t = listaTelefonosModificar.get(i);
            }
            
            
            persistenciaTelefonos.editar(em, t);
        }
    }

    @Override
    public void borrarTelefono(Telefonos telefonos) {
        persistenciaTelefonos.borrar(em, telefonos);
    }


    @Override
    public void crearTelefono(Telefonos telefonos) {
        persistenciaTelefonos.crear(em, telefonos);
    }

    
}
