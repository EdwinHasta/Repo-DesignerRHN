package Administrar;

import Entidades.Cuentas;
import Entidades.Empresas;
import Entidades.Rubrospresupuestales;
import InterfaceAdministrar.AdministrarCuentasInterface;
import InterfacePersistencia.PersistenciaCuentasInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaRubrosPresupuestalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarCuentas implements AdministrarCuentasInterface {

    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    PersistenciaCuentasInterface persistenciaCuentas;
    @EJB
    PersistenciaRubrosPresupuestalesInterface persistenciaRubrosPresupuestales;
    //// ---- ////
    Cuentas cuenta;
    List<Cuentas> listCuentas;
    List<Empresas> listEmpresas;
    List<Rubrospresupuestales> listRubros;

    @Override
    public void crearCuentas(List<Cuentas> listCuentasCrear) {
        try {
            for (int i = 0; i < listCuentasCrear.size(); i++) {
                persistenciaCuentas.crear(listCuentasCrear.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error en crearCuentas Admi : " + e.toString());
        }
    }

    @Override
    public void modificarCuentas(List<Cuentas> listCuentasModificar) {
        try {
            for (int i = 0; i < listCuentasModificar.size(); i++) {
                persistenciaCuentas.editar(listCuentasModificar.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error en modificarCuentas Admi : " + e.toString());
        }
    }

    @Override
    public void borrarCuentas(List<Cuentas> listCuentasBorrar) {
        try {
            for (int i = 0; i < listCuentasBorrar.size(); i++) {
                persistenciaCuentas.borrar(listCuentasBorrar.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error en borrarCuentas Admi : " + e.toString());
        }
    }

    @Override
    public List<Cuentas> listCuentasEmpresa(BigInteger secuencia) {
        try {
            listCuentas = persistenciaCuentas.buscarCuentasSecuenciaEmpresa(secuencia);
            return listCuentas;
        } catch (Exception e) {
            System.out.println("Error en listCuentasEmpresa Admi : " + e.toString());
            return listCuentas;
        }
    }

    @Override
    public List<Empresas> listEmpresas() {
        try {
            listEmpresas = persistenciaEmpresas.buscarEmpresas();
            return listEmpresas;
        } catch (Exception e) {
            System.out.println("Error en listEmpresas Admi : " + e.toString());
            return listEmpresas;
        }
    }

    @Override
    public List<Rubrospresupuestales> listRubros() {
        try {
            listRubros = persistenciaRubrosPresupuestales.buscarRubros();
            return listRubros;
        } catch (Exception e) {
            System.out.println("Error en listRubros Admi : " + e.toString());
            return listRubros;
        }
    }
}
