/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.dao.BaseDao;
import com.algoboss.erp.entity.CbcPlanejamentoOrcamentario;
import com.algoboss.erp.entity.GerCentroCusto;
import com.algoboss.erp.entity.CpgTipoDespesa;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Agnaldo
 */
@ManagedBean
@SessionScoped
public class CbcPlanejamentoOrcamentarioBean extends GenericBean<CbcPlanejamentoOrcamentario> {
    @Inject
    private BaseDao baseDao;
    private List<CpgTipoDespesa> tipoDespesaList;
    private List<GerCentroCusto> centroCustoList;
    private List<String> centroCustoIdFiltroList;
    private List<Date> columns;
    private List<CbcPlanejamentoOrcamentario[]> dynamicBeanList;
    private String repetirAte;
    private Date vigencia;
    private int meses;

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public CbcPlanejamentoOrcamentarioBean() {
        super.url = "views/caixa-bancos/planejamento-orcamentario/cbcPlanejamentoOrcamentarioList.xhtml";
        super.namedFindAll = "findAllCbcPlanejamentoOrcamentario";
        super.type = CbcPlanejamentoOrcamentario.class;
        super.urlForm = "cbcPlanejamentoOrcamentarioForm.xhtml";
        super.subtitle = "Financeiro | Caixa e Bancos | Planejamento Orçamentário";
    }

    public List<Date> getColumns() {
        return columns;
    }

    public List<CbcPlanejamentoOrcamentario[]> getDynamicBeanList() {
        return dynamicBeanList;
    }

    public List<GerCentroCusto> getCentroCustoList() {
        return centroCustoList;
    }

    public List<String> getCentroCustoIdFiltroList() {
        return centroCustoIdFiltroList;
    }

    public void setCentroCustoIdFiltroList(List<String> centroCustoIdFiltroList) {
        this.centroCustoIdFiltroList = centroCustoIdFiltroList;
    }

    public String getRepetirAte() {
        return repetirAte;
    }

    public void setRepetirAte(String repetirAte) {
        this.repetirAte = repetirAte;
    }

    public int getMeses() {
        return meses;
    }

    public Date getVigencia() {
        return vigencia;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }

    @Override
    public void indexBean(Long nro) {
        try {
            tipoDespesaList = (List<CpgTipoDespesa>) (Object) baseDao.findAll("findAllTipoDespesa");
            centroCustoList = (List<GerCentroCusto>) (Object) baseDao.findAll("findAllCentroCusto");
            centroCustoIdFiltroList = new ArrayList<String>();  
            for (GerCentroCusto cc : centroCustoList) {
                centroCustoIdFiltroList.add(cc.getCentroCustoId().toString());                    
            }
            meses = 12;
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(Calendar.getInstance().get(Calendar.YEAR), 0, 1);
            vigencia = cal.getTime();
            populateColumns();
            super.indexBean(nro);
        } catch (Throwable ex) {
            Logger.getLogger(CbcPlanejamentoOrcamentarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanList() {
        super.doBeanList();
        populateBeanList();
    }

    @Override
    public void doBeanSave() {
        try {
            baseDao.save(bean);
            if (repetirAte != null) {
                try {
                    Date dtRepetirAte = new SimpleDateFormat("dd/MM/yyyy").parse(repetirAte);
                    for (int i = 0; i < columns.size(); i++) {
                        Date d = columns.get(i);
                        if (d.after(bean.getPeriodoVigencia()) && !d.after(dtRepetirAte)) {
                            CbcPlanejamentoOrcamentario beanClone = new CbcPlanejamentoOrcamentario();
                            beanClone.setCentroCusto(bean.getCentroCusto());
                            beanClone.setTipoDespesa(bean.getTipoDespesa());
                            beanClone.setPeriodoVigencia(d);
                            for (int k = 0; k < beanList.size(); k++) {
                                CbcPlanejamentoOrcamentario cbcPlanejamentoOrcamentario = beanList.get(k);
                                if (beanClone.getCentroCusto().equals(cbcPlanejamentoOrcamentario.getCentroCusto())
                                        && beanClone.getTipoDespesa().equals(cbcPlanejamentoOrcamentario.getTipoDespesa())
                                        && beanClone.getPeriodoVigencia().equals(cbcPlanejamentoOrcamentario.getPeriodoVigencia())) {
                                    beanClone = cbcPlanejamentoOrcamentario;
                                    break;
                                }
                            }
                            beanClone.setValor(bean.getValor());
                            baseDao.save(beanClone);
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(CbcPlanejamentoOrcamentarioBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            doBeanList();
        } catch (Throwable ex) {
            Logger.getLogger(CbcPlanejamentoOrcamentarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscar() {
        populateColumns();
        populateBeanList();
    }

    private void populateColumns() {
        columns = new ArrayList();
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(vigencia);
        for (int i = 0; i < meses; i++) {
            columns.add(cal.getTime());
            cal.add(Calendar.MONTH, 1);
        }
    }

    private void populateBeanList() {
        dynamicBeanList = new ArrayList<CbcPlanejamentoOrcamentario[]>();
        for (int h = 0; h < centroCustoList.size(); h++) {
            if(!centroCustoIdFiltroList.contains(centroCustoList.get(h).getCentroCustoId().toString())){
                continue;
            }
            for (int i = 0; i < tipoDespesaList.size(); i++) {
                CbcPlanejamentoOrcamentario[] array = new CbcPlanejamentoOrcamentario[columns.size()];
                for (int j = 0; j < array.length; j++) {
                    CbcPlanejamentoOrcamentario obj = new CbcPlanejamentoOrcamentario();
                    obj.setCentroCusto(centroCustoList.get(h));
                    obj.setTipoDespesa(tipoDespesaList.get(i));
                    obj.setPeriodoVigencia(columns.get(j));
                    obj.setValor(BigDecimal.ZERO);
                    for (int k = 0; k < beanList.size(); k++) {
                        CbcPlanejamentoOrcamentario cbcPlanejamentoOrcamentario = beanList.get(k);
                        if (obj.getCentroCusto().equals(cbcPlanejamentoOrcamentario.getCentroCusto())
                                && obj.getTipoDespesa().equals(cbcPlanejamentoOrcamentario.getTipoDespesa())
                                && obj.getPeriodoVigencia().equals(cbcPlanejamentoOrcamentario.getPeriodoVigencia())) {
                            obj = cbcPlanejamentoOrcamentario;
                            break;
                        }
                    }

                    array[j] = obj;
                }
                dynamicBeanList.add(array);
            }
        }
    }
}