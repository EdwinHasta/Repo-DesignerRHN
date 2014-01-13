/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposFamiliares;
import InterfacePersistencia.PersistenciaTiposFamiliaresInterface;
import InterfaceAdministrar.AdministrarTiposFamiliaresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposFamiliares implements AdministrarTiposFamiliaresInterface {

    @EJB
    PersistenciaTiposFamiliaresInterface persistenciaTiposFamiliares;
    private TiposFamiliares tiposFamiliaresSeleccionada;
    private TiposFamiliares tiposFamiliares;
    private List<TiposFamiliares> listTiposFamiliares;

    public void modificarTiposFamiliares(List<TiposFamiliares> listTiposFamiliaresModificadas) {
        for (int i = 0; i < listTiposFamiliaresModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            tiposFamiliaresSeleccionada = listTiposFamiliaresModificadas.get(i);
            persistenciaTiposFamiliares.editar(tiposFamiliaresSeleccionada);
        }
    }

    public void borrarTiposFamiliares(TiposFamiliares tiposExamenes) {
        persistenciaTiposFamiliares.borrar(tiposExamenes);
    }

    public void crearTiposFamiliares(TiposFamiliares tiposExamenes) {
        persistenciaTiposFamiliares.crear(tiposExamenes);
    }

    public List<TiposFamiliares> mostrarTiposFamiliares() {
        listTiposFamiliares = persistenciaTiposFamiliares.buscarTiposFamiliares();
        return listTiposFamiliares;
    }

    public TiposFamiliares mostrarTipoExamen(BigInteger secTipoEmpresa) {
        tiposFamiliares = persistenciaTiposFamiliares.buscarTiposFamiliares(secTipoEmpresa);
        return tiposFamiliares;
    }

    public BigInteger verificarBorradoHvReferencias(BigInteger secuenciaTiposFamiliares) {
        BigInteger verificadorHvReferencias = null;

        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaTiposFamiliares);
            verificadorHvReferencias = persistenciaTiposFamiliares.contadorHvReferencias(secuenciaTiposFamiliares);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposFamiliares verificarBorradoElementos ERROR :" + e);
        } finally {
            return verificadorHvReferencias;
        }
    }

}
