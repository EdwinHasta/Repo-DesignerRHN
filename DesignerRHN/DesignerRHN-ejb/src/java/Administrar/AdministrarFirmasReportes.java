/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import InterfaceAdministrar.AdministrarFirmasReportesInterface;
import Entidades.FirmasReportes;
import Entidades.Cargos;
import Entidades.Empresas;
import Entidades.Personas;
import InterfacePersistencia.PersistenciaFirmasReportesInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaPersonasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarFirmasReportes implements AdministrarFirmasReportesInterface {

     //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaFirmasReportes'.
     */
    @EJB
    PersistenciaFirmasReportesInterface persistenciaFirmasReportes;
    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------

    public void modificarFirmasReportes(List<FirmasReportes> listaFirmasReportes) {
        for (int i = 0; i < listaFirmasReportes.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaFirmasReportes.editar(listaFirmasReportes.get(i));
        }
    }

    public void borrarFirmasReportes(List<FirmasReportes> listaFirmasReportes) {
        for (int i = 0; i < listaFirmasReportes.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaFirmasReportes.borrar(listaFirmasReportes.get(i));
        }
    }

    public void crearFirmasReportes(List<FirmasReportes> listaFirmasReportes) {
        for (int i = 0; i < listaFirmasReportes.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaFirmasReportes.crear(listaFirmasReportes.get(i));
        }
    }

    @Override
    public List<FirmasReportes> consultarFirmasReportes() {
        List<FirmasReportes> listaFirmasReportes;
        listaFirmasReportes = persistenciaFirmasReportes.consultarFirmasReportes();
        return listaFirmasReportes;
    }

    public FirmasReportes consultarTipoIndicador(BigInteger secMotivoDemanda) {
        FirmasReportes tiposIndicadores;
        tiposIndicadores = persistenciaFirmasReportes.consultarFirmaReporte(secMotivoDemanda);
        return tiposIndicadores;
    }

    public List<Cargos> consultarLOVCargos() {
        List<Cargos> listLOVCargos;
        listLOVCargos = persistenciaCargos.consultarCargos();
        return listLOVCargos;
    }
    public List<Personas> consultarLOVPersonas() {
        List<Personas> listLOVPersonas;
        listLOVPersonas = persistenciaPersonas.consultarPersonas();
        return listLOVPersonas;
    }
    public List<Empresas> consultarLOVEmpresas() {
        List<Empresas> listLOVEmpresas;
        listLOVEmpresas = persistenciaEmpresas.consultarEmpresas();
        return listLOVEmpresas;
    }

}
