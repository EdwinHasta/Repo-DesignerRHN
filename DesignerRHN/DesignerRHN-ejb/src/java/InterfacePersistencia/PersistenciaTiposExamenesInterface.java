/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposExamenes;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author John Pineda
 */
@Local
public interface PersistenciaTiposExamenesInterface {

    public void crear(TiposExamenes tiposExamenes);

    public void editar(TiposExamenes tiposExamenes);

    public void borrar(TiposExamenes tiposExamenes);

    public TiposExamenes buscarTipoExamen(BigInteger secuenciaTe);

    public List<TiposExamenes> buscarTiposExamenes();

    public BigDecimal contadorTiposExamenesCargos(BigInteger secuencia);

    public BigDecimal contadorVigenciasExamenesMedicos(BigInteger secuencia);

}
