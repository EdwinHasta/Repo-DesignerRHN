/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.Indicadores;
import Entidades.TiposIndicadores;
import Entidades.VigenciasIndicadores;
import InterfaceAdministrar.AdministrarEmplVigenciaIndicadorInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaIndicadoresInterface;
import InterfacePersistencia.PersistenciaTiposIndicadoresInterface;
import InterfacePersistencia.PersistenciaVigenciasIndicadoresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarEmplVigenciaIndicador implements AdministrarEmplVigenciaIndicadorInterface{

    @EJB
    PersistenciaVigenciasIndicadoresInterface persistenciaVigenciasIndicadores;
    @EJB
    PersistenciaTiposIndicadoresInterface persistenciaTiposIndicadores;
    @EJB
    PersistenciaIndicadoresInterface persistenciaIndicadores;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;

    @Override
    public List<Indicadores> listIndicadores() {
        try {
            List<Indicadores> retorno = persistenciaIndicadores.buscarIndicadores();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error listIndicadores Admi : "+e.toString());
            return null;
        }
    }

    @Override
    public List<TiposIndicadores> listTiposIndicadores() {
        try {
            List<TiposIndicadores> retorno = persistenciaTiposIndicadores.buscarTiposIndicadores();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error listTiposIndicadores Admi : "+e.toString());
            return null;
        }
    }

    @Override
    public Empleados empleadoActual(BigInteger secuencia) {
        try {
            Empleados actual = persistenciaEmpleado.buscarEmpleado(secuencia);
            return actual;
        } catch (Exception e) {
            System.out.println("Error empleadoActual Admi : "+e.toString());
            return null;
        }
    }
    
    @Override
    public List<VigenciasIndicadores> listVigenciasIndicadoresEmpleado(BigInteger secuencia){
        try{
        List<VigenciasIndicadores> retorno = persistenciaVigenciasIndicadores.indicadoresTotalesEmpleadoSecuencia(secuencia);
        return retorno;
        }catch(Exception e){
            System.out.println("Error listVigenciasAfiliacioneaEmpleado Admi : "+e.toString());
            return null;
        }
    }
    
    @Override
    public void crearVigenciasIndicadores(List<VigenciasIndicadores> listVI){
        try{
            for(int i = 0;i<listVI.size();i++){
                if(listVI.get(i).getIndicador().getSecuencia() == null){
                listVI.get(i).setIndicador(null);
                }
                if(listVI.get(i).getTipoindicador().getSecuencia() == null){
                listVI.get(i).setTipoindicador(null);
                }
                persistenciaVigenciasIndicadores.crear(listVI.get(i));
            }
        }catch(Exception e){
            System.out.println("Error crearVigenciasIndicadores Admi : "+e.toString());
        }
    }
    
    @Override
    public void editarVigenciasIndicadores(List<VigenciasIndicadores> listVI){
        try{
            for(int i = 0;i<listVI.size();i++){
                if(listVI.get(i).getIndicador().getSecuencia() == null){
                listVI.get(i).setIndicador(null);
                }
                if(listVI.get(i).getTipoindicador().getSecuencia() == null){
                listVI.get(i).setTipoindicador(null);
                }
                persistenciaVigenciasIndicadores.editar(listVI.get(i));
            }
        }catch(Exception e){
            System.out.println("Error editarVigenciasIndicadores Admi : "+e.toString());
        }
    }
    
    @Override
    public void borrarVigenciasIndicadores(List<VigenciasIndicadores> listVI){
        try{
            for(int i = 0;i<listVI.size();i++){
                if(listVI.get(i).getIndicador().getSecuencia() == null){
                listVI.get(i).setIndicador(null);
                }
                if(listVI.get(i).getTipoindicador().getSecuencia() == null){
                listVI.get(i).setTipoindicador(null);
                }
                persistenciaVigenciasIndicadores.borrar(listVI.get(i));
            }
        }catch(Exception e){
            System.out.println("Error borrarVigenciasIndicadores Admi : "+e.toString());
        }
    }
}
