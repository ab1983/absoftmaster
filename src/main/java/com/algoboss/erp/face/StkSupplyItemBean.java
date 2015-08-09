/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import javax.enterprise.context.SessionScoped;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.algoboss.core.face.BaseBean;
import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.entity.CpgTipoDespesa;
import com.algoboss.erp.entity.StkBrand;
import com.algoboss.erp.entity.StkSupplyCategory;
import com.algoboss.erp.entity.StkSupplyItem;
import com.algoboss.erp.entity.StkUnitOfMeasure;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class StkSupplyItemBean extends GenericBean<StkSupplyItem> {
    @Inject
    private StkSupplyCategoryBean supplyCategoryBean;
    @Inject
    private StkBrandBean brandBean;
    @Inject
    private StkUnitOfMeasureBean unitOfMeasureBean;
    @Inject
    private CpgTipoDespesaBean tipoDespesaBean;
    private Converter supplyCategoryConverter;
    private Converter brandConverter;
    private Converter unitOfMeasureConverter;
    private Converter tipoDespesaConverter;
    /**
     * Creates a new instance of CpgTipoDespesaBean
     */
    public StkSupplyItemBean() {        
        super.url = "views/cadastro/stk/supply-item/supplyItemList.xhtml";
        super.namedFindAll = "findAllStkSupplyItem";
        super.type = StkSupplyItem.class;
        super.urlForm = "supplyItemForm.xhtml";
        super.subtitle = "Cadastro | Estoque | Item de Suprimento";
    }

    public Converter getBrandConverter() {
        return brandConverter;
    }

    public Converter getSupplyCategoryConverter() {
        return supplyCategoryConverter;
    }

    public Converter getTipoDespesaConverter() {
        return tipoDespesaConverter;
    }

    public Converter getUnitOfMeasureConverter() {
        return unitOfMeasureConverter;
    }
    
    @Override
    public void indexBean(Long nro) throws Throwable {
        super.indexBean(nro);
        supplyCategoryBean.doBeanList();
        supplyCategoryConverter = BaseBean.getConverter(supplyCategoryBean.getBeanList(),StkSupplyCategory.class , "supplyCategoryId");        
        brandBean.doBeanList();
        brandConverter = BaseBean.getConverter(brandBean.getBeanList(),StkBrand.class , "brandId");
        unitOfMeasureBean.doBeanList();
        unitOfMeasureConverter = BaseBean.getConverter(unitOfMeasureBean.getBeanList(),StkUnitOfMeasure.class , "unitOfMeasureId");
        tipoDespesaBean.doBeanList();        
        tipoDespesaConverter = BaseBean.getConverter(tipoDespesaBean.getBeanList(),CpgTipoDespesa.class , "tipoDespesaId");
    }

    @Override
    public void indexBeanNewWin(Long nro) throws Throwable {
        super.indexBeanNewWin(nro);
        supplyCategoryBean.doBeanList();
        supplyCategoryConverter = BaseBean.getConverter(supplyCategoryBean.getBeanList(),StkSupplyCategory.class , "supplyCategoryId");        
        brandBean.doBeanList();
        brandConverter = BaseBean.getConverter(brandBean.getBeanList(),StkBrand.class , "brandId");
        unitOfMeasureBean.doBeanList();
        unitOfMeasureConverter = BaseBean.getConverter(unitOfMeasureBean.getBeanList(),StkUnitOfMeasure.class , "unitOfMeasureId");
        tipoDespesaBean.doBeanList();        
        tipoDespesaConverter = BaseBean.getConverter(tipoDespesaBean.getBeanList(),CpgTipoDespesa.class , "tipoDespesaId");
    }

    @Override
    public void doBeanList() {
        super.doBeanList();
        super.getBean().setCode(Integer.toString(super.getBeanList().size()+1));
    }


    
}
