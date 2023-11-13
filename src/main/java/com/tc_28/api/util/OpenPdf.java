package com.tc_28.api.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tc_28.api.dto.LiquidationDto;

public class OpenPdf {

	public static ByteArrayInputStream liquidationsReport(List<LiquidationDto> liquidations) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 1, 1, 1, 1, 1 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Empresa", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(CMYKColor.GRAY);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("No. Trayectos", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(CMYKColor.GRAY);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("No. Entregas", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(CMYKColor.GRAY);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Fecha Liquidacion", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(CMYKColor.GRAY);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Valor Total", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(CMYKColor.GRAY);
			table.addCell(hcell);
			for (LiquidationDto liquidation : liquidations) {

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(liquidation.getName_company()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(liquidation.getNumber_trips())));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(liquidation.getNumber_deliveries())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(String.valueOf(liquidation.getDate())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("COP" + liquidation.getTotal_value()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}

			PdfWriter.getInstance(document, out);
			document.open();
			document.add(table);

			document.close();

		} catch (DocumentException ex) {

		}

		return new ByteArrayInputStream(out.toByteArray());
	}

}