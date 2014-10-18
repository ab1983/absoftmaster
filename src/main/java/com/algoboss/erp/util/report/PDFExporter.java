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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;


import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.column.Column;
import org.primefaces.component.columns.Columns;
import org.primefaces.component.datatable.DataTableRenderer;
import org.primefaces.util.Constants;

public class PDFExporter extends Exporter {

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
            Document document = new Document(PageSize.A4, 25f, 25f, 25f, 25f);  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            if (!document.isOpen()) {
                document.open();
            }
            document.addAuthor("Algo Boss");
            document.addCreator("Real's HowTo");
            document.addSubject("Thanks for your support");
            document.addCreationDate();
            document.addTitle(filename);            
            //document.setMargins(1f, 1f, 1f, 1f);
            //document.setPageSize(PageSize.A4);
            document.add(exportPDFTable(context, table, false, false, false, encodingType));
            //document.setMargins(1f, 1f, 1f, 1f);
            //document.setPageSize(PageSize.A4);

            document.close();

            //writePDFToResponse(context.getExternalContext(), baos, filename);
            writePDFToResponseNew(context.getExternalContext(), baos, filename);

        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void export(FacesContext context, DataTable table, String filename) throws IOException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
            //XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            DataTableRenderer render = new DataTableRenderer();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.reset();
            ExternalContext externalContext = context.getExternalContext();
            PrintWriter writer = new PrintWriter(baos);
             
            context.setResponseWriter(context.getRenderKit().createResponseWriter(writer, "text/html", "utf-8"));
            render.encodeEnd(context, table);
            writer.write(
"<link href=\"/ERP/f/javax.faces.resource/theme.css?ln=primefaces-cupertino\" rel=\"stylesheet\" type=\"text/css\">\n" +
"<link href=\"/ERP/f/javax.faces.resource/primefaces.css?ln=primefaces\" rel=\"stylesheet\" type=\"text/css\">\n" +
"<link href=\"/ERP/f/javax.faces.resource/schedule/schedule.css?ln=primefaces\" rel=\"stylesheet\" type=\"text/css\">\n" +
"<meta content=\"application/pdf; charset=UTF-8\" http-equiv=\"Content-Type\">\n" +
"<link type=\"text/css\" rel=\"stylesheet\" href=\"/ERP/resources/css/default.css\">\n" +
"<link type=\"text/css\" rel=\"stylesheet\" href=\"/ERP/resources/css/cssLayout.css\">\n" +
"<title>Plataforma de Gerenciamento</title>");
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

            response.setContentLength(baos.size());
            response.getOutputStream().write(baos.toByteArray(), 0, baos.size());
            FacesContext.getCurrentInstance().responseComplete();
            //worker.parseXHtml(pdfWriter, document, new StringReader(new String(baos.toByteArray())));
            document.close();

            //writePDFToResponse(context.getExternalContext(), baos, filename);
            //writePDFToResponseNew(context.getExternalContext(), baos, filename);

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (DocumentException ex) {
            Logger.getLogger(PDFExporter.class.getName()).log(Level.SEVERE, null, ex);
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
        response.setContentLength(baos.size());
        response.getOutputStream().write(baos.toByteArray(), 0, baos.size());
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