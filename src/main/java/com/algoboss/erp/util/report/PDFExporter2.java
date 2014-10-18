/*
 * Copyright 2009-2012 Prime Teknoloji.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.algoboss.erp.util.report;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementHandler;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.Writable;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.WritableElement;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;


import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.column.Column;
import org.primefaces.component.columns.Columns;
import org.primefaces.component.datatable.DataTableRenderer;
import org.primefaces.util.Constants;

public class PDFExporter2 extends Exporter {

    private Font cellFont;
    private Font facetFont;
    private BaseColor facetBgColor;

    @Override
    public void export(FacesContext context, DataTable table, String filename, boolean pageOnly, boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor) throws IOException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            if (preProcessor != null) {
                preProcessor.invoke(context.getELContext(), new Object[]{document});
            }

            if (!document.isOpen()) {
                document.open();
            }

            document.add(exportPDFTable(context, table, pageOnly, selectionOnly, encodingType));

            if (postProcessor != null) {
                postProcessor.invoke(context.getELContext(), new Object[]{document});
            }

            document.close();

            writePDFToResponse(context.getExternalContext(), baos, filename);

        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void export(FacesContext context, DataTable table, String filename, String encodingType) throws IOException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            if (!document.isOpen()) {
                document.open();
            }
            document.setMargins(1f, 1f, 1f, 1f);
            document.setPageSize(PageSize.A4);
            document.add(exportPDFTable(context, table, false, false, true, encodingType));
            document.setMargins(1f, 1f, 1f, 1f);
            document.setPageSize(PageSize.A4);

            document.close();

            //writePDFToResponse(context.getExternalContext(), baos, filename);
            writePDFToResponseNew(context.getExternalContext(), baos, filename);

        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void export(FacesContext context, DataTable table, String filename) throws IOException {
        try {
            Document document = new Document(PageSize.A4);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream(1000000);
            FileOutputStream file = new FileOutputStream("D://Documents//testeLink.pdf");
            PdfWriter pdfWriter = PdfWriter.getInstance(document, file);
            document.open();
            document.addAuthor("Algo Boss");
            document.addCreator("Real's HowTo");
            document.addSubject("Thanks for your support");
            document.addCreationDate();
            document.addTitle("Please read this");
            //XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            DataTableRenderer render = new DataTableRenderer();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.reset();
            ExternalContext externalContext = context.getExternalContext();
            //BufferedWriter writer = new BufferedWriter(baos2,true);
            OutputStreamWriter writer = new OutputStreamWriter(baos2, "iso-8859-1");

            context.setResponseWriter(context.getRenderKit().createResponseWriter(writer, null, "iso-8859-1"));
            render.encodeEnd(context, table);
            //System.out.println(new String(baos2.toByteArray(), "utf-8"));
            /*writer.write(
             "<link href=\"/ERP/f/javax.faces.resource/theme.css?ln=primefaces-cupertino\" rel=\"stylesheet\" type=\"text/css\">\n"
             + "<link href=\"/ERP/f/javax.faces.resource/primefaces.css?ln=primefaces\" rel=\"stylesheet\" type=\"text/css\">\n"
             + "<link href=\"/ERP/f/javax.faces.resource/schedule/schedule.css?ln=primefaces\" rel=\"stylesheet\" type=\"text/css\">\n"
             + "<meta content=\"application/pdf; charset=UTF-8\" http-equiv=\"Content-Type\">\n"
             + "<link type=\"text/css\" rel=\"stylesheet\" href=\"/ERP/resources/css/default.css\">\n"
             + "<link type=\"text/css\" rel=\"stylesheet\" href=\"/ERP/resources/css/cssLayout.css\">\n"
             + "<title>Plataforma de Gerenciamento</title>");*/
            /*
             externalContext.setResponseHeader("Expires", "0");
             externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
             externalContext.setResponseHeader("Pragma", "public");
             externalContext.setResponseHeader("Content-disposition", "inline;filename=" + filename + ".pdf");
             externalContext.setResponseContentType("application/pdf");
             externalContext.setResponseContentLength(baos.size());
             externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", new HashMap<String, Object>());
             OutputStream out = externalContext.getResponseOutputStream();
             baos.writeTo(out);
             externalContext.responseFlushBuffer();     
             */

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=\"" + filename + "\";");

            //String str = "<html><head></head><body>" + new String(baos2.toByteArray()) + "</body></html>";
            String str = "<html><head></head><body>"
                    + "<link href=\"/ERP/f/javax.faces.resource/theme.css?ln=primefaces-cupertino\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                    + "<link href=\"/ERP/f/javax.faces.resource/primefaces.css?ln=primefaces\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                    + "<link href=\"/ERP/f/javax.faces.resource/schedule/schedule.css?ln=primefaces\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                    + "<meta content=\"application/pdf; charset=UTF-8\" http-equiv=\"Content-Type\"/>\n"
                    + "<link type=\"text/css\" rel=\"stylesheet\" href=\"/ERP/resources/css/default.css\"/>\n"
                    + "<link type=\"text/css\" rel=\"stylesheet\" href=\"/ERP/resources/css/cssLayout.css\"/>\n"
                    + "<title>Plataforma de Gerenciamento</title>"
                    + "<div id='tabView:ad_j_id77' class='ui-datatable ui-widget ui-algo-element ui-algo-element-container data-list'><div class='ui-datatable-header ui-widget-header ui-corner-top'><div id='tabView:ad_j_id77:ad_j_id41' class='ui-panel ui-widget ui-widget-content ui-corner-all ui-algo-element ui-algo-element-container'><div id='tabView:ad_j_id77:ad_j_id41_content' class='ui-panel-content ui-widget-content'><label id='tabView:ad_j_id77:ad_j_id39' class='ui-outputlabel ui-algo-element ui-algo-element-value'>Listagem: FUNCIONÁRIO</label><label id='tabView:ad_j_id77:ad_j_id40' class='ui-outputlabel ui-algo-element ui-algo-element-value' style='float:right;top:-5px;position: relative;'>Pesquisar: <input id='tabView:ad_j_id77:globalFilter' name='tabView:ad_j_id77:globalFilter' type='text' value='' onkeyup='funcionarioDataTable.filter()' class='ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all ui-algo-element  ui-algo-element-value' /><script id='tabView:ad_j_id77:globalFilter_s' type='text/javascript'>PrimeFaces.cw('InputText','widget_tabView_ad_j_id77_globalFilter',{id:'tabView:ad_j_id77:globalFilter'});</script></label></div></div><script id='tabView:ad_j_id77:ad_j_id41_s' type='text/javascript'>PrimeFaces.cw('Panel','widget_tabView_ad_j_id77_ad_j_id41',{id:'tabView:ad_j_id77:ad_j_id41'});</script></div><div id='tabView:ad_j_id77_paginator_top' class='ui-paginator ui-paginator-top ui-widget-header'><span class='ui-paginator-first ui-state-default ui-corner-all ui-state-disabled'><span class='ui-icon ui-icon-seek-first'>p</span></span><span class='ui-paginator-prev ui-state-default ui-corner-all ui-state-disabled'><span class='ui-icon ui-icon-seek-prev'>p</span></span><span class='ui-paginator-pages'><span class='ui-paginator-page ui-state-default ui-state-active ui-corner-all'>1</span><span class='ui-paginator-page ui-state-default ui-corner-all'>2</span></span><span class='ui-paginator-next ui-state-default ui-corner-all'><span class='ui-icon ui-icon-seek-next'>p</span></span><span class='ui-paginator-last ui-state-default ui-corner-all'><span class='ui-icon ui-icon-seek-end'>p</span></span></div><div class='ui-datatable-tablewrapper'><table role='grid'><thead id='tabView:ad_j_id77_head'><tr role='row'><th id='tabView:ad_j_id77:ad_j_id44' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader'><span>Nome</span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id44:filter' name='tabView:ad_j_id77:ad_j_id44:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id48' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader'><span><span id='tabView:ad_j_id77:ad_j_id45'>Usuário</span></span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id48:filter' name='tabView:ad_j_id77:ad_j_id48:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id52' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader'><span><span id='tabView:ad_j_id77:ad_j_id49'>Admissão</span></span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id52:filter' name='tabView:ad_j_id77:ad_j_id52:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id56' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader' style='text-align:center;'><span><span id='tabView:ad_j_id77:ad_j_id53'>Afastado</span></span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id56:filter' name='tabView:ad_j_id77:ad_j_id56:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id60' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader'><span><span id='tabView:ad_j_id77:ad_j_id57'>Veículo</span></span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id60:filter' name='tabView:ad_j_id77:ad_j_id60:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id64' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader'><span><span id='tabView:ad_j_id77:ad_j_id61'>MATRÍCULA</span></span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id64:filter' name='tabView:ad_j_id77:ad_j_id64:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id68' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader'><span><span id='tabView:ad_j_id77:ad_j_id65'>CPF</span></span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id68:filter' name='tabView:ad_j_id77:ad_j_id68:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id72' class='ui-state-default' role='columnheader'><span><span id='tabView:ad_j_id77:ad_j_id69'>SALÁRIO</span></span></th><th id='tabView:ad_j_id77:ad_j_id76' class='ui-state-default' role='columnheader' style='text-align:center;'><span>Ação</span></th></tr></thead><tfoot></tfoot><tbody id='tabView:ad_j_id77_data' class='ui-datatable-data ui-widget-content'><tr data-ri='0' data-rk='315' class='ui-widget-content ui-datatable-even' role='row' aria-selected='false'><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id42'>MARCUS2</span><a id='tabView:ad_j_id77:0:ad_j_id43' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id43',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id46'>SDASA2</span><a id='tabView:ad_j_id77:0:ad_j_id47' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id47',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id50'>2013/08/15</span><a id='tabView:ad_j_id77:0:ad_j_id51' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id51',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><span id='tabView:ad_j_id77:0:ad_j_id54' class=' ui-icon ui-icon-cancel ' style='display:inline-block;'>false</span><a id='tabView:ad_j_id77:0:ad_j_id55' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id55',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id58'>JQV-4404 - GM</span><a id='tabView:ad_j_id77:0:ad_j_id59' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id59',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id62'>123124</span><a id='tabView:ad_j_id77:0:ad_j_id63' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id63',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id66'></span><a id='tabView:ad_j_id77:0:ad_j_id67' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id67',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id70'></span><a id='tabView:ad_j_id77:0:ad_j_id71' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id71',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><a id='tabView:ad_j_id77:0:ad_j_id73' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id73',update:'tabView:j_idt233'});return false;'>Editar</a><span id='tabView:ad_j_id77:0:ad_j_id74'> | </span><a id='tabView:ad_j_id77:0:ad_j_id75' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id75',update:'tabView:j_idt233'});return false;'>Excluir</a></td></tr><tr data-ri='1' data-rk='318' class='ui-widget-content ui-datatable-odd' role='row' aria-selected='false'><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id42'>PAULO</span><a id='tabView:ad_j_id77:1:ad_j_id43' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id43',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id46'>PAULO</span><a id='tabView:ad_j_id77:1:ad_j_id47' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id47',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id50'>2013/08/07</span><a id='tabView:ad_j_id77:1:ad_j_id51' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id51',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><span id='tabView:ad_j_id77:1:ad_j_id54' class=' ui-icon ui-icon-cancel ' style='display:inline-block;'>false</span><a id='tabView:ad_j_id77:1:ad_j_id55' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id55',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id58'></span><a id='tabView:ad_j_id77:1:ad_j_id59' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id59',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id62'></span><a id='tabView:ad_j_id77:1:ad_j_id63' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id63',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id66'></span><a id='tabView:ad_j_id77:1:ad_j_id67' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id67',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id70'></span><a id='tabView:ad_j_id77:1:ad_j_id71' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id71',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><a id='tabView:ad_j_id77:1:ad_j_id73' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id73',update:'tabView:j_idt233'});return false;'>Editar</a><span id='tabView:ad_j_id77:1:ad_j_id74'> | </span><a id='tabView:ad_j_id77:1:ad_j_id75' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id75',update:'tabView:j_idt233'});return false;'>Excluir</a></td></tr><tr data-ri='2' data-rk='319' class='ui-widget-content ui-datatable-even' role='row' aria-selected='false'><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id42'>MAIZA</span><a id='tabView:ad_j_id77:2:ad_j_id43' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id43',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id46'>MAIZA</span><a id='tabView:ad_j_id77:2:ad_j_id47' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id47',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id50'></span><a id='tabView:ad_j_id77:2:ad_j_id51' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id51',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><span id='tabView:ad_j_id77:2:ad_j_id54' class=' ui-icon ui-icon-cancel ' style='display:inline-block;'>false</span><a id='tabView:ad_j_id77:2:ad_j_id55' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id55',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id58'>BMW - BMW-9999</span><a id='tabView:ad_j_id77:2:ad_j_id59' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id59',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id62'>TESTE123123</span><a id='tabView:ad_j_id77:2:ad_j_id63' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id63',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id66'>123.123.123-12</span><a id='tabView:ad_j_id77:2:ad_j_id67' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id67',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id70'>12.345,67</span><a id='tabView:ad_j_id77:2:ad_j_id71' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id71',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><a id='tabView:ad_j_id77:2:ad_j_id73' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id73',update:'tabView:j_idt233'});return false;'>Editar</a><span id='tabView:ad_j_id77:2:ad_j_id74'> | </span><a id='tabView:ad_j_id77:2:ad_j_id75' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id75',update:'tabView:j_idt233'});return false;'>Excluir</a></td></tr><tr data-ri='3' data-rk='320' class='ui-widget-content ui-datatable-odd' role='row' aria-selected='false'><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id42'>ELAINE</span><a id='tabView:ad_j_id77:3:ad_j_id43' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id43',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id46'>ELAINE</span><a id='tabView:ad_j_id77:3:ad_j_id47' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id47',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id50'>2013/08/22</span><a id='tabView:ad_j_id77:3:ad_j_id51' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id51',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><span id='tabView:ad_j_id77:3:ad_j_id54' class=' ui-icon ui-icon-cancel ' style='display:inline-block;'>false</span><a id='tabView:ad_j_id77:3:ad_j_id55' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id55',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id58'>JQV-4404 - GM</span><a id='tabView:ad_j_id77:3:ad_j_id59' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id59',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id62'></span><a id='tabView:ad_j_id77:3:ad_j_id63' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id63',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id66'></span><a id='tabView:ad_j_id77:3:ad_j_id67' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id67',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id70'></span><a id='tabView:ad_j_id77:3:ad_j_id71' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id71',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><a id='tabView:ad_j_id77:3:ad_j_id73' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id73',update:'tabView:j_idt233'});return false;'>Editar</a><span id='tabView:ad_j_id77:3:ad_j_id74'> | </span><a id='tabView:ad_j_id77:3:ad_j_id75' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id75',update:'tabView:j_idt233'});return false;'>Excluir</a></td></tr><tr data-ri='4' data-rk='321' class='ui-widget-content ui-datatable-even' role='row' aria-selected='false'><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id42'>CLEIDE2</span><a id='tabView:ad_j_id77:4:ad_j_id43' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id43',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id46'>CLEIDE DA SILVA</span><a id='tabView:ad_j_id77:4:ad_j_id47' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id47',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id50'>2013/08/19</span><a id='tabView:ad_j_id77:4:ad_j_id51' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id51',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><span id='tabView:ad_j_id77:4:ad_j_id54' class=' ui-icon ui-icon-check ' style='display:inline-block;'>true</span><a id='tabView:ad_j_id77:4:ad_j_id55' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id55',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id58'>EEQEWEQE - QWEQ</span><a id='tabView:ad_j_id77:4:ad_j_id59' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id59',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id62'></span><a id='tabView:ad_j_id77:4:ad_j_id63' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id63',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id66'>123.123.123-12</span><a id='tabView:ad_j_id77:4:ad_j_id67' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id67',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id70'>456,70</span><a id='tabView:ad_j_id77:4:ad_j_id71' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id71',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><a id='tabView:ad_j_id77:4:ad_j_id73' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id73',update:'tabView:j_idt233'});return false;'>Editar</a><span id='tabView:ad_j_id77:4:ad_j_id74'> | </span><a id='tabView:ad_j_id77:4:ad_j_id75' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id75',update:'tabView:j_idt233'});return false;'>Excluir</a></td></tr><tr data-ri='5' data-rk='322' class='ui-widget-content ui-datatable-odd' role='row' aria-selected='false'><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id42'>GABRIELA</span><a id='tabView:ad_j_id77:5:ad_j_id43' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id43',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id46'>GABRIELA</span><a id='tabView:ad_j_id77:5:ad_j_id47' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id47',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id50'>2013/08/20</span><a id='tabView:ad_j_id77:5:ad_j_id51' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id51',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><span id='tabView:ad_j_id77:5:ad_j_id54' class=' ui-icon ui-icon-cancel ' style='display:inline-block;'>false</span><a id='tabView:ad_j_id77:5:ad_j_id55' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id55',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id58'></span><a id='tabView:ad_j_id77:5:ad_j_id59' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id59',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id62'></span><a id='tabView:ad_j_id77:5:ad_j_id63' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id63',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id66'></span><a id='tabView:ad_j_id77:5:ad_j_id67' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id67',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id70'></span><a id='tabView:ad_j_id77:5:ad_j_id71' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id71',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><a id='tabView:ad_j_id77:5:ad_j_id73' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id73',update:'tabView:j_idt233'});return false;'>Editar</a><span id='tabView:ad_j_id77:5:ad_j_id74'> | </span><a id='tabView:ad_j_id77:5:ad_j_id75' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id75',update:'tabView:j_idt233'});return false;'>Excluir</a></td></tr>"
                    + "</tbody></table></div></div>"
                    + "</body></html>";

            //worker.parseXHtml(pdfWriter, document, new StringReader(str));

            response.setContentLength(baos.size());
            response.getOutputStream().write(baos.toByteArray(), 0, baos.size());
            //System.out.println(str);
            document.close();
            pdfWriter.close();
            baos.close();
            baos2.close();
            file.close();
            FacesContext.getCurrentInstance().responseComplete();
            //writePDFToResponse(context.getExternalContext(), baos, filename);
            //writePDFToResponseNew(context.getExternalContext(), baos, filename);

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(PDFExporter2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void export2(FacesContext context, String htmltable, String filename) throws IOException {
        try {
            Document document = new Document(PageSize.A4);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream(1000000);
            FileOutputStream file = new FileOutputStream("D://Documents//testeLink.pdf");
            PdfWriter pdfWriter = PdfWriter.getInstance(document, file);
            document.open();
            document.addAuthor("Algo Boss");
            document.addCreator("Real's HowTo");
            document.addSubject("Thanks for your support");
            document.addCreationDate();
            document.addTitle("Please read this");
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            DataTableRenderer render = new DataTableRenderer();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.reset();
            ExternalContext externalContext = context.getExternalContext();
            //BufferedWriter writer = new BufferedWriter(baos2,true);
            OutputStreamWriter writer = new OutputStreamWriter(baos2, "iso-8859-1");

            context.setResponseWriter(context.getRenderKit().createResponseWriter(writer, null, "iso-8859-1"));
            //render.encodeEnd(context, table);
            //System.out.println(new String(baos2.toByteArray(), "utf-8"));
            /*writer.write(
             "<link href=\"/ERP/f/javax.faces.resource/theme.css?ln=primefaces-cupertino\" rel=\"stylesheet\" type=\"text/css\">\n"
             + "<link href=\"/ERP/f/javax.faces.resource/primefaces.css?ln=primefaces\" rel=\"stylesheet\" type=\"text/css\">\n"
             + "<link href=\"/ERP/f/javax.faces.resource/schedule/schedule.css?ln=primefaces\" rel=\"stylesheet\" type=\"text/css\">\n"
             + "<meta content=\"application/pdf; charset=UTF-8\" http-equiv=\"Content-Type\">\n"
             + "<link type=\"text/css\" rel=\"stylesheet\" href=\"/ERP/resources/css/default.css\">\n"
             + "<link type=\"text/css\" rel=\"stylesheet\" href=\"/ERP/resources/css/cssLayout.css\">\n"
             + "<title>Plataforma de Gerenciamento</title>");*/
            /*
             externalContext.setResponseHeader("Expires", "0");
             externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
             externalContext.setResponseHeader("Pragma", "public");
             externalContext.setResponseHeader("Content-disposition", "inline;filename=" + filename + ".pdf");
             externalContext.setResponseContentType("application/pdf");
             externalContext.setResponseContentLength(baos.size());
             externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", new HashMap<String, Object>());
             OutputStream out = externalContext.getResponseOutputStream();
             baos.writeTo(out);
             externalContext.responseFlushBuffer();     
             */

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=\"" + filename + "\";");

            //String str = "<html><head></head><body>" + new String(baos2.toByteArray()) + "</body></html>";
            String str = "<html><head></head><body>"
                    + "<link href=\"/ERP/f/javax.faces.resource/theme.css?ln=primefaces-cupertino\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                    + "<link href=\"/ERP/f/javax.faces.resource/primefaces.css?ln=primefaces\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                    + "<link href=\"/ERP/f/javax.faces.resource/schedule/schedule.css?ln=primefaces\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                    + "<meta content=\"application/pdf; charset=UTF-8\" http-equiv=\"Content-Type\"/>\n"
                    + "<link type=\"text/css\" rel=\"stylesheet\" href=\"/ERP/resources/css/default.css\"/>\n"
                    + "<link type=\"text/css\" rel=\"stylesheet\" href=\"/ERP/resources/css/cssLayout.css\"/>\n"
                    + "<title>Plataforma de Gerenciamento</title>"
                    + "<div id='tabView:ad_j_id77' class='ui-datatable ui-widget ui-algo-element ui-algo-element-container data-list'><div class='ui-datatable-header ui-widget-header ui-corner-top'><div id='tabView:ad_j_id77:ad_j_id41' class='ui-panel ui-widget ui-widget-content ui-corner-all ui-algo-element ui-algo-element-container'><div id='tabView:ad_j_id77:ad_j_id41_content' class='ui-panel-content ui-widget-content'><label id='tabView:ad_j_id77:ad_j_id39' class='ui-outputlabel ui-algo-element ui-algo-element-value'>Listagem: FUNCIONÁRIO</label><label id='tabView:ad_j_id77:ad_j_id40' class='ui-outputlabel ui-algo-element ui-algo-element-value' style='float:right;top:-5px;position: relative;'>Pesquisar: <input id='tabView:ad_j_id77:globalFilter' name='tabView:ad_j_id77:globalFilter' type='text' value='' onkeyup='funcionarioDataTable.filter()' class='ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all ui-algo-element  ui-algo-element-value' /><script id='tabView:ad_j_id77:globalFilter_s' type='text/javascript'>PrimeFaces.cw('InputText','widget_tabView_ad_j_id77_globalFilter',{id:'tabView:ad_j_id77:globalFilter'});</script></label></div></div><script id='tabView:ad_j_id77:ad_j_id41_s' type='text/javascript'>PrimeFaces.cw('Panel','widget_tabView_ad_j_id77_ad_j_id41',{id:'tabView:ad_j_id77:ad_j_id41'});</script></div><div id='tabView:ad_j_id77_paginator_top' class='ui-paginator ui-paginator-top ui-widget-header'><span class='ui-paginator-first ui-state-default ui-corner-all ui-state-disabled'><span class='ui-icon ui-icon-seek-first'>p</span></span><span class='ui-paginator-prev ui-state-default ui-corner-all ui-state-disabled'><span class='ui-icon ui-icon-seek-prev'>p</span></span><span class='ui-paginator-pages'><span class='ui-paginator-page ui-state-default ui-state-active ui-corner-all'>1</span><span class='ui-paginator-page ui-state-default ui-corner-all'>2</span></span><span class='ui-paginator-next ui-state-default ui-corner-all'><span class='ui-icon ui-icon-seek-next'>p</span></span><span class='ui-paginator-last ui-state-default ui-corner-all'><span class='ui-icon ui-icon-seek-end'>p</span></span></div><div class='ui-datatable-tablewrapper'><table role='grid'><thead id='tabView:ad_j_id77_head'><tr role='row'><th id='tabView:ad_j_id77:ad_j_id44' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader'><span>Nome</span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id44:filter' name='tabView:ad_j_id77:ad_j_id44:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id48' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader'><span><span id='tabView:ad_j_id77:ad_j_id45'>Usuário</span></span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id48:filter' name='tabView:ad_j_id77:ad_j_id48:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id52' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader'><span><span id='tabView:ad_j_id77:ad_j_id49'>Admissão</span></span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id52:filter' name='tabView:ad_j_id77:ad_j_id52:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id56' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader' style='text-align:center;'><span><span id='tabView:ad_j_id77:ad_j_id53'>Afastado</span></span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id56:filter' name='tabView:ad_j_id77:ad_j_id56:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id60' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader'><span><span id='tabView:ad_j_id77:ad_j_id57'>Veículo</span></span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id60:filter' name='tabView:ad_j_id77:ad_j_id60:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id64' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader'><span><span id='tabView:ad_j_id77:ad_j_id61'>MATRÍCULA</span></span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id64:filter' name='tabView:ad_j_id77:ad_j_id64:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id68' class='ui-state-default ui-sortable-column ui-filter-column' role='columnheader'><span><span id='tabView:ad_j_id77:ad_j_id65'>CPF</span></span><span class='ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s'></span><input id='tabView:ad_j_id77:ad_j_id68:filter' name='tabView:ad_j_id77:ad_j_id68:filter' class='ui-column-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all' value='' autocomplete='off' style='display:none;' /></th><th id='tabView:ad_j_id77:ad_j_id72' class='ui-state-default' role='columnheader'><span><span id='tabView:ad_j_id77:ad_j_id69'>SALÁRIO</span></span></th><th id='tabView:ad_j_id77:ad_j_id76' class='ui-state-default' role='columnheader' style='text-align:center;'><span>Ação</span></th></tr></thead><tfoot></tfoot><tbody id='tabView:ad_j_id77_data' class='ui-datatable-data ui-widget-content'><tr data-ri='0' data-rk='315' class='ui-widget-content ui-datatable-even' role='row' aria-selected='false'><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id42'>MARCUS2</span><a id='tabView:ad_j_id77:0:ad_j_id43' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id43',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id46'>SDASA2</span><a id='tabView:ad_j_id77:0:ad_j_id47' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id47',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id50'>2013/08/15</span><a id='tabView:ad_j_id77:0:ad_j_id51' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id51',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><span id='tabView:ad_j_id77:0:ad_j_id54' class=' ui-icon ui-icon-cancel ' style='display:inline-block;'>false</span><a id='tabView:ad_j_id77:0:ad_j_id55' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id55',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id58'>JQV-4404 - GM</span><a id='tabView:ad_j_id77:0:ad_j_id59' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id59',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id62'>123124</span><a id='tabView:ad_j_id77:0:ad_j_id63' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id63',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id66'></span><a id='tabView:ad_j_id77:0:ad_j_id67' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id67',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:0:ad_j_id70'></span><a id='tabView:ad_j_id77:0:ad_j_id71' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id71',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><a id='tabView:ad_j_id77:0:ad_j_id73' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id73',update:'tabView:j_idt233'});return false;'>Editar</a><span id='tabView:ad_j_id77:0:ad_j_id74'> | </span><a id='tabView:ad_j_id77:0:ad_j_id75' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:0:ad_j_id75',update:'tabView:j_idt233'});return false;'>Excluir</a></td></tr><tr data-ri='1' data-rk='318' class='ui-widget-content ui-datatable-odd' role='row' aria-selected='false'><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id42'>PAULO</span><a id='tabView:ad_j_id77:1:ad_j_id43' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id43',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id46'>PAULO</span><a id='tabView:ad_j_id77:1:ad_j_id47' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id47',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id50'>2013/08/07</span><a id='tabView:ad_j_id77:1:ad_j_id51' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id51',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><span id='tabView:ad_j_id77:1:ad_j_id54' class=' ui-icon ui-icon-cancel ' style='display:inline-block;'>false</span><a id='tabView:ad_j_id77:1:ad_j_id55' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id55',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id58'></span><a id='tabView:ad_j_id77:1:ad_j_id59' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id59',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id62'></span><a id='tabView:ad_j_id77:1:ad_j_id63' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id63',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id66'></span><a id='tabView:ad_j_id77:1:ad_j_id67' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id67',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:1:ad_j_id70'></span><a id='tabView:ad_j_id77:1:ad_j_id71' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id71',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><a id='tabView:ad_j_id77:1:ad_j_id73' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id73',update:'tabView:j_idt233'});return false;'>Editar</a><span id='tabView:ad_j_id77:1:ad_j_id74'> | </span><a id='tabView:ad_j_id77:1:ad_j_id75' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:1:ad_j_id75',update:'tabView:j_idt233'});return false;'>Excluir</a></td></tr><tr data-ri='2' data-rk='319' class='ui-widget-content ui-datatable-even' role='row' aria-selected='false'><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id42'>MAIZA</span><a id='tabView:ad_j_id77:2:ad_j_id43' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id43',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id46'>MAIZA</span><a id='tabView:ad_j_id77:2:ad_j_id47' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id47',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id50'></span><a id='tabView:ad_j_id77:2:ad_j_id51' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id51',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><span id='tabView:ad_j_id77:2:ad_j_id54' class=' ui-icon ui-icon-cancel ' style='display:inline-block;'>false</span><a id='tabView:ad_j_id77:2:ad_j_id55' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id55',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id58'>BMW - BMW-9999</span><a id='tabView:ad_j_id77:2:ad_j_id59' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id59',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id62'>TESTE123123</span><a id='tabView:ad_j_id77:2:ad_j_id63' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id63',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id66'>123.123.123-12</span><a id='tabView:ad_j_id77:2:ad_j_id67' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id67',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:2:ad_j_id70'>12.345,67</span><a id='tabView:ad_j_id77:2:ad_j_id71' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id71',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><a id='tabView:ad_j_id77:2:ad_j_id73' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id73',update:'tabView:j_idt233'});return false;'>Editar</a><span id='tabView:ad_j_id77:2:ad_j_id74'> | </span><a id='tabView:ad_j_id77:2:ad_j_id75' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:2:ad_j_id75',update:'tabView:j_idt233'});return false;'>Excluir</a></td></tr><tr data-ri='3' data-rk='320' class='ui-widget-content ui-datatable-odd' role='row' aria-selected='false'><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id42'>ELAINE</span><a id='tabView:ad_j_id77:3:ad_j_id43' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id43',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id46'>ELAINE</span><a id='tabView:ad_j_id77:3:ad_j_id47' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id47',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id50'>2013/08/22</span><a id='tabView:ad_j_id77:3:ad_j_id51' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id51',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><span id='tabView:ad_j_id77:3:ad_j_id54' class=' ui-icon ui-icon-cancel ' style='display:inline-block;'>false</span><a id='tabView:ad_j_id77:3:ad_j_id55' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id55',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id58'>JQV-4404 - GM</span><a id='tabView:ad_j_id77:3:ad_j_id59' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id59',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id62'></span><a id='tabView:ad_j_id77:3:ad_j_id63' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id63',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id66'></span><a id='tabView:ad_j_id77:3:ad_j_id67' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id67',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:3:ad_j_id70'></span><a id='tabView:ad_j_id77:3:ad_j_id71' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id71',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><a id='tabView:ad_j_id77:3:ad_j_id73' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id73',update:'tabView:j_idt233'});return false;'>Editar</a><span id='tabView:ad_j_id77:3:ad_j_id74'> | </span><a id='tabView:ad_j_id77:3:ad_j_id75' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:3:ad_j_id75',update:'tabView:j_idt233'});return false;'>Excluir</a></td></tr><tr data-ri='4' data-rk='321' class='ui-widget-content ui-datatable-even' role='row' aria-selected='false'><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id42'>CLEIDE2</span><a id='tabView:ad_j_id77:4:ad_j_id43' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id43',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id46'>CLEIDE DA SILVA</span><a id='tabView:ad_j_id77:4:ad_j_id47' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id47',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id50'>2013/08/19</span><a id='tabView:ad_j_id77:4:ad_j_id51' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id51',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><span id='tabView:ad_j_id77:4:ad_j_id54' class=' ui-icon ui-icon-check ' style='display:inline-block;'>true</span><a id='tabView:ad_j_id77:4:ad_j_id55' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id55',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id58'>EEQEWEQE - QWEQ</span><a id='tabView:ad_j_id77:4:ad_j_id59' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id59',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id62'></span><a id='tabView:ad_j_id77:4:ad_j_id63' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id63',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id66'>123.123.123-12</span><a id='tabView:ad_j_id77:4:ad_j_id67' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id67',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:4:ad_j_id70'>456,70</span><a id='tabView:ad_j_id77:4:ad_j_id71' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id71',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><a id='tabView:ad_j_id77:4:ad_j_id73' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id73',update:'tabView:j_idt233'});return false;'>Editar</a><span id='tabView:ad_j_id77:4:ad_j_id74'> | </span><a id='tabView:ad_j_id77:4:ad_j_id75' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:4:ad_j_id75',update:'tabView:j_idt233'});return false;'>Excluir</a></td></tr><tr data-ri='5' data-rk='322' class='ui-widget-content ui-datatable-odd' role='row' aria-selected='false'><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id42'>GABRIELA</span><a id='tabView:ad_j_id77:5:ad_j_id43' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id43',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id46'>GABRIELA</span><a id='tabView:ad_j_id77:5:ad_j_id47' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id47',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id50'>2013/08/20</span><a id='tabView:ad_j_id77:5:ad_j_id51' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id51',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><span id='tabView:ad_j_id77:5:ad_j_id54' class=' ui-icon ui-icon-cancel ' style='display:inline-block;'>false</span><a id='tabView:ad_j_id77:5:ad_j_id55' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id55',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id58'></span><a id='tabView:ad_j_id77:5:ad_j_id59' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id59',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id62'></span><a id='tabView:ad_j_id77:5:ad_j_id63' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id63',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id66'></span><a id='tabView:ad_j_id77:5:ad_j_id67' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id67',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell'><span id='tabView:ad_j_id77:5:ad_j_id70'></span><a id='tabView:ad_j_id77:5:ad_j_id71' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id71',update:'tabView:j_idt233'});return false;'></a></td><td role='gridcell' style='text-align:center;'><a id='tabView:ad_j_id77:5:ad_j_id73' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id73',update:'tabView:j_idt233'});return false;'>Editar</a><span id='tabView:ad_j_id77:5:ad_j_id74'> | </span><a id='tabView:ad_j_id77:5:ad_j_id75' href='#' class='ui-commandlink ui-widget' onclick='PrimeFaces.ab({source:'tabView:ad_j_id77:5:ad_j_id75',update:'tabView:j_idt233'});return false;'>Excluir</a></td></tr>"
                    + "</tbody></table></div></div>"
                    + "</body></html>";
            ElementHandler eh = new ElementHandler() {
                public List<Writable> elements = new ArrayList<Writable>();

                @Override
                public void add(Writable wrtbl) {
                    if (wrtbl instanceof WritableElement) {
                        elements.add(wrtbl);
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            //worker.parseXHtml(eh, new StringReader(htmltable));
            worker.parseXHtml(pdfWriter, document, new StringReader(htmltable));

            response.setContentLength(baos.size());
            response.getOutputStream().write(baos.toByteArray(), 0, baos.size());
            //System.out.println(str);
            document.close();
            pdfWriter.close();
            baos.close();
            baos2.close();
            file.close();
            FacesContext.getCurrentInstance().responseComplete();
            //writePDFToResponse(context.getExternalContext(), baos, filename);
            //writePDFToResponseNew(context.getExternalContext(), baos, filename);

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(PDFExporter2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void export(FacesContext context, String cssstring, String htmlstring, String filename) throws IOException {
        try {
            InputStream is = new ByteArrayInputStream(htmlstring.getBytes());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // step 1
            Document document = new Document();

            // step 2
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            writer.setInitialLeading(12.5f);

            // step 3
            document.open();

            HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);

            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

            // CSS
            CSSResolver cssResolver = new StyleAttrCSSResolver();
            String[] cssstringArray = cssstring.split(";");
FacesContext aFacesContext = FacesContext.getCurrentInstance();  
            ServletContext context2 = (ServletContext)aFacesContext.getExternalContext().getContext();  
            String realPath = context2.getRealPath("/");  
            System.out.println(realPath);              
            System.out.println(context2.getContextPath());  
            //System.out.println(context2.getResource(""));  
            System.out.println(aFacesContext.getExternalContext().getContextName());
            System.out.println(aFacesContext.getExternalContext().getRequestContextPath()+aFacesContext.getExternalContext().getRequestServletPath());
            //System.out.println(aFacesContext.getExternalContext().getRequestServletPath());
            //System.out.println(aFacesContext.getExternalContext().getRequestPathInfo());
            //System.out.println(aFacesContext.getExternalContext().getRequestScheme());
            //System.out.println(aFacesContext.getExternalContext().getRequestScheme());
            //System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
            for (int i = 0; i < cssstringArray.length; i++) {
                String cssHref = cssstringArray[i];
                try {
                    URL url = new URL(aFacesContext.getExternalContext().getRequestScheme(),aFacesContext.getExternalContext().getRequestServerName(),aFacesContext.getExternalContext().getRequestServerPort(), cssHref);
                    InputStream csspathtest = url.openStream();
                    CssFile cssfiletest = XMLWorkerHelper.getCSS(csspathtest);
                    cssResolver.addCss(cssfiletest);                    
                } catch (IOException ex) {
                    Logger.getLogger(PDFExporter2.class.getName()).log(Level.SEVERE, null, ex);
                }
                //String realPath2 = context2.getRealPath(cssHref);

                //InputStream csspathtest2 = aFacesContext.getExternalContext().getResourceAsStream(realPath2);
                //System.out.println(cssfiletest);
            }

            Pipeline<?> pipeline = new CssResolverPipeline(cssResolver,
                    new HtmlPipeline(htmlContext, new PdfWriterPipeline(
                    document, writer)));

            XMLWorker worker = new XMLWorker(pipeline, true);
            XMLParser p = new XMLParser(worker);
            p.parse(is,Charset.forName("iso-8859-1"));//new FileInputStream("results/demo2/walden.html"));

            // step
            document.close();

            // post back...
            HttpServletResponse response = (HttpServletResponse) context
                    .getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-disposition",
                    "inline;filename=\"" + filename + "\";");
            response.setContentLength(baos.size());
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
            context.responseComplete();

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(PDFExporter2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected PdfPTable exportPDFTable(FacesContext context, DataTable table, boolean pageOnly, boolean selectionOnly, String encoding) {
        return exportPDFTable(context, table, pageOnly, selectionOnly, false, encoding);
    }

    protected PdfPTable exportPDFTable(FacesContext context, DataTable table, boolean pageOnly, boolean selectionOnly, boolean filteredOnly, String encoding) {
        int columnsCount = getColumnsCount(table);
        PdfPTable pdfTable = new PdfPTable(columnsCount);
        this.cellFont = FontFactory.getFont(FontFactory.TIMES, encoding, 7, Font.NORMAL);
        this.facetFont = FontFactory.getFont(FontFactory.TIMES, encoding, 7, Font.BOLD, BaseColor.BLUE);
        this.facetBgColor = BaseColor.LIGHT_GRAY;

        addColumnFacets(table, pdfTable, ColumnType.HEADER);

        if (pageOnly) {
            exportPageOnly(context, table, pdfTable);
        } else if (selectionOnly) {
            exportSelectionOnly(context, table, pdfTable);
        } else if (filteredOnly) {
            exportFilteredOnly(context, table, pdfTable);
        } else {
            exportAll(context, table, pdfTable);
        }

        if (table.hasFooterColumn()) {
            addColumnFacets(table, pdfTable, ColumnType.FOOTER);
        }

        table.setRowIndex(-1);

        return pdfTable;
    }

    protected void exportPageOnly(FacesContext context, DataTable table, PdfPTable pdfTable) {
        int first = table.getFirst();
        int rowsToExport = first + table.getRows();

        for (int rowIndex = first; rowIndex < rowsToExport; rowIndex++) {
            exportRow(table, pdfTable, rowIndex);
        }
    }

    protected void exportFilteredOnly(FacesContext context, DataTable table, PdfPTable pdfTable) {
        Object selection = table.getFilteredValue();
        String var = table.getVar();

        if (selection != null) {
            Map<String, Object> requestMap = context.getExternalContext().getRequestMap();

            if (selection.getClass().isArray()) {
                int size = Array.getLength(selection);

                for (int i = 0; i < size; i++) {
                    requestMap.put(var, Array.get(selection, i));

                    exportCells(table, pdfTable);
                }
            } else {
                requestMap.put(var, selection);

                exportCells(table, pdfTable);
            }
        }
    }

    protected void exportSelectionOnly(FacesContext context, DataTable table, PdfPTable pdfTable) {
        Object selection = table.getSelection();
        String var = table.getVar();

        if (selection != null) {
            Map<String, Object> requestMap = context.getExternalContext().getRequestMap();

            if (selection.getClass().isArray()) {
                int size = Array.getLength(selection);

                for (int i = 0; i < size; i++) {
                    requestMap.put(var, Array.get(selection, i));

                    exportCells(table, pdfTable);
                }
            } else {
                requestMap.put(var, selection);

                exportCells(table, pdfTable);
            }
        }
    }

    protected void exportAll(FacesContext context, DataTable table, PdfPTable pdfTable) {
        int first = table.getFirst();
        int rowCount = table.getRowCount();
        int rows = table.getRows();
        boolean lazy = table.isLazy();

        if (lazy) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                if (rowIndex % rows == 0) {
                    table.setFirst(rowIndex);
                    table.loadLazyData();
                }

                exportRow(table, pdfTable, rowIndex);
            }

            //restore
            table.setFirst(first);
            table.loadLazyData();
        } else {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                exportRow(table, pdfTable, rowIndex);
            }

            //restore
            table.setFirst(first);
        }
    }

    protected void exportRow(DataTable table, PdfPTable pdfTable, int rowIndex) {
        table.setRowIndex(rowIndex);

        if (!table.isRowAvailable()) {
            return;
        }

        exportCells(table, pdfTable);
    }

    protected void exportCells(DataTable table, PdfPTable pdfTable) {
        for (UIColumn col : table.getColumns()) {
            if (!col.isRendered()) {
                continue;
            }

            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyModel();
            }

            if (col.isExportable()) {
                addColumnValue(pdfTable, col.getChildren(), this.cellFont);
            }
        }
    }

    protected void addColumnFacets(DataTable table, PdfPTable pdfTable, ColumnType columnType) {
        for (UIColumn col : table.getColumns()) {
            if (!col.isRendered()) {
                continue;
            }

            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyModel();
            }

            if (col.isExportable()) {
                addColumnValue(pdfTable, col.getFacet(columnType.facet()), this.facetFont, this.facetBgColor);
            }
        }
    }

    protected void addColumnValue(PdfPTable pdfTable, UIComponent component, Font font) {
        String value = component == null ? "" : exportValue(FacesContext.getCurrentInstance(), component);

        pdfTable.addCell(new Paragraph(value, font));
    }

    protected void addColumnValue(PdfPTable pdfTable, UIComponent component, Font font, BaseColor bgColor) {
        String value = component == null ? "" : exportValue(FacesContext.getCurrentInstance(), component);
        PdfPCell cell = new PdfPCell(new Paragraph(value, font));
        cell.setBackgroundColor(bgColor);
        pdfTable.addCell(cell);

    }

    protected void addColumnValue(PdfPTable pdfTable, List<UIComponent> components, Font font) {
        StringBuilder builder = new StringBuilder();
        FacesContext context = FacesContext.getCurrentInstance();

        for (UIComponent component : components) {
            if (component.isRendered()) {
                String value = exportValue(context, component);

                if (value != null) {
                    builder.append(value);
                }
            }
        }

        pdfTable.addCell(new Paragraph(builder.toString(), font));
    }

    protected void writePDFToResponse(ExternalContext externalContext, ByteArrayOutputStream baos, String fileName) throws IOException, DocumentException {
        externalContext.setResponseContentType("application/pdf");
        externalContext.setResponseHeader("Expires", "0");
        externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        externalContext.setResponseHeader("Pragma", "public");
        externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
        externalContext.setResponseContentLength(baos.size());
        externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", new HashMap<String, Object>());
        OutputStream out = externalContext.getResponseOutputStream();
        baos.writeTo(out);
        externalContext.responseFlushBuffer();
    }

    protected void writePDFToResponseNew(ExternalContext externalContext, ByteArrayOutputStream baos, String fileName) throws IOException, DocumentException {
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\";");
        //response.setContentLength(baos.size());
        //response.getOutputStream().write(baos.toByteArray(), 0, baos.size());
        FacesContext.getCurrentInstance().responseComplete();

        //externalContext.responseReset();
        //externalContext.setResponseContentType("application/pdf");

        //externalContext.setResponseHeader("Expires", "0");
        //externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        //externalContext.setResponseHeader("Pragma", "public");

        //externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
        //externalContext.setResponseContentLength(baos.size());
        //externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", new HashMap<String, Object>());
        //OutputStream out = externalContext.getResponseOutputStream();
        //baos.writeTo(out);
        //externalContext.responseFlushBuffer();
    }

    protected int getColumnsCount(DataTable table) {
        int count = 0;

        for (UIComponent child : table.getChildren()) {
            if (!child.isRendered()) {
                continue;
            }

            if (child instanceof Column) {
                Column column = (Column) child;

                if (column.isExportable()) {
                    count++;
                }
            } else if (child instanceof Columns) {
                Columns columns = (Columns) child;

                if (columns.isExportable()) {
                    count += columns.getRowCount();
                }
            }
        }

        return count;
    }
}