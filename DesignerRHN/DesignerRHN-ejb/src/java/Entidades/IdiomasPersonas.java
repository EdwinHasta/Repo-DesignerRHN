/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "IDIOMASPERSONAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IdiomasPersonas.findAll", query = "SELECT i FROM IdiomasPersonas i")})
public class IdiomasPersonas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "HABLA")
    private BigInteger habla;
    @Column(name = "LECTURA")
    private BigInteger lectura;
    @Column(name = "ESCRITURA")
    private BigInteger escritura;
    @JoinColumn(name = "PERSONA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Personas persona;
    @JoinColumn(name = "IDIOMA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Idiomas idioma;

    public IdiomasPersonas() {
    }

    public IdiomasPersonas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getHabla() {
        return habla;
    }

    public void setHabla(BigInteger habla) {
        this.habla = habla;
    }

    public BigInteger getLectura() {
        return lectura;
    }

    public void setLectura(BigInteger lectura) {
        this.lectura = lectura;
    }

    public BigInteger getEscritura() {
        return escritura;
    }

    public void setEscritura(BigInteger escritura) {
        this.escritura = escritura;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public Idiomas getIdioma() {
        return idioma;
    }

    public void setIdioma(Idiomas idioma) {
        this.idioma = idioma;
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
        if (!(object instanceof IdiomasPersonas)) {
            return false;
        }
        IdiomasPersonas other = (IdiomasPersonas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.IdiomasPersonas[ secuencia=" + secuencia + " ]";
    }
    
}
