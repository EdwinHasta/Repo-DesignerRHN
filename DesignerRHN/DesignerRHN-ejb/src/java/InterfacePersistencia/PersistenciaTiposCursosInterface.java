/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposCursos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaTiposCursosInterface {

    public void crear(TiposCursos tiposCursos);

    public void editar(TiposCursos tiposCursos);

    public void borrar(TiposCursos tiposCursos);

    public List<TiposCursos> consultarTiposCursos();

    public TiposCursos consultarTipoCurso(BigInteger secuencia);

    public BigInteger contarCursosTipoCurso(BigInteger secuencia);
}
