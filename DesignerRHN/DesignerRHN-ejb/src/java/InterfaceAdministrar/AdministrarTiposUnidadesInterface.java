/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposUnidades;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposUnidadesInterface {

    public void modificarTiposUnidades(List<TiposUnidades> listaTiposUnidades);

    public void borrarTiposUnidades(List<TiposUnidades> listaTiposUnidades);

    public void crearTiposUnidades(List<TiposUnidades> listaTiposUnidades);

    public List<TiposUnidades> consultarTiposUnidades();

    public TiposUnidades consultarTipoIndicador(BigInteger secMotivoDemanda);

    public BigInteger contarUnidadesTipoUnidad(BigInteger secuenciaVigenciasIndicadores);
}
