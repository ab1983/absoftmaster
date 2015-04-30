/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import java.util.Date;
import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.algoboss.erp.util.ManualCDILookup;

/**
 *
 * @author Agnaldo
 */
public class LifeCycleListener extends ManualCDILookup implements PhaseListener {

	private static final long serialVersionUID = 1L;
	@Inject
	private GerLoginBean loginBean;

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();

		// if (context.isPostback()) {
		// UICommand component = findInvokedCommandComponent(context);

		// if (component != null) {
		// String methodExpression =
		// component.getActionExpression().getExpressionString();
		// It'll contain #{bean.action}.
		// }
		// }
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		// System.out.println("AFTER");
		FacesContext context = event.getFacesContext();
		updateSessionTimeout(context);
		if (event.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
			loginBean = getFacadeWithJNDI(GerLoginBean.class);
			if (loginBean != null) {
				loginBean.setReloadView(true);
			}
		}
		if (event.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
			if (loginBean != null) {
				loginBean.setReloadView(false);
			}
		}
	}

	private void updateSessionTimeout(FacesContext context) {
		try {

			UIViewRoot view = context.getViewRoot();
			boolean timeOutOk = false;
			UIComponent comp = null;
			String id = "";
			if (view != null) {
				Map<String, String> params = context.getExternalContext().getRequestParameterMap();
				String src = params.get("javax.faces.source");
				if (src != null && !src.isEmpty() && context.getPartialViewContext().isAjaxRequest()) {
					comp = view.findComponent(src);
					if (comp != null) {
						id = comp.getId();
					}
					if (!id.contains("updateActiveSession")) {
						HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
						if (session != null) {
							session.setAttribute("activeSession", true);
							session.setAttribute("lastTime", new Date().getTime());
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private UICommand findInvokedCommandComponent(FacesContext context) {
		if (context.isPostback()) {
			UICommand componentAux = null;
			try {

				UIViewRoot view = context.getViewRoot();
				if (view != null) {
					Map<String, String> params = context.getExternalContext().getRequestParameterMap();

					if (context.getPartialViewContext().isAjaxRequest()) {
						UIComponent comp = view.findComponent(params.get("javax.faces.source"));
						UICommand comm = (UICommand) comp;
						return comm;
					} else {
						for (String clientId : params.keySet()) {
							UIComponent component = view.findComponent(clientId);

							if (component instanceof UICommand) {
								return (UICommand) component;
							}
						}
					}
				}
				if (componentAux != null) {
					String methodExpression = componentAux.getActionExpression().getExpressionString();
					// It'll contain #{bean.action}.
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
