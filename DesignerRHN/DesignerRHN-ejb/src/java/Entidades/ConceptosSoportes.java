/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "CONCEPTOSSOPORTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptosSoportes.findAll", query = "SELECT c FROM ConceptosSoportes c"),
    @NamedQuery(name = "ConceptosSoportes.findBySecuencia", query = "SELECT c FROM ConceptosSoportes c WHERE c.secuencia = :secuencia"),
    @NamedQuery(name = "ConceptosSoportes.findByTipo", query = "SELECT c FROM ConceptosSoportes c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "ConceptosSoportes.findBySubgrupo", query = "SELECT c FROM ConceptosSoportes c WHERE c.subgrupo = :subgrupo"),
    @NamedQuery(name = "ConceptosSoportes.findByComentario", query = "SELECT c FROM ConceptosSoportes c WHERE c.comentario = :comentario"),
    @NamedQuery(name = "ConceptosSoportes.findByConsecutivo", query = "SELECT c FROM ConceptosSoportes c WHERE c.consecutivo = :consecutivo")})
public class ConceptosSoportes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TIPO")
    private String tipo;
    @Size(max = 20)
    @Column(name = "SUBGRUPO")
    private String subgrupo;
    @Size(max = 40)
    @Column(name = "COMENTARIO")
    private String comentario;
    @Column(name = "CONSECUTIVO")
    private Short consecutivo;
    @JoinColumn(name = "TIPOENTIDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposEntidades tipoentidad;
    @JoinColumn(name = "OPERANDO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Operandos operando;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Conceptos concepto;

    public ConceptosSoportes() {
    }

    public ConceptosSoportes(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public ConceptosSoportes(BigDecimal secuencia, String tipo) {
        this.secuencia = secuencia;
        this.tipo = tipo;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubgrupo() {
        return subgrupo;
    }

    public void setSubgrupo(String subgrupo) {
        this.subgrupo = subgrupo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Short getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Short consecutivo) {
        this.consecutivo = consecutivo;
    }

    public TiposEntidades getTipoentidad() {
        return tipoentidad;
    }

    public void setTipoentidad(TiposEntidades tipoentidad) {
        this.tipoentidad = tipoentidad;
    }

    public Operandos getOperando() {
        return operando;
    }

    public void setOperando(Operandos operando) {
        this.operando = operando;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptosSoportes)) {
            return false;
        }
        ConceptosSoportes other = (ConceptosSoportes) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ConceptosSoportes[ secuencia=" + secuencia + " ]";
    }
    
}
