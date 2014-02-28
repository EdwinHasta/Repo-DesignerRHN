/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.EvalActividades;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaEvalActividadesInterface {
    public void crear(EvalActividades evalCompetencias);
    public void editar(EvalActividades evalCompetencias);
    public void borrar(EvalActividades evalCompetencias);

    public List<EvalActividades> consultarEvalActividades();
    public EvalActividades consultarEvalActividad(BigInteger secuencia);
    public BigInteger contarEvalPlanesDesarrollosEvalActividad(BigInteger secuencia);
    public BigInteger contarCapNecesidadesEvalActividad(BigInteger secuencia) ;
    public BigInteger contarCapBuzonesEvalActividad(BigInteger secuencia);
}
