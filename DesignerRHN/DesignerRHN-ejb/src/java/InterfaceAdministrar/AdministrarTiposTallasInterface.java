/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposTallas;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposTallasInterface {

    public void modificarTiposTallas(List<TiposTallas> listTiposEmpresasModificadas);

    public void borrarTiposTallas(TiposTallas tiposTallas);

    public void crearTiposTallas(TiposTallas tiposTallas);

    public List<TiposTallas> mostrarTiposTallas();

    public TiposTallas mostrarTipoTalla(BigInteger secTipoEmpresa);

    public BigDecimal verificarBorradoElementos(BigInteger secuenciaElementos);

    public BigDecimal verificarBorradoVigenciasTallas(BigInteger secuenciaVigenciasTallas);
}
