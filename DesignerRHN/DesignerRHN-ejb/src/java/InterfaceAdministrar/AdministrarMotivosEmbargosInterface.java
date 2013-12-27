/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MotivosEmbargos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMotivosEmbargosInterface {

    public void modificarMotivosEmbargos(List<MotivosEmbargos> listaMotivosPrestamosModificados);

    public void borrarMotivosEmbargos(MotivosEmbargos tiposDias);

    public void crearMotivosEmbargos(MotivosEmbargos tiposDias);

    public List<MotivosEmbargos> mostrarMotivosEmbargos();

    public MotivosEmbargos mostrarMotivoEmbargo(BigInteger secMotivoPrestamo);

    public BigInteger verificarEersPrestamos(BigInteger secuenciaTiposDias);

    public BigInteger verificarEmbargos(BigInteger secuenciaTiposDias);
}
