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
public interface AdministrarEvalActividadesInterface {

    public void modificarEvalActividades(List<EvalActividades> listaEvalActividades);

    public void borrarEvalActividades(List<EvalActividades> listaEvalActividades);

    public void crearEvalActividades(List<EvalActividades> listaEvalActividades);

    public List<EvalActividades> consultarEvalActividades();

    public EvalActividades consultarEvalActividad(BigInteger secEvalActividades);

    public BigInteger contarCapBuzonesEvalActividad(BigInteger secEvalActividades);

    public BigInteger contarCapNecesidadesEvalActividad(BigInteger secEvalActividades);

    public BigInteger contarEvalPlanesDesarrollosEvalActividad(BigInteger secEvalActividades);
}
