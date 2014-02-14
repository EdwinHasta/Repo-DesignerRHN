/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import Entidades.SucursalesPila;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaSucursalesPilaInterface {
    public List<SucursalesPila> consultarSucursalesPila();
     public List<SucursalesPila> consultarSucursalesPilaPorEmpresa(BigInteger secEmpresa);
}
