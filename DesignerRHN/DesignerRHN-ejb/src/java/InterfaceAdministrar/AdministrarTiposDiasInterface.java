/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposDias;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposDiasInterface {

    public void modificarTiposDias(List<TiposDias> listaTiposDiasModificados);

    public void borrarTiposDias(TiposDias tiposDias);

    public void crearTiposDias(TiposDias tiposDias);

    public List<TiposDias> mostrarTiposDias();

    public TiposDias mostrarTipoDia(BigInteger secTipoDia);

    public BigInteger verificarDiasLaborales(BigInteger secuenciaTiposDias);

    public BigInteger verificarExtrasRecargos(BigInteger secuenciaTiposDias);
}
