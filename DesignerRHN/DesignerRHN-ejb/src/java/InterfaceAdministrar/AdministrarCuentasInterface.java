/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Cuentas;
import Entidades.Empresas;
import Entidades.Rubrospresupuestales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarCuentasInterface {

    public void crearCuentas(List<Cuentas> listCuentasCrear);

    public void modificarCuentas(List<Cuentas> listCuentasModificar);

    public void borrarCuentas(List<Cuentas> listCuentasBorrar);

    public List<Cuentas> listCuentasEmpresa(BigInteger secuencia);

    public List<Empresas> listEmpresas();

    public List<Rubrospresupuestales> listRubros();
}
