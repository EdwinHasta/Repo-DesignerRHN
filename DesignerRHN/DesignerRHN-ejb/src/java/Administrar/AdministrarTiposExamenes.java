/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import InterfaceAdministrar.AdministrarTiposExamenesInterface;
import Entidades.TiposExamenes;
import InterfacePersistencia.PersistenciaTiposExamenesInterface;
import java.math.BigDecimal;
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
    private TiposExamenes tiposTallaSeleccionada;
    private TiposExamenes tiposTallas;
    private List<TiposExamenes> listTiposTallas;
    private BigDecimal verificadorTiposExamenesCargos;
    private BigDecimal verificadorVigenciasExamenesMedicos;

    public void modificarTiposExamenes(List<TiposExamenes> listTiposEmpresasModificadas) {
        for (int i = 0; i < listTiposEmpresasModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            tiposTallaSeleccionada = listTiposEmpresasModificadas.get(i);
            persistenciaTiposExamenes.editar(tiposTallaSeleccionada);
        }
    }

    public void borrarTiposExamenes(TiposExamenes tiposExamenes) {
        persistenciaTiposExamenes.borrar(tiposExamenes);
    }

    public void crearTiposExamenes(TiposExamenes tiposExamenes) {
        persistenciaTiposExamenes.crear(tiposExamenes);
    }

    public List<TiposExamenes> mostrarTiposExamenes() {
        listTiposTallas = persistenciaTiposExamenes.buscarTiposExamenes();
        return listTiposTallas;
    }

    public TiposExamenes mostrarTipoExamen(BigInteger secTipoEmpresa) {
        tiposTallas = persistenciaTiposExamenes.buscarTipoExamen(secTipoEmpresa);
        return tiposTallas;
    }

    public BigDecimal verificarBorradoTiposExamenesCargos(BigInteger secuenciaTiposExamenesCargos) {
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaTiposExamenesCargos);
            verificadorTiposExamenesCargos = persistenciaTiposExamenes.contadorTiposExamenesCargos(secuenciaTiposExamenesCargos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposExamenes verificarBorradoTiposExamenesCargos ERROR :" + e);
        } finally {
            return verificadorTiposExamenesCargos;
        }
    }

    public BigDecimal verificarBorradoVigenciasExamenesMedicos(BigInteger secuenciaVigenciasExamenesMedicos) {
        try {
            System.err.println("Secuencia Borrado Vigencias Tallas" + secuenciaVigenciasExamenesMedicos);
            verificadorVigenciasExamenesMedicos = persistenciaTiposExamenes.contadorVigenciasExamenesMedicos(secuenciaVigenciasExamenesMedicos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposExamenes verificarBorradoVigenciasExamenesMedicos ERROR :" + e);
        } finally {
            return verificadorVigenciasExamenesMedicos;
        }
    }
}
