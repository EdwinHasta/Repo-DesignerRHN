/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import Entidades.FormasDtos;
import java.math.BigInteger;
import java.util.List;


public interface PersistenciaFormasDtosInterface {
    public void crear(FormasDtos formasDtos);
    public void editar(FormasDtos formasDtos);
    public void borrar(FormasDtos formasDtos);
    public List<FormasDtos> formasDescuentos(BigInteger tipoEmbargo);
}
