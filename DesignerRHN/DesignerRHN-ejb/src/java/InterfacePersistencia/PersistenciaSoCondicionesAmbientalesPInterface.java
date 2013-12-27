/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import Entidades.SoCondicionesAmbientalesP;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaSoCondicionesAmbientalesPInterface {
    
    public void crear(SoCondicionesAmbientalesP soCondicionesAmbientalesP);

    public void editar(SoCondicionesAmbientalesP soCondicionesAmbientalesP) ;
    public void borrar(SoCondicionesAmbientalesP soCondicionesAmbientalesP);
    public SoCondicionesAmbientalesP buscarSoCondicionAmbientalP(BigInteger secuenciaSCAP);
    public List<SoCondicionesAmbientalesP> buscarSoCondicionesAmbientalesP();
    public BigDecimal contadorSoAccidentesMedicos(BigInteger secuencia);
}
