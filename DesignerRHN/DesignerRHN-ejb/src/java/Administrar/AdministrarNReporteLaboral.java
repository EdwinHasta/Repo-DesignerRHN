/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Cargos;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import InterfaceAdministrar.AdministrarNReporteLaboralInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaInforeportesInterface;
import InterfacePersistencia.PersistenciaParametrosInformesInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarNReporteLaboral implements AdministrarNReporteLaboralInterface {

    @EJB
    PersistenciaInforeportesInterface persistenciaInforeportes;
    @EJB
    PersistenciaParametrosInformesInterface persistenciaParametrosInformes;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    List<Inforeportes> listInforeportes;
    ParametrosInformes parametroReporte;
    String usuarioActual;
    List<Cargos> listCargos;
    List<Empresas> listEmpresas;
    List<Empleados> listEmpleados;

    @Override
    public ParametrosInformes parametrosDeReporte() {
        try {
            usuarioActual = persistenciaActualUsuario.actualAliasBD();
            parametroReporte = persistenciaParametrosInformes.buscarParametroInformeUsuario(usuarioActual);
            return parametroReporte;
        } catch (Exception e) {
            System.out.println("Error parametrosDeReporte Administrar" + e);
            return null;
        }
    }

    @Override
    public List<Inforeportes> listInforeportesUsuario() {
        try {
            listInforeportes = persistenciaInforeportes.buscarInforeportesUsuarioLaboral();
            System.out.println("Tamaño: " + listInforeportes.size());
            return listInforeportes;
        } catch (Exception e) {
            System.out.println("Error listInforeportesUsuario " + e);
            return null;
        }
    }
    
    @Override
    public void modificarParametrosInformes(ParametrosInformes parametroInforme){
        try{
            persistenciaParametrosInformes.editar(parametroInforme);
        }catch(Exception e){
            System.out.println("Error modificarParametrosInformes : "+e.toString());
        }
    }
    
    @Override
    public List<Cargos> listCargos(){
        try{
            listCargos = persistenciaCargos.buscarCargos();
            return listCargos;
        }catch(Exception e){
            System.out.println("Error en listCargos Administrar: "+e.toString());
            return null;
        }
    }
    
    @Override
    public List<Empleados> listEmpleados(){
        try{
            listEmpleados = persistenciaEmpleado.buscarEmpleados();
            return listEmpleados;
        }catch(Exception e){
            System.out.println("Error listEmpleados : "+e.toString());
            return null;
        }
    }
    
    @Override
    public List<Empresas> listEmpresas() {
        try {
            listEmpresas = persistenciaEmpresas.buscarEmpresas();
            return listEmpresas;
        } catch (Exception e) {
            System.out.println("Error listEmpresas Administrar : " + e.toString());
            return null;
        }
    }
    
     @Override 
    public void guardarCambiosInfoReportes(List<Inforeportes> listaIR) {
        try {
            for (int i = 0; i < listaIR.size(); i++) {
                persistenciaInforeportes.editar(listaIR.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error guardarCambiosInfoReportes Admi : " + e.toString());
        }
    }
}
