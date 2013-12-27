/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposDias;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface PersistenciaTiposDiasInterface {

    public void crear(TiposDias tiposDias);

    public void editar(TiposDias tiposDias);

    public void borrar(TiposDias tiposDias);

    public TiposDias buscarTipoDia(BigInteger secuencia);

    public List<TiposDias> buscarTiposDias();

}
