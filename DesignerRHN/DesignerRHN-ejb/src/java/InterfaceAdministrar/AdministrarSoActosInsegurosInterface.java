/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.SoActosInseguros;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarSoActosInsegurosInterface {

    public void modificarSoActosInseguros(List<SoActosInseguros> listSoActosInsegurosPModificada);

    public void borrarSoActosInseguros(SoActosInseguros soActosInseguros);

    public void crearSoActosInseguros(SoActosInseguros soActosInseguros);

    public List<SoActosInseguros> mostrarSoActosInseguros();

    public SoActosInseguros mostrarSoActoInseguro(BigInteger secSoCondicionesAmbientalesP);

    public BigDecimal verificarSoAccidentesMedicos(BigInteger secuenciaElementos);
}
