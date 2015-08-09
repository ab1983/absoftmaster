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
       name="findAllSecGroup",
       query="select t from SecGroup t")
})
@Entity
@Table(name="sec_group")
public class SecGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="SecGroup",sequenceName="sequence_sec_group",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SecGroup")
    @Column(name="group_id")
    private Long groupId;    
    private String name;
    private String description;
    private boolean inactive;
    
}
