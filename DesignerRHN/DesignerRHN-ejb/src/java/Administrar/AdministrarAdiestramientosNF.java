/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.AdiestramientosNF;
import InterfaceAdministrar.AdministrarAdiestramientosNFInterface;
import InterfacePersistencia.PersistenciaAdiestramientosNFInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful

public class AdministrarAdiestramientosNF implements AdministrarAdiestramientosNFInterface{
    
    @EJB
    PersistenciaAdiestramientosNFInterface persistenciaAdiestramientosNF;
    
    @Override
    public List<AdiestramientosNF> AdiestramientosNF(){
        List<AdiestramientosNF> listaAdiestramientosNF;
        listaAdiestramientosNF = persistenciaAdiestramientosNF.adiestramientosNF();
        return listaAdiestramientosNF;
    }

    @Override
    public List<AdiestramientosNF>  lovAdiestramientosNF(){
        return persistenciaAdiestramientosNF.adiestramientosNF();
    }
    
}
