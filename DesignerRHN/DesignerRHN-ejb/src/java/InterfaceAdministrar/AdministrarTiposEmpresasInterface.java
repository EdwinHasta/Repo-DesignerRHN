/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposEmpresas;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposEmpresasInterface {

    public void modificarTiposEmpresas(List<TiposEmpresas> listTiposEmpresasModificadas);

    public void borrarTiposEmpresas(TiposEmpresas tipoEmpresa);

    public void crearTiposEmpresas(TiposEmpresas tipoEmpresa);

    public List<TiposEmpresas> mostrarTiposEmpresas();

    public TiposEmpresas mostrarTipoEmpresa(BigInteger secTipoEmpresa);

    public BigInteger verificarBorradoSueldosMercados(BigInteger secuenciaSueldosMercados);
}
