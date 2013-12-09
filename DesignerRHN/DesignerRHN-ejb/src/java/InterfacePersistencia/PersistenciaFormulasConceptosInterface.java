package InterfacePersistencia;

import Entidades.FormulasConceptos;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaFormulasConceptosInterface {

    public boolean verificarExistenciaConceptoFormulasConcepto(BigInteger secConcepto);

    public List<FormulasConceptos> formulasConcepto(BigInteger secConcepto);

    public boolean verificarFormulaCargue_Concepto(BigInteger secConcepto, BigInteger secFormula);

    public void crear(FormulasConceptos conceptos);

    public void editar(FormulasConceptos conceptos);

    public void borrar(FormulasConceptos conceptos);
    
    public List<FormulasConceptos> buscarFormulasConceptos();
}
