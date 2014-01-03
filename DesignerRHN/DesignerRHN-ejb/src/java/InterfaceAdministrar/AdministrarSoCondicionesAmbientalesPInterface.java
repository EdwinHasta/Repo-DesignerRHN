/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.SoCondicionesAmbientalesP;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarSoCondicionesAmbientalesPInterface {

    public void modificarSoCondicionesAmbientalesP(List<SoCondicionesAmbientalesP> listSoCondicionesAmbientalesPModificada);

    public void borrarSoCondicionesAmbientalesP(SoCondicionesAmbientalesP soCondicionesAmbientalesP);

    public void crearSoCondicionesAmbientalesP(SoCondicionesAmbientalesP soCondicionesAmbientalesP);

    public List<SoCondicionesAmbientalesP> mostrarSoCondicionesAmbientalesP();

    public SoCondicionesAmbientalesP mostrarSoCondicionAmbientalP(BigInteger secSoCondicionesAmbientalesP);

    public BigInteger verificarSoAccidentesMedicos(BigInteger secuenciaElementos);
}
