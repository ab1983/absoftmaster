/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.integration.small.face;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperRunManager;

import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.face.GenericBean;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class FinReportBean extends GenericBean<Object> {

    @Inject
    private BaseDao baseDao;
    private Date startDate;
    private Date endDate;

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public FinReportBean() {
        super.url = "views/integration/small/report/reportList.xhtml";
        super.namedFindAll = "findAllPurOrder";
        super.type = Object.class;
        super.urlForm = "reportForm.xhtml";
        super.subtitle = "Small | Relatório";
    }

    @Override
    public void indexBean(Long nro) {
        try {
            super.indexBean(nro);
        } catch (Throwable ex) {
            Logger.getLogger(FinReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initBean() {
        super.initBean();
        //clienteList = (List<GerCliente>) (Object) baseDao.findAll("findAllGerCliente");
        //tipoReceitaList = (List<CrcTipoReceita>) (Object) baseDao.findAll("findAllCrcTipoReceita");
        //centroCustoList = (List<CentroCusto>) (Object) baseDao.findAll("findAllCentroCusto");
        //gerTipoDocumentoList = (List<GerTipoDocumento>) (Object) baseDao.findAll("findAllGerTipoDocumento");

    }

    @Override
    public void doBeanList() {
        try {
            //super.doBeanList();
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = "jdbc:firebirdsql:algomaster.zapto.org/3050:C:\\Program Files (x86)\\SmallSoft\\Small Commerce\\SMALL.GDB";
            Connection con = DriverManager.getConnection(url, "SYSDBA", "masterkey");
            FacesContext fc = FacesContext.getCurrentInstance();
            ServletContext context = (ServletContext) fc.getExternalContext().getContext();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            File relatorioJasper = new File(context.getRealPath("/WEB-INF/classes/com/algoboss/integration/small/report/reportContasPagarSmall.jasper"));

            // parâmetros, se houver
            Map param = new HashMap();
            param.put("START_DATE", startDate);
            param.put("END_DATE", endDate);
            byte[] bytes = null;

            try {

                bytes =
                        JasperRunManager.runReportToPdf(relatorioJasper.getPath(), param, con);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
            if (bytes != null && bytes.length > 0) {
                try {
                    // Envia o relatório em formato PDF para o browser
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    response.setHeader("Content-disposition", "inline; filename=reportContasPagarSmall");
                    //response.getWriter().write("<head><title>Relatório Small</title></head>");
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(FinReportBean.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    fc.responseComplete();  
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FinReportBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FinReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanForm() {
        super.doBeanForm();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
