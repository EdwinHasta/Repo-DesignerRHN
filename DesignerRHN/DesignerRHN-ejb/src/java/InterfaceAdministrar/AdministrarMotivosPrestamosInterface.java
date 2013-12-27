/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MotivosPrestamos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMotivosPrestamosInterface {

    public void modificarMotivosPrestamos(List<MotivosPrestamos> listaMotivosPrestamosModificados);

    public void borrarMotivosPrestamos(MotivosPrestamos tiposDias);

    public void crearMotivosPrestamos(MotivosPrestamos tiposDias);

    public List<MotivosPrestamos> mostrarMotivosPrestamos();

    public MotivosPrestamos mostrarMotivoPrestamo(BigInteger secMotivoPrestamo);

    public BigInteger verificarEersPrestamos(BigInteger secuenciaMotivosPrestamos);
}
