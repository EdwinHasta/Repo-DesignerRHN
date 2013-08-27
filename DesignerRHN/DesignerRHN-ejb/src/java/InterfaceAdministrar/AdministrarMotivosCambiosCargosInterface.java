/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MotivosCambiosCargos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface AdministrarMotivosCambiosCargosInterface {

    public List<MotivosCambiosCargos> consultarTodo();

    public MotivosCambiosCargos consultarPorSecuencia(BigInteger secuenciaMCC);

    public List<String> consultarNombreTodo();
}
