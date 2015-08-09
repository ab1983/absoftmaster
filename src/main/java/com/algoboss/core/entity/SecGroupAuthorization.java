/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllSecGroupAuthorization",
       query="select t from SecGroupAuthorization t")
})
@Entity
@Table(name="sec_group_authorization")
public class SecGroupAuthorization implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="SecGroupAuthorization",sequenceName="sequence_sec_group_authorization",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SecGroupAuthorization")
    @Column(name="group_authorization_id")
    private Long groupAuthorizationId;
    @ManyToOne()
    @JoinColumn(name="group_id")    
    private SecGroup group = new SecGroup();    
    /*
    @ManyToOne()
    @JoinColumn(name="service_contract_id",nullable=false)    
    private AdmServiceContract serviceContract = new AdmServiceContract();    
    @OneToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)   
    @JoinColumn(name="authorization_id",nullable=false)
    private SecAuthorization authorization = new SecAuthorization();  
    */
    
    
}
