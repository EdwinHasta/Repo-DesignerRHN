/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposEducaciones;
import InterfaceAdministrar.AdministrarTiposEducacionesInterface;
import InterfacePersistencia.PersistenciaTiposEducacionesInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;


/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposEducaciones implements AdministrarTiposEducacionesInterface{
    
   @EJB
   PersistenciaTiposEducacionesInterface persistenciaTiposEducaciones;
     
    @Override
    public List<TiposEducaciones> TiposEducaciones(){
        List<TiposEducaciones> listaTiposEducaciones;
        listaTiposEducaciones = persistenciaTiposEducaciones.tiposEducaciones();
        return listaTiposEducaciones;
    }

    
    
    
    
    
    @Override
    public List<TiposEducaciones>  lovTiposEducaciones(){
        return persistenciaTiposEducaciones.tiposEducaciones();
    }
    
    
  
  }
   
   









