/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.TiposEmbargos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposEmbargosInterface {
    public void modificarTiposPrestamos(List<TiposEmbargos> listaTiposEmbargosModificados);
    public void borrarTiposPrestamos(TiposEmbargos tiposEmbargos);
    public void crearTiposPrestamos(TiposEmbargos tiposEmbargos);
    public List<TiposEmbargos> mostrarTiposPrestamos();
    public TiposEmbargos mostrarTipoPrestamo(BigInteger secMotivoPrestamo);

    public BigInteger verificarDiasLaborales(BigInteger secuenciaTiposDias);
    public BigInteger verificarExtrasRecargos(BigInteger secuenciaTiposDias);
}
