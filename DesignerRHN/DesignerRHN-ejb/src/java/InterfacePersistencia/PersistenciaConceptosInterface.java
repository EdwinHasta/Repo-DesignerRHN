/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Conceptos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaConceptosInterface {
    public void crear(Conceptos concepto);
    public void editar(Conceptos concepto);
    public void borrar(Conceptos concepto);
    public Conceptos buscarConcepto(Object id);
    public List<Conceptos> buscarConceptos();
    public void revisarConcepto(int codigoConcepto);
    public boolean verificarCodigoConcepto(BigInteger codigoConcepto);
    public Conceptos validarCodigoConcepto(BigInteger codigoConcepto, BigInteger secEmpresa);
}
