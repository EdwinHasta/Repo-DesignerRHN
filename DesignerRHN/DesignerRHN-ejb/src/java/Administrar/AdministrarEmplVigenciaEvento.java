/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.Eventos;
import Entidades.VigenciasEventos;
import InterfaceAdministrar.AdministrarEmplVigenciaEventoInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEventosInterface;
import InterfacePersistencia.PersistenciaVigenciasEventosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.EJB;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarEmplVigenciaEvento implements AdministrarEmplVigenciaEventoInterface{

    @EJB
    PersistenciaEventosInterface persistenciaEventos;
    @EJB
    PersistenciaVigenciasEventosInterface persistenciaVigenciasEventos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    
    @Override
    public List<VigenciasEventos> listVigenciasEventosEmpleado(BigInteger secuencia){
        try{
            List<VigenciasEventos> vigencias = persistenciaVigenciasEventos.vigenciasEventosSecuenciaEmpleado(secuencia);
            return vigencias;
        }catch(Exception e){
            System.out.println("Error listVigenciasEventosEmpleado Admi : "+e.toString());
            return null;
        }
    }
    
    @Override
    public void crearVigenciasEventos (List<VigenciasEventos> listaV){
        try{
            for(int i = 0;i<listaV.size();i++){
                if(listaV.get(i).getEvento().getSecuencia() == null){
                    listaV.get(i).setEvento(null);
                }
                persistenciaVigenciasEventos.crear(listaV.get(i));
            }
        }catch(Exception e){
            System.out.println("Error crearVigenciasEventos Admi : "+e.toString());
        }
    }
    
    @Override
    public void editarVigenciasEventos (List<VigenciasEventos> listaV){
        try{
            for(int i = 0;i<listaV.size();i++){
                if(listaV.get(i).getEvento().getSecuencia() == null){
                    listaV.get(i).setEvento(null);
                }
                persistenciaVigenciasEventos.editar(listaV.get(i));
            }
        }catch(Exception e){
            System.out.println("Error editarVigenciasEventos Admi : "+e.toString());
        }
    }
    
    @Override
    public void borrarVigenciasEventos (List<VigenciasEventos> listaV){
        try{
            for(int i = 0;i<listaV.size();i++){
                if(listaV.get(i).getEvento().getSecuencia() == null){
                    listaV.get(i).setEvento(null);
                }
                persistenciaVigenciasEventos.borrar(listaV.get(i));
            }
        }catch(Exception e){
            System.out.println("Error borrarVigenciasEventos Admi : "+e.toString());
        }
    }
    
    @Override
    public List<Eventos> listEventos(){
        try{
        List<Eventos> eventos = persistenciaEventos.buscarEventos();
        return eventos;
        }catch(Exception e){
            System.out.println("Error listEventos Admi : "+e.toString());
            return null;
        }
    }
    
    @Override
    public Empleados empleadoActual (BigInteger secuencia){
        try{
            Empleados empl = persistenciaEmpleado.buscarEmpleado(secuencia);
            return empl;
        }catch(Exception e){
            System.out.println("Error empleadoActual Admi : "+e.toString());
            return null;
        }
    }
  

}
