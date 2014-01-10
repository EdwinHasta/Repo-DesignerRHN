/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.MotivosCesantias;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMotivosCesantiasInterface {
    public void modificarMotivosCesantias(List<MotivosCesantias> listaMotivosPrestamosModificados);

    public void borrarMotivosCesantias(MotivosCesantias tiposDias);

    public void crearMotivosCesantias(MotivosCesantias tiposDias);

    public List<MotivosCesantias> mostrarMotivosCesantias();

    public MotivosCesantias mostrarMotivoCesantia(BigInteger secMotivoPrestamo);

    public BigInteger verificarNovedadesSistema(BigInteger secuenciaMotivosCesantias);
}
