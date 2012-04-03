package abel.wallpaper.global;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WallpaperHandler extends DefaultHandler {
	private WallpaperDataConfig dataConfing;
	private StringBuilder sbTexto;

	public WallpaperDataConfig getDataConfig() {
		return dataConfing;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		super.characters(ch, start, length);

		if (this.dataConfing != null)
			sbTexto.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {

		super.endElement(uri, localName, name);

		if (this.dataConfing != null) {

			if (localName.equals("activado")) {
				dataConfing.setActivado(sbTexto.toString());
			} else if (localName.equals("paquete")) {
				dataConfing.setPaquete(sbTexto.toString());
			} else if (localName.equals("firsTime")) {
				dataConfing.setFirsTime(sbTexto.toString());
			}

			sbTexto.setLength(0);
		}
	}

	@Override
	public void startDocument() throws SAXException {

		super.startDocument();

		dataConfing = new WallpaperDataConfig();
		sbTexto = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

		super.startElement(uri, localName, name, attributes);

		dataConfing = new WallpaperDataConfig();
	}

}
