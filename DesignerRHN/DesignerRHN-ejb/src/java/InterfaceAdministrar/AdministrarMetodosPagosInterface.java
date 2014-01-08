/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MetodosPagos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMetodosPagosInterface {

    public void modificarMetodosPagos(List<MetodosPagos> listMotivosCambiosCargosModificadas);

    public void borrarMetodosPagos(MetodosPagos metodosPagos);

    public void crearMetodosPagos(MetodosPagos metodosPagos);

    public List<MetodosPagos> mostrarMetodosPagos();

    public MetodosPagos mostrarMetodoPago(BigInteger secMetodosPagos);

    public BigInteger verificarVigenciasFormasPagos(BigInteger secuenciaMetodoPago);
}
