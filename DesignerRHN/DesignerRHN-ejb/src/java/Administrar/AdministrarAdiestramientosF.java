/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;


import Entidades.AdiestramientosF;
import InterfaceAdministrar.AdministrarAdiestramientosFInterface;
import InterfacePersistencia.PersistenciaAdiestramientosFInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarAdiestramientosF implements AdministrarAdiestramientosFInterface {
    @EJB
    PersistenciaAdiestramientosFInterface persistenciaAdiestramientosF;
    
    @Override
    public List<AdiestramientosF> AdiestramientoF(){
        List<AdiestramientosF> listaAdiestramientosF;
        listaAdiestramientosF = persistenciaAdiestramientosF.adiestramientosF();
        return listaAdiestramientosF;
    }

    @Override
    public List<AdiestramientosF>  lovAdiestramientosF(){
        return persistenciaAdiestramientosF.adiestramientosF();
    }
}


