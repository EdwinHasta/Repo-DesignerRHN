/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarTiposExamenesInterface;
import Entidades.TiposExamenes;
import InterfacePersistencia.PersistenciaTiposExamenesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposExamenes implements AdministrarTiposExamenesInterface {

    @EJB
    PersistenciaTiposExamenesInterface persistenciaTiposExamenes;

    public void modificarTiposExamenes(List<TiposExamenes> listaTiposExamenes) {
        for (int i = 0; i < listaTiposExamenes.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposExamenes.editar(listaTiposExamenes.get(i));
        }
    }

    public void borrarTiposExamenes(List<TiposExamenes> listaTiposExamenes) {
        for (int i = 0; i < listaTiposExamenes.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposExamenes.borrar(listaTiposExamenes.get(i));
        }
    }

    public void crearTiposExamenes(List<TiposExamenes> listaTiposExamenes) {
        for (int i = 0; i < listaTiposExamenes.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposExamenes.borrar(listaTiposExamenes.get(i));
        }
    }

    public List<TiposExamenes> consultarTiposExamenes() {
        List<TiposExamenes> listTiposTallas;
        listTiposTallas = persistenciaTiposExamenes.buscarTiposExamenes();
        return listTiposTallas;
    }

    public TiposExamenes consultarTipoExamen(BigInteger secTipoEmpresa) {
        TiposExamenes tiposTallas;
        tiposTallas = persistenciaTiposExamenes.buscarTipoExamen(secTipoEmpresa);
        return tiposTallas;
    }

    public BigInteger contarTiposExamenesCargosTipoExamen(BigInteger secuenciaTiposExamenesCargos) {
        BigInteger verificadorTiposExamenesCargos;
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaTiposExamenesCargos);
            return verificadorTiposExamenesCargos = persistenciaTiposExamenes.contadorTiposExamenesCargos(secuenciaTiposExamenesCargos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposExamenes verificarBorradoTiposExamenesCargos ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarVigenciasExamenesMedicosTipoExamen(BigInteger secuenciaVigenciasExamenesMedicos) {
        try {
            BigInteger verificadorVigenciasExamenesMedicos;
            System.err.println("Secuencia Borrado Vigencias Tallas" + secuenciaVigenciasExamenesMedicos);
            return verificadorVigenciasExamenesMedicos = persistenciaTiposExamenes.contadorVigenciasExamenesMedicos(secuenciaVigenciasExamenesMedicos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposExamenes verificarBorradoVigenciasExamenesMedicos ERROR :" + e);
            return null;
        }
    }
}
