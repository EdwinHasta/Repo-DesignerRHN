/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposAuxilios;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposAuxiliosInterface {

    public void modificarTiposAuxilios(List<TiposAuxilios> listaMotivosPrestamosModificados);

    public void borrarTiposAuxilios(TiposAuxilios tiposAuxilios);

    public void crearTiposAuxilios(TiposAuxilios tiposAuxilios);

    public List<TiposAuxilios> mostrarTiposAuxilios();

    public TiposAuxilios mostrarTipoAuxilio(BigInteger secTiposAuxilios);

    public BigInteger verificarTablasAuxilios(BigInteger secuenciaTiposAuxilios);
}
