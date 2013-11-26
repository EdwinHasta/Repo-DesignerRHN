/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Deportes;
import Entidades.Empleados;
import Entidades.VigenciasDeportes;
import InterfaceAdministrar.AdministrarVigenciaDeporteInterface;
import InterfacePersistencia.PersistenciaDeportesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaVigenciasDeportesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarVigenciaDeporte implements AdministrarVigenciaDeporteInterface{

    @EJB
    PersistenciaVigenciasDeportesInterface persistenciaVigenciasDeportes;
    @EJB
    PersistenciaDeportesInterface persistenciaDeportes;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;

    @Override
    public List<VigenciasDeportes> listVigenciasDeportesPersona(BigInteger secuenciaP) {
        try {
            List<VigenciasDeportes> retorno = persistenciaVigenciasDeportes.deportesTotalesSecuenciaPersona(secuenciaP);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error listVigenciasDeportesPersona Admi : "+e.toString());
            return null;
        }
    }
    
    @Override
    public void crearVigenciasDeportes(List<VigenciasDeportes> listaVD){
        try{
            for(int i = 0;i<listaVD.size();i++){
                if(listaVD.get(i).getDeporte().getSecuencia() == null){
                    listaVD.get(i).setDeporte(null);
                }
                persistenciaVigenciasDeportes.crear(listaVD.get(i));
            }
        }catch(Exception e){
            System.out.println("Error crearVigenciasDeportes Admi : "+e.toString());
        }
    }
    
    @Override
    public void editarVigenciasDeportes(List<VigenciasDeportes> listaVD){
        try{
            for(int i = 0;i<listaVD.size();i++){
                if(listaVD.get(i).getDeporte().getSecuencia() == null){
                    listaVD.get(i).setDeporte(null);
                }
                persistenciaVigenciasDeportes.editar(listaVD.get(i));
            }
        }catch(Exception e){
            System.out.println("Error editarVigenciasDeportes Admi : "+e.toString());
        }
    }
    
    @Override
    public void borrarVigenciasDeportes(List<VigenciasDeportes> listaVD){
        try{
            for(int i = 0;i<listaVD.size();i++){
                if(listaVD.get(i).getDeporte().getSecuencia() == null){
                    listaVD.get(i).setDeporte(null);
                }
                persistenciaVigenciasDeportes.borrar(listaVD.get(i));
            }
        }catch(Exception e){
            System.out.println("Error borrarVigenciasDeportes Admi : "+e.toString());
        }
    }
    
    @Override
    public List<Deportes> listDeportes(){
        try{
        List<Deportes> retorno = persistenciaDeportes.buscarDeportes();
        return retorno;
        }catch(Exception  e){
            System.out.println("Error listDeportes Admi : "+e.toString());
            return null;
        }
    }
    
    @Override
    public Empleados empleadoActual(BigInteger secuenciaP){
        try{
        Empleados retorno = persistenciaEmpleado.buscarEmpleado(secuenciaP);
        return retorno;
        }catch(Exception  e){
            System.out.println("Error empleadoActual Admi : "+e.toString());
            return null;
        }
    }
}
