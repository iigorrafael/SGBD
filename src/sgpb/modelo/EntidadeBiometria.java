/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sgpb.modelo;

import com.futronic.SDKHelper.*;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author ALISSON
 */
@Entity
@Table(name="biometria")
public class EntidadeBiometria implements Serializable {
    private static final long serialVersionUID = 1L;

    public EntidadeBiometria() {
        super();
          // Geração de um unico identificador
        chave = new byte[16];
        java.util.UUID guid = java.util.UUID.randomUUID();
        long itemHigh = guid.getMostSignificantBits();
        long itemLow = guid.getLeastSignificantBits();
        for( int i = 7; i >= 0; i-- )
        {
            chave[i]   = (byte)(itemHigh & 0xFF);
            itemHigh >>>= 8;
            chave[8+i] = (byte)(itemLow & 0xFF);
            itemLow >>>= 8;
        }
        template = null;
        
    }
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @OneToOne
    private Pessoa pessoa;
    
    private byte[] chave;
    
    @Lob
    private byte[] template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   
    
    //ANOTAÇÃO PARA A BIOMETRIA
    
    public byte[] getTemplate()
    {
        return template;
    }

   
    public void setTemplate( byte[] value)
    {
        template = value;
    }

 
    public byte[] getUniqueID()
    {
        return getChave();
    }
    
    
    public byte[] getChave() {
        return chave;
    }

    public void setChave(byte[] key) {
        this.chave = key;
    }
    
     public FtrIdentifyRecord getFtrIdentifyRecord()
    {
        FtrIdentifyRecord r = new FtrIdentifyRecord();
        r.m_KeyValue = getChave();
        r.m_Template = template;
        
        return r;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
     
     

    
}
