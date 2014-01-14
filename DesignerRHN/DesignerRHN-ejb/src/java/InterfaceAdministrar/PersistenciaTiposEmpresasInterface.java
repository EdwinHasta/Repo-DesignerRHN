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
public interface PersistenciaTiposEmpresasInterface {

    public void crear(TiposEmpresas tiposEmpresas);

    public void editar(TiposEmpresas tiposEmpresas);

    public void borrar(TiposEmpresas tiposEmpresas);

    public TiposEmpresas buscarTipoEmpresa(BigInteger secuenciaTE);

    public List<TiposEmpresas> buscarTiposEmpresas();

    public BigInteger contadorSueldosMercados(BigInteger secuencia);
}
