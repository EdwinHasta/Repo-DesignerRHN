/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.SoPoblacionObjetivos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaSoPoblacionObjetivosInterface {

    public void crear(SoPoblacionObjetivos soPoblacionObjetivos);

    public void editar(SoPoblacionObjetivos soPoblacionObjetivos);

    public void borrar(SoPoblacionObjetivos soPoblacionObjetivos);

    public SoPoblacionObjetivos buscarSoPoblacionObjetivo(BigInteger secuencia);

    public List<SoPoblacionObjetivos> consultarSoPoblacionObjetivos();
}
