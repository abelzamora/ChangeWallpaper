package abel.wallpaper.global;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class WallpaperParserXml {

	private static final String XML_URL = "/sdcard/changeWallpaper/dataConfig.xml";

	public WallpaperParserXml() {
	}

	public WallpaperDataConfig readXml() {
		// Instanciamos la fábrica para DOM
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		WallpaperDataConfig dataConfig = new WallpaperDataConfig();

		try {
			// Creamos un nuevo parser DOM
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Realizamos lalectura completa del XML
			File file = new File(XML_URL);
			InputStream is = new FileInputStream(file);
			Document dom = builder.parse(is);

			// Nos posicionamos en el nodo principal del árbol (<rss>)
			Element root = dom.getDocumentElement();

			// Localizamos todos los elementos <item>
			NodeList items = root.getChildNodes();

			// Recorremos la lista de noticias
			for (int i = 0; i < items.getLength(); i++) {
				// Noticia noticia = new Noticia();

				// Obtenemos la noticia actual
				Node item = items.item(i);

		
					String etiqueta = item.getNodeName();

					if (etiqueta.equals("activado")) {
						dataConfig.setActivado(obtenerTexto(item));
					} else if (etiqueta.equals("paquete")) {
						dataConfig.setPaquete(obtenerTexto(item));
					} else if (etiqueta.equals("firsTime")) {
						dataConfig.setFirsTime(obtenerTexto(item));
					}

			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return dataConfig;

	}

	private String obtenerTexto(Node dato) {
		StringBuilder texto = new StringBuilder();
		NodeList fragmentos = dato.getChildNodes();

		for (int k = 0; k < fragmentos.getLength(); k++) {
			texto.append(fragmentos.item(k).getNodeValue());
		}

		return texto.toString();
	}

	public void writeXml(WallpaperDataConfig dataConfig) throws IllegalArgumentException, IllegalStateException, IOException {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		serializer.setOutput(writer);
		serializer.startDocument("UTF-8", true);
		serializer.startTag("", "param_config");
		serializer.startTag("", "activado");
		serializer.text(String.valueOf(dataConfig.getActivado()));
		serializer.endTag("", "activado");
		serializer.startTag("", "paquete");
		serializer.text(dataConfig.getPaquete());
		serializer.endTag("", "paquete");
		serializer.startTag("", "firsTime");
		serializer.text(String.valueOf(dataConfig.getFirsTime()));
		serializer.endTag("", "firsTime");
		serializer.endTag("", "param_config");
		serializer.endDocument();

		writeFile(writer.toString());

	}

	private void writeFile(String file) throws IOException {
		Writer writer = new FileWriter(XML_URL);
		writer.write(file);
		writer.close();

	}

}