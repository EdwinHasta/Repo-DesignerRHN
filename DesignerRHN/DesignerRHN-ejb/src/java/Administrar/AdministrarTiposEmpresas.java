/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarTiposEmpresasInterface;
import Entidades.TiposEmpresas;
import InterfacePersistencia.PersistenciaTiposEmpresasInterface;
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
    TiposEmpresas tiposEmpresasSeleccionada;

    public void modificarTiposEmpresas(List<TiposEmpresas> listTiposEmpresas) {
        for (int i = 0; i < listTiposEmpresas.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposEmpresas.editar(listTiposEmpresas.get(i));
        }
    }

    public void borrarTiposEmpresas(List<TiposEmpresas> listTiposEmpresas) {
        for (int i = 0; i < listTiposEmpresas.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposEmpresas.borrar(listTiposEmpresas.get(i));
        }
    }

    public void crearTiposEmpresas(List<TiposEmpresas> listTiposEmpresas) {
        for (int i = 0; i < listTiposEmpresas.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposEmpresas.crear(listTiposEmpresas.get(i));
        }
    }

    public List<TiposEmpresas> consultarTiposEmpresas() {
        List<TiposEmpresas> listTiposEmpresas;
        listTiposEmpresas = persistenciaTiposEmpresas.buscarTiposEmpresas();
        return listTiposEmpresas;
    }

    public TiposEmpresas consultarTipoEmpresa(BigInteger secTipoEmpresa) {
        TiposEmpresas tiposEmpresas;
        tiposEmpresas = persistenciaTiposEmpresas.buscarTipoEmpresa(secTipoEmpresa);
        return tiposEmpresas;
    }

    public BigInteger contarSueldosMercadosTipoEmpresa(BigInteger secuenciaSueldosMercados) {
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
