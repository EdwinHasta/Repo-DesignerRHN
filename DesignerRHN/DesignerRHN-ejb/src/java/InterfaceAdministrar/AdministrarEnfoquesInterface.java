/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Enfoques;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarEnfoquesInterface {

    public void modificarEnfoques(List<Enfoques> listEnfoquesModificadas);

    public void borrarEnfoques(Enfoques motivosLocalizaciones);

    public void crearEnfoques(Enfoques motivosLocalizaciones);

    public void buscarEnfoques(Enfoques motivosLocalizaciones);

    public List<Enfoques> mostrarEnfoques();

    public Enfoques mostrarEnfoque(BigInteger secEnfoques);

    public BigInteger verificarTiposDetalles(BigInteger secuenciaTiposAuxilios);
}
