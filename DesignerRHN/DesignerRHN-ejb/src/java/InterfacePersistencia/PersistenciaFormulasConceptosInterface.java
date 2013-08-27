package InterfacePersistencia;

import Entidades.FormulasConceptos;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaFormulasConceptosInterface {
    public boolean verificarExistenciaConceptoFormulasConcepto(BigInteger secConcepto);
    public List<FormulasConceptos> formulasConcepto(BigInteger secConcepto); 
    public boolean verificarFormulaCargue_Concepto(BigInteger secConcepto, BigInteger secFormula);
}
