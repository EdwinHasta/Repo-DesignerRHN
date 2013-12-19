/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposJornadas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface PersistenciaTiposJornadasInterface {

    public void crear(TiposJornadas tiposJornadas);

    public void editar(TiposJornadas tiposJornadas);

    public void borrar(TiposJornadas tiposJornadas);

    public TiposJornadas buscarTipoJornada(BigInteger secuencia);

    public List<TiposJornadas> buscarTiposJornadas();

}
