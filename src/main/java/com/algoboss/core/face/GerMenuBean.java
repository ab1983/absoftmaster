/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.face;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.menu.MenuModel;

import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.entity.AdmContract;
import com.algoboss.core.entity.AdmRepresentative;
import com.algoboss.core.entity.AdmService;
import com.algoboss.core.entity.AdmServiceContract;
import com.algoboss.core.entity.AdmServiceModuleContract;
import com.algoboss.core.entity.SecUser;
import com.algoboss.core.entity.SecUserAuthorization;


/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class GerMenuBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private BaseDao baseDao;    
    private MenuModel model;
    private List<AdmService> serviceList;
    private AdmServiceModuleContract serviceModuleContract;
    @Inject
    private GerLoginBean loginMenuBean;
    private List<SecUserAuthorization> userAuthorizationList = new ArrayList();

    public MenuModel getModel() {
        return model;
    }

    public AdmServiceModuleContract getServiceModuleContract() {
        return serviceModuleContract;
    }

    public List<SecUserAuthorization> getUserAuthorizationList() {
        return userAuthorizationList;
    }

    //@PostConstruct
    public GerMenuBean() {


    }

    private void populateContract() {
        serviceList = new ArrayList();
        serviceList.add(new AdmService("Cadastro>Usuário", "Cadastro de Usuário", "Cadastro", "#{secUserBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Cadastro>Autorização Usuário", "Libera acesso ao usuário", "Cadastro", "#{secUserAuthorizationBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Cadastro>Cliente", "Libera acesso ao usuário", "Cadastro", "#{gerClienteBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Cadastro>Fornecedor", "Libera acesso ao usuário", "Cadastro", "#{gerFornecedorBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Cadastro>Tipo de Documento", "Libera acesso ao usuário", "Cadastro", "#{gerTipoDocumentoBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Cadastro>Centro de Custo", "Libera acesso ao usuário", "Cadastro", "#{centroCustoBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Cadastro>Suprimentos>Categoria do Produto", "Libera acesso ao usuário", "Cadastro", "#{stkSupplyCategoryBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Cadastro>Suprimentos>Unidade de Medida", "Libera acesso ao usuário", "Cadastro", "#{stkUnitOfMeasureBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Cadastro>Suprimentos>Marca", "Libera acesso ao usuário", "Cadastro", "#{stkBrandBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Cadastro>Suprimentos>Produto", "Libera acesso ao usuário", "Cadastro", "#{stkSupplyItemBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Cadastro>CPG - Tipo de Despesa", "Libera acesso ao usuário", "Cadastro", "#{tipoDespesaBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Cadastro>CRC - Tipo de Receita", "Libera acesso ao usuário", "Cadastro", "#{crcTipoReceitaBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Cadastro>Obras", "Libera acesso ao usuário", "Cadastro", "#{gobObraBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Financeiro>Contas a pagar>Lançamento Docto", "Libera acesso ao usuário", "Financeiro", "#{cpgDocumentoBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Financeiro>Contas a receber>Lançamento Docto", "Libera acesso ao usuário", "Financeiro", "#{crcDocumentoBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Suprimentos>Estoque>Entrada", "Libera acesso ao usuário", "Suprimentos", "#{stkInputDocumentBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Suprimentos>Estoque>Saída", "    Libera acesso ao usuário", "Suprimentos", "#{stkOutputDocumentBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Suprimentos>Compra>Solicitação", "Libera acesso ao usuário", "Suprimentos", "#{purSolicitationBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Suprimentos>Compra>Cotação", "Libera acesso ao usuário", "Suprimentos", "#{purQuotationBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Suprimentos>Compra>Pedido", "Libera acesso ao usuário", "Suprimentos", "#{purOrderBean .indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Suprimentos>Compra>Aprovação", "Libera acesso ao usuário", "Suprimentos", "#{purApprovalBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Suprimentos>Compra>Parâmetros", "Libera acesso ao usuário", "Suprimentos", "#{purParameterBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Operacional>Cronograma de Obras", "Libera acesso ao usuário", "Gerenciamento de Obras", "#{grtCronogramaBean.indexBean()}", "", "1", new Date()));
        serviceList.add(new AdmService("Operacional>Aquisição Imovel", "Libera acesso ao usuário", "Gerenciamento de Obras", "#{gobAquisicaoImovelBean.indexBean()}", "", "1", new Date()));

        AdmContract contract = new AdmContract();
        AdmRepresentative representative = new AdmRepresentative();
        SecUser user = new SecUser();
        user.setAdministrator(true);
        user.setEmail("agnaldo_luiz@msn.com");
        user.setName("Agnaldo Luíz");
        user.setLogin("AGNALDO");
        user.setPassword("1");

        representative.setCompany("AlgoBoss");
        representative.setContact("11970660488");
        representative.setUser(user);
        contract.setRepresentative(representative);
        serviceModuleContract = new AdmServiceModuleContract();
        for (int i = 0; i < serviceList.size(); i++) {
            AdmService admService = serviceList.get(i);
            //baseDao.save(admService);
            admService.setServiceId(Integer.valueOf(i).longValue());
            AdmServiceContract serviceContract = new AdmServiceContract();
            serviceContract.setService(admService);
            //serviceContractList.add(serviceContract);
            serviceModuleContract.getServiceContractList().add(serviceContract);
        }
        try {
            serviceModuleContract.setExpectedAmount(2);
            contract.getServiceModuleContractList().add(serviceModuleContract);
            baseDao.save(contract);
        } catch (Throwable ex) {
            Logger.getLogger(GerMenuBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        userAuthorizationList = new ArrayList();
        for (AdmServiceContract admServiceContract : serviceModuleContract.getServiceContractList()) {
            SecUserAuthorization userAuthorization = new SecUserAuthorization();
            userAuthorization.setServiceContract(admServiceContract);
            if (userAuthorization.getServiceContract().getService().getName().contains("Cliente")) {
                userAuthorization.getAuthorization().setReadOnly(true);
            }
        }

    }
}
