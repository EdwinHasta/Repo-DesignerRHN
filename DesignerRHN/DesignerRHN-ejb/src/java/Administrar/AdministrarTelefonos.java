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

    @Override
    public List<Telefonos> telefonosPersona(BigInteger secPersona) {
        try {
            return persistenciaTelefonos.telefonosPersona(secPersona);
        } catch (Exception e) {
            System.err.println("Error AdministrarTelefonos.telefonosPersona " + e);
            return null;
        }
    }
    
    @Override
    public Personas encontrarPersona(BigInteger secPersona){
        return persistenciaPersonas.buscarPersonaSecuencia(secPersona);
    } 
    
    //Lista de Valores TiposTelefonos
    
    @Override
    public List<TiposTelefonos>  lovTiposTelefonos(){
        return persistenciaTiposTelefonos.tiposTelefonos();
    }
    
    @Override
    public List<Ciudades>  lovCiudades(){
        return PersistenciaCiudades.ciudades();
    }
}
