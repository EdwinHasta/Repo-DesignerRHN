/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.EvalDimensiones;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarEvalDimensionesInterface {

    public void modificarEvalDimensiones(List<EvalDimensiones> listTiposEntidadesModificadas);

    public void borrarEvalDimensiones(EvalDimensiones tipoCentroCosto);

    public void crearEvalDimensiones(EvalDimensiones tiposCentrosCostos);

    public EvalDimensiones buscarEvalDimension(BigInteger secTipoCentrosCostos);

    public List<EvalDimensiones> mostrarEvalDimensiones();

    public BigInteger verificarEvalPlanillas(BigInteger secuenciaTiposAuxilios);
}
