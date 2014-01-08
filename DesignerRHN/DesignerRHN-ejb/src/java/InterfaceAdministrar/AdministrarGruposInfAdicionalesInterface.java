/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.GruposInfAdicionales;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarGruposInfAdicionalesInterface {

    public void modificarGruposInfAdicionales(List<GruposInfAdicionales> listDeportesModificadas);

    public void borrarGruposInfAdicionales(GruposInfAdicionales deportes);

    public void crearGruposInfAdicionales(GruposInfAdicionales deportes);

    public List<GruposInfAdicionales> mostrarGruposInfAdicionales();

    public GruposInfAdicionales mostrarGrupoInfAdicional(BigInteger secDeportes);

    public BigInteger verificadorInformacionesAdicionales(BigInteger secuenciaGruposInfAdicionales);
}
