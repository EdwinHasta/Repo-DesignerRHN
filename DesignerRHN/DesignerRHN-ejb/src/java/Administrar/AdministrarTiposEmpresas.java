/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarTiposEmpresasInterface;
import Entidades.TiposEmpresas;
import InterfacePersistencia.PersistenciaTiposEmpresasInterface;
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
public class AdministrarTiposEmpresas implements AdministrarTiposEmpresasInterface {

    @EJB
    PersistenciaTiposEmpresasInterface persistenciaTiposEmpresas;
    private TiposEmpresas tiposEmpresasSeleccionada;
    private TiposEmpresas tiposEmpresas;
    private List<TiposEmpresas> listTiposEmpresas;

    public void modificarTiposEmpresas(List<TiposEmpresas> listTiposEmpresasModificadas) {
        for (int i = 0; i < listTiposEmpresasModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            tiposEmpresasSeleccionada = listTiposEmpresasModificadas.get(i);
            persistenciaTiposEmpresas.editar(tiposEmpresasSeleccionada);
        }
    }

    public void borrarTiposEmpresas(TiposEmpresas tipoEmpresa) {
        persistenciaTiposEmpresas.borrar(tipoEmpresa);
    }

    public void crearTiposEmpresas(TiposEmpresas tipoEmpresa) {
        persistenciaTiposEmpresas.crear(tipoEmpresa);
    }

    public List<TiposEmpresas> mostrarTiposEmpresas() {
        listTiposEmpresas = persistenciaTiposEmpresas.buscarTiposEmpresas();
        return listTiposEmpresas;
    }

    public TiposEmpresas mostrarTipoEmpresa(BigInteger secTipoEmpresa) {
        tiposEmpresas = persistenciaTiposEmpresas.buscarTipoEmpresa(secTipoEmpresa);
        return tiposEmpresas;
    }

    public BigInteger verificarBorradoSueldosMercados(BigInteger secuenciaSueldosMercados) {
        BigInteger verificadorSueldoMercados = null;
        try {
            System.err.println("Secuencia Borrado Sueldos Proyectos" + secuenciaSueldosMercados);
            verificadorSueldoMercados = persistenciaTiposEmpresas.contadorSueldosMercados(secuenciaSueldosMercados);
        } catch (Exception e) {
            System.err.println("ERROR AministrarTiposEmpresas verificarBorradoSueldosMercados ERROR :" + e);
        } finally {
            return verificadorSueldoMercados;
        }
    }
}
