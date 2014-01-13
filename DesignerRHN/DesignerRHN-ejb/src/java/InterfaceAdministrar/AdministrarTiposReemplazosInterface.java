/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposReemplazos;
import java.math.BigInteger;
import java.util.List;

public interface AdministrarTiposReemplazosInterface {

    public void modificarTiposReemplazos(List<TiposReemplazos> listaTiposReemplazosModificados);

    public void borrarTiposReemplazos(TiposReemplazos tiposIndicadores);

    public void crearTiposReemplazos(TiposReemplazos tiposIndicadores);

    public List<TiposReemplazos> mostrarTiposReemplazos();

    public TiposReemplazos mostrarTipoReemplazo(BigInteger secMotivoDemanda);

    public BigInteger verificarBorradoEncargaturas(BigInteger secuenciaTiposReemplazos);

    public BigInteger verificarBorradoProgramacionesTiempos(BigInteger secuenciaTiposReemplazos);

    public BigInteger verificarBorradoReemplazos(BigInteger secuenciaTiposReemplazos);

    public List<TiposReemplazos> lovTiposReemplazos();

}
