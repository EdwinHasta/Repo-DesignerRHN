/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposExamenes;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposExamenesInterface {

    public void modificarTiposExamenes(List<TiposExamenes> listTiposEmpresasModificadas);

    public void borrarTiposExamenes(TiposExamenes tiposExamenes);

    public void crearTiposExamenes(TiposExamenes tiposExamenes);

    public List<TiposExamenes> mostrarTiposExamenes();

    public TiposExamenes mostrarTipoExamen(BigInteger secTipoEmpresa);

    public BigInteger verificarBorradoTiposExamenesCargos(BigInteger secuenciaTiposExamenesCargos);

    public BigInteger verificarBorradoVigenciasExamenesMedicos(BigInteger secuenciaVigenciasExamenesMedicos);
}
