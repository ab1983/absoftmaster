/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.MethodExpression;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.algoboss.app.entity.DevEntityClass;
import com.algoboss.app.entity.DevEntityObject;
import com.algoboss.app.entity.DevEntityPropertyDescriptor;
import com.algoboss.app.entity.DevReportFieldContainer;
import com.algoboss.app.entity.DevReportFieldOptions;
import com.algoboss.app.entity.DevReportRequirement;
import com.algoboss.app.entity.DevRequirement;
import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.dao.UsuarioDao;
import com.algoboss.core.entity.AdmCompany;
import com.algoboss.core.entity.AdmContract;
import com.algoboss.core.entity.AdmInstantiatesSite;
import com.algoboss.core.entity.AdmRepresentative;
import com.algoboss.core.entity.AdmService;
import com.algoboss.core.entity.AdmServiceContract;
import com.algoboss.core.entity.AdmServiceModuleContract;
import com.algoboss.core.entity.SecUser;
import com.algoboss.core.entity.SecUserAuthorization;
import com.algoboss.core.face.GerLoginBean;
import com.algoboss.core.util.AlgoUtil;
import com.algoboss.core.util.PrivateKey;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Agnaldo
 */
@WebServlet(name = "UpdateCheck", urlPatterns = {"/update"})
public class UpdateCheck extends HttpServlet {
    @Inject
    protected BaseDao baseDao;
	protected void service (HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
		StringWriter sw = new StringWriter();
		boolean newLicense = false;
		try {
			String line = null;
			StringBuffer jb = new StringBuffer();
			char[] chars = new char[request.getContentLength()];
			BufferedReader reader = request.getReader();
		    reader.read(chars);	
		    String param = new String(chars);
			System.out.println(new String(chars));
			Gson gson1 = new GsonBuilder()
		    //.serializeNulls()
		    .create(); 
			Gson gson2 = new GsonBuilder()
			.setPrettyPrinting()
		    .setExclusionStrategies(new ExclusionStrategy() { 
		    	
		    	List<FieldAttributes> fieldList = new ArrayList<FieldAttributes>();
		    	FieldAttributes lastField = null;
		        public boolean shouldSkipClass(Class<?> clazz) {
		        	System.out.println("##########"+clazz.getSimpleName());
		            return false;
		        } 

				@Override
				public boolean shouldSkipField(FieldAttributes f) {
					boolean skip = false;
						
					try {
						Field f2 = (Field)f.getClass().getDeclaredField("field");
						f2.setAccessible(true);
						Field f3 = (Field)f2.get(f);
						//f3.setAccessible(true);
						Field f4 = f3.getClass().getDeclaredField("slot");
						f4.setAccessible(true);
						Integer slot = (Integer)f4.get(f3);
						if(slot == 1){
							if(fieldList.isEmpty() || (!fieldList.get(fieldList.size()-1).getDeclaringClass().isAssignableFrom(f.getDeclaringClass())
									&& !fieldList.get(fieldList.size()-1).getName().equals(f.getName()))){
								fieldList.add(f);
							}							
						}
						skip = checkRules(f);
						if(slot == f.getDeclaringClass().getDeclaredFields().length-1){
							while(!fieldList.isEmpty() && fieldList.get(fieldList.size()-1).getDeclaringClass().isAssignableFrom(f.getDeclaringClass())){
								fieldList.remove(fieldList.size()-1);
							}							
						}
						
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
					// TODO Auto-generated method stub
					System.out.println(f.getDeclaringClass().getSimpleName()+"-"+f.getName()+"-"+f.getDeclaredClass().getSimpleName());
					return skip;
				}
				public boolean checkRules(FieldAttributes f){
					if(f.getDeclaringClass().isAssignableFrom(AdmServiceContract.class)){
						if(f.getDeclaredClass().isAssignableFrom(AdmServiceModuleContract.class)){
							return true; 
						} 						
					}
					if(f.getDeclaringClass().isAssignableFrom(DevEntityPropertyDescriptor.class)){
						if(f.getDeclaredClass().isAssignableFrom(DevEntityClass.class) && f.getName().equals("entityClassParent")){
							return true;
						}				
					}	
					if(f.getDeclaringClass().isAssignableFrom(AdmContract.class)){
						if(f.getName().equals("instantiatesSiteList")){
							return false;
						}else{
							return false;
						}
					}		
					if(f.getDeclaringClass().isAssignableFrom(AdmService.class)){
						if(f.getDeclaredClass().isAssignableFrom(DevRequirement.class)){
							//return true; 
						}						
						if(f.getDeclaredClass().isAssignableFrom(DevReportRequirement.class)){
							//return true; 
						}						
					}
					if(f.getDeclaringClass().isAssignableFrom(DevRequirement.class)){
						if(f.getDeclaredClass().isAssignableFrom(DevRequirement.class)){
							return true;
						}						
						if(f.getDeclaredClass().isAssignableFrom(AdmContract.class)){
							return true;
						}	
						if(f.getDeclaredClass().isAssignableFrom(AdmService.class)){
							return true; 
						}	
						if(f.getDeclaredClass().isAssignableFrom(DevReportFieldContainer.class)){
							return true; 
						}		
								
					}
					if(f.getDeclaringClass().isAssignableFrom(DevReportRequirement.class)){
						if(f.getDeclaredClass().isAssignableFrom(AdmContract.class)){
							return true;
						}							
						if(f.getDeclaredClass().isAssignableFrom(AdmService.class)){
							return true; 
						}							
					}
					if(f.getDeclaringClass().isAssignableFrom(DevReportFieldContainer.class)){
						if(f.getDeclaredClass().isAssignableFrom(DevReportFieldOptions.class)){
							return true;
						}														
					}
					if(f.getDeclaringClass().isAssignableFrom(DevReportFieldOptions.class)){
						if(f.getDeclaredClass().isAssignableFrom(DevEntityPropertyDescriptor.class)){
							return true;
						}														
					}					
												
					if(f.getDeclaringClass().isAssignableFrom(AdmInstantiatesSite.class)){
						if(f.getDeclaredClass().isAssignableFrom(AdmContract.class)){
							return true;
						}					
					}
					
					/*
					if(!fieldList.isEmpty() && fieldList.get(fieldList.size()-1).getDeclaringClass().isAssignableFrom(AdmInstantiatesSite.class)){
						if(f.getDeclaringClass().isAssignableFrom(AdmContract.class)){
							if(f.getName().equals("contractId")){
								return false;
							}else{
								return true;
							}
						}						
					}
					*/	
					if(f.getDeclaringClass().isAssignableFrom(AdmRepresentative.class)){
						if(f.getName().equals("contractList")){
							return true;
						}							
					}
					if(f.getDeclaringClass().isAssignableFrom(AdmRepresentative.class)){
						if(f.getName().equals("representativeList")){
							return true;
						}							
					}					
					if(f.getDeclaringClass().isAssignableFrom(AdmRepresentative.class)){
						if(f.getDeclaredClass().isAssignableFrom(AdmRepresentative.class)){
							return true;
						}						
					}		
					if(f.getDeclaringClass().isAssignableFrom(AdmRepresentative.class)){
						if(f.getDeclaredClass().isAssignableFrom(SecUser.class)){
							return true;
						}						
					}							
					
					if(f.getDeclaringClass().isAssignableFrom(SecUser.class)){
						if(f.getDeclaredClass().isAssignableFrom(AdmContract.class)){
							return true;
						}						
					}	
					if(f.getDeclaringClass().isAssignableFrom(SecUserAuthorization.class)){
						if(f.getDeclaredClass().isAssignableFrom(AdmServiceContract.class)){
							return true; 
						}						
					}		

					if(f.getDeclaringClass().isAssignableFrom(DevEntityClass.class)){
						if(f.getDeclaredClass().isAssignableFrom(AdmInstantiatesSite.class)){
							return true;  
						}					
					}					
					if(f.getDeclaredClass().isAssignableFrom(DevEntityObject.class)){
						return true; 
					}	
					if(f.getDeclaringClass().isAssignableFrom(DevReportFieldOptions.class)){
						if(f.getName().equals("fieldContainerParent")){
							return true;
						}						
					}					 
					if(f.getDeclaringClass().isAssignableFrom(DevEntityClass.class)){
						if(f.getName().equals("entityPropertyDescriptorParentList")){
							return true;
						}						
					}						
					if(f.getDeclaringClass().isAssignableFrom(AdmService.class)){
						if(f.getName().equals("serviceContractList")){
							return true;
						}						
					}	
					if(f.getName().equals("entityObjectList")){
						return true;
					}						
					if(f.getDeclaredClass().isAssignableFrom(AdmContract.class)){
						//return true;
					}
					return false;					
				}
		     })
		    //.serializeNulls()
		    .create();			
			SecUser userToUp = gson1.fromJson(param, new SecUser().getClass());
			SecUser user = new SecUser();
			user.setEmail(userToUp.getEmail());
			user.setPassword(userToUp.getPassword());
			//FacesContext facesContext = FacesContext.getCurrentInstance();
			//String methodStr = "#{gerLoginBean.doLogin()}";
			//MethodExpression method = facesContext.getApplication().getExpressionFactory().createMethodExpression(facesContext.getELContext(), String.valueOf(methodStr), null, new Class<?>[]{});
			//method.invoke(facesContext.getELContext(), null);		
			user = new UsuarioDao(baseDao).findByEmailAndPassword(user.getEmail().toUpperCase(), user.getPassword());
			StringWriter actionWriter = new StringWriter();
			PrintWriter action = new PrintWriter(actionWriter);
			boolean toUpdateContract = true;
			AdmContract contract = user!=null?user.getContract():null;
			if(contract!=null && contract.getSystemSchema()!=null && !contract.getSystemSchema().isEmpty()){
				if(!user.isInactive()){
					Gson gsonPk = new Gson();
					PrivateKey privateKey = gsonPk.fromJson(AlgoUtil.decrypt(DatatypeConverter.parseHexBinary(userToUp.getContract().getPrivateKey())), PrivateKey.class);
					if(contract.getPrivateKey()==null || contract.getPrivateKey().isEmpty() || (privateKey.getExpiration() != null && new Date().after(privateKey.getExpiration()))){
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.MONTH, 1);
						privateKey.setExpiration(cal.getTime());
						Gson gson = new Gson();
						String pkJson = gson.toJson(privateKey);
						String pkEnc = "";
						try {
							pkEnc = DatatypeConverter.printHexBinary(AlgoUtil.encrypt(pkJson));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
						contract.setPrivateKey(pkEnc);
						newLicense = true;
					}
					else if(!contract.getPrivateKey().equals(userToUp.getContract().getPrivateKey())){
						toUpdateContract = false;
						//SOLICITAR NOVA PRIVATE KEY
						System.out.println("### ERROR TO COMPARE PRIVATE KEY SERVER!");
					}
				}else{
					toUpdateContract = false;
					System.out.println("### USER INACTIVE!");
				}
			}else{
				System.out.println("### UPDATE NOT AVAILABLE!");
				toUpdateContract = false;
			}
			if(toUpdateContract){
				contract.setSystemSchema(null);		
				AdmContract contractToSend = baseDao.clone(contract);
				contractToSend.setServiceModuleContractList(generateModules(contract));
				gson2.toJson(contractToSend,sw);
				action.println("var GsonBuilder = com.google.gson.GsonBuilder;");
				action.println("var AdmContract = com.algoboss.core.entity.AdmContract;");
				action.println("var contrato = "+sw.toString()+";");
				action.println("var gson = new GsonBuilder().setPrettyPrinting().create();");
				action.println("var baseDao = login.getBaseDao();");
				action.println("var admContract = gson.fromJson(JSON.stringify(contrato), new AdmContract().getClass());");
				//action.println("var admContract = baseDao.toManaged(admContractToUp);");
				//action.println("login.getBaseBean().copyObject(admContractToUp, admContract);");
				//action.println("admContract.setPrivateKey(admContractToUp.getPrivateKey());");
				//action.println("for(var x=0;x<100;x++){println(x+' oi!!!!');}");
				action.println("var moduleList = admContract.getServiceModuleContractList();");
				action.println("admContract.setServiceModuleContractList(null);");
				action.println("login.getUser().setContract(admContract);");
				action.println("baseDao.save(admContract)");
				action.println("admContract.setServiceModuleContractList(moduleList);");
				action.println("baseDao.save(admContract)");				
				if(newLicense){
					try {
						List<String[]> destin = new ArrayList<String[]>();
						destin.add(new String[] { "agnaldo_luiz@msn.com", "Agnaldo luiz." });
						destin.add(new String[] { "agnaldo.luiz@algoboss.com", "Agnaldo luiz." });
						String msg = contract.getPrivateKey();
						AlgoUtil.sendEmail(destin, "webmaster@algoboss.com", "AlgoBoss", "AlgoBoss - Ativação de Licença", msg, null);
					} catch (Exception ex) {
						Logger.getLogger(AlgoUtil.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
				
				//action.append("var FacesContext = 	javax.faces.context.FacesContext;");
				//action.append("var navigationHandler = FacesContext.getCurrentInstance().getApplication().getNavigationHandler();");
				//action.append("navigationHandler.handleNavigation(FacesContext.getCurrentInstance(), null, 'update.xhtml?faces-redirect=true');");
				try {
					baseDao.save(contract);	
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
	        Calendar cal = Calendar.getInstance();
	        cal.set(2015, 7, 27);
	        if(new Date().after(cal.getTime())){
	        	//System.exit(0);
	        }  			
			response.setContentType("application/javascript");
			PrintWriter out = response.getWriter();
			if(!actionWriter.toString().isEmpty()){
				out.println(actionWriter.toString());
			}
			out.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	private List<AdmServiceModuleContract> generateModules(AdmContract contract){
		List<AdmServiceModuleContract> moduleListNew = new ArrayList<AdmServiceModuleContract>();
        List<AdmServiceModuleContract> moduleList = contract.getServiceModuleContractList();
        for (AdmServiceModuleContract admServiceModuleContract : moduleList) {
        	if(admServiceModuleContract.isInactive()){
        		continue;
        	}
        	moduleListNew.add(admServiceModuleContract);
        	List<AdmServiceContract> serviceContractListNew = new ArrayList<AdmServiceContract>();
            List<AdmServiceContract> serviceContractList = admServiceModuleContract.getServiceContractList();
            for (AdmServiceContract admServiceContract : serviceContractList) {
            	if(admServiceContract.isInactive() || !admServiceContract.getService().getModule().matches("APP|REP")){
            		continue;
            	}
            	serviceContractListNew.add(admServiceContract);
            }
            admServiceModuleContract.setServiceContractList(serviceContractListNew);
        }		
        return moduleListNew;
	}
 }
