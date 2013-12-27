/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.ExtrasRecargos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface PersistenciaExtrasRecargosInterface {

    public void crear(ExtrasRecargos extrasRecargos);

    public void editar(ExtrasRecargos extrasRecargos);

    public void borrar(ExtrasRecargos extrasRecargos);

    public ExtrasRecargos buscarExtraRecargo(BigInteger secuencia);

    public List<ExtrasRecargos> buscarExtrasRecargos();

}
