/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.LugaresOcurrencias;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarLugaresOcurrenciasInterface {

    public void modificarLesiones(List<LugaresOcurrencias> listLugaresOcurrenciasModificadas);

    public void borrarLugarOcurrencia(LugaresOcurrencias lugarOcurrencia);

    public void crearLugarOcurrencia(LugaresOcurrencias lugarOcurrencia);

    public List<LugaresOcurrencias> mostrarLugaresOcurrencias();

    public LugaresOcurrencias mostrarLugarOcurrencia(BigInteger secLugarOcurrencia);

    public BigInteger verificarSoAccidentes(BigInteger secuenciaLugaresOcurrencias);
}
