/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposIndicadores;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposIndicadoresInterface {

    public void modificarTiposIndicadores(List<TiposIndicadores> listTiposIndicadoresModificadas);

    public void borrarTiposIndicadores(TiposIndicadores tiposIndicadores);

    public void crearTiposIndicadores(TiposIndicadores tiposIndicadores);

    public List<TiposIndicadores> mostrarTiposIndicadores();

    public TiposIndicadores mostrarTipoIndicador(BigInteger secMotivoDemanda);

    public BigInteger verificarBorradoVigenciasIndicadores(BigInteger secuenciaVigenciasIndicadores);
}
