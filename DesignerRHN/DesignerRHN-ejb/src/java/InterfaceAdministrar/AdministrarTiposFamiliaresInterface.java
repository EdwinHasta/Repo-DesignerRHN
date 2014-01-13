/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.TiposFamiliares;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposFamiliaresInterface {
    public void modificarTiposFamiliares(List<TiposFamiliares> listTiposFamiliaresModificadas);
    public void borrarTiposFamiliares(TiposFamiliares tiposExamenes) ;
    public void crearTiposFamiliares(TiposFamiliares tiposExamenes);
    public List<TiposFamiliares> mostrarTiposFamiliares();
    public TiposFamiliares mostrarTipoExamen(BigInteger secTipoEmpresa);
    public BigInteger verificarBorradoHvReferencias(BigInteger secuenciaTiposFamiliares);
}
