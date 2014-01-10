/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.PryPlataformas;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarPryPlataformasInterface {

    public void modificarPryPlataformas(List<PryPlataformas> listPryClientesModificadas);

    public void borrarPryPlataformas(PryPlataformas pryClientes);

    public void crearPryPlataformas(PryPlataformas pryClientes);

    public List<PryPlataformas> mostrarPryPlataformas();

    public PryPlataformas mostrarPryPlataformas(BigInteger secPryClientes);

    public BigInteger verificarBorradoProyecto(BigInteger secuenciaProyectos);
}
