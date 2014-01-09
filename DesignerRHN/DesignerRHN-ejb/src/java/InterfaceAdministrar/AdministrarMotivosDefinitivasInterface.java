/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.MotivosDefinitivas;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMotivosDefinitivasInterface {
     public void modificarMotivosDefinitivas(List<MotivosDefinitivas> listaMotivosPrestamosModificados);

    public void borrarMotivosDefinitivas(MotivosDefinitivas tiposDias);

    public void crearMotivosDefinitivas(MotivosDefinitivas tiposDias);

    public List<MotivosDefinitivas> mostrarMotivosDefinitivas();

    public MotivosDefinitivas mostrarMotivoDefinitiva(BigInteger secMotivoPrestamo);

    public BigInteger verificarNovedadesSistema(BigInteger secuenciaMotivosCesantias);

    public BigInteger verificarParametrosCambiosMasivos(BigInteger secuenciaMotivosCesantias);
}
