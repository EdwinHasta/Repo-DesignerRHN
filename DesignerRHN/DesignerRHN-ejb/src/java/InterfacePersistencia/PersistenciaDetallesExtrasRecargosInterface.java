/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import Entidades.DetallesExtrasRecargos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface PersistenciaDetallesExtrasRecargosInterface {
    
    public void crear(DetallesExtrasRecargos extrasRecargos);
    public void editar(DetallesExtrasRecargos extrasRecargos);
    public void borrar(DetallesExtrasRecargos extrasRecargos);
    public DetallesExtrasRecargos buscarDetalleExtraRecargo(BigInteger secuencia);
    public List<DetallesExtrasRecargos> buscaDetallesExtrasRecargos();
    public List<DetallesExtrasRecargos> buscaDetallesExtrasRecargosPorSecuenciaExtraRecargo(BigInteger secuencia);
    
}
