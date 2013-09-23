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
    private Telefonos t;

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
            
            
            persistenciaTelefonos.editar(t);
        }
    }

    @Override
    public void borrarTelefono(Telefonos telefonos) {
        persistenciaTelefonos.borrar(telefonos);
    }


    @Override
    public void crearTelefono(Telefonos telefonos) {
        persistenciaTelefonos.crear(telefonos);
    }

    
}
