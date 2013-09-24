/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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


@Stateful
public class AdministrarDirecciones implements AdministrarDireccionesInterface{
    
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;
    @EJB
    PersistenciaCiudadesInterface PersistenciaCiudades;
    @EJB
    PersistenciaDireccionesInterface persistenciaDirecciones;
    private Direcciones d;

@Override
    public List<Direcciones> direccionesPersona(BigInteger secPersona) {
        try {
            return persistenciaDirecciones.direccionesPersona(secPersona);
        } catch (Exception e) {
            System.err.println("Error AdministrarTelefonos.telefonosPersona " + e);
            return null;
        }
    }
    
    @Override
    public Personas encontrarPersona(BigInteger secPersona){
        return persistenciaPersonas.buscarPersonaSecuencia(secPersona);
    } 
    
        //Lista de Valores Ciudades

    @Override
    public List<Ciudades>  lovCiudades(){
        return PersistenciaCiudades.ciudades();
    }
    
     @Override
    public void modificarDireccion(List<Direcciones> listaDireccionesModificar) {
        for (int i = 0; i < listaDireccionesModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaDireccionesModificar.get(i).getCiudad().getSecuencia() == null) {
                listaDireccionesModificar.get(i).setCiudad(null);
                d = listaDireccionesModificar.get(i);
            } else {
                d = listaDireccionesModificar.get(i);
            }
            
            
            persistenciaDirecciones.editar(d);
        }
    }

    @Override
    public void borrarDireccion(Direcciones direcciones) {
        persistenciaDirecciones.borrar(direcciones);
    }


    @Override
    public void crearDireccion(Direcciones direcciones) {
        persistenciaDirecciones.crear(direcciones);
    }
    
}
